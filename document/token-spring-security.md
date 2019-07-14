[返回目录](../README.md)

在我的用户密码授权文章里介绍了spring-security的工作过程，不了解的同学，可以先看看[用户密码授权](./user-password-spring-security.md)这篇文章，在
用户密码授权模式里，主要是通过一个登陆页进行授权，然后把授权对象写到session里，它主要用在mvc框架里，而对于webapi来说，一般不会采用这种方式，对于webapi
来说，一般会用jwt授权方式，就是token授权码的方式，每访问api接口时，在http头上带着你的token码，而大叔自己也写了一个简单的jwt授权模式，下面介绍一下。

### WebSecurityConfig授权配置
```$xslt
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {
  /**
   * token过滤器.
   */
  @Autowired
  LindTokenAuthenticationFilter lindTokenAuthenticationFilter;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf().disable()
        // 基于token，所以不需要session
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        // 对于获取token的rest api要允许匿名访问
        .antMatchers("/lind-auth/**").permitAll()
        // 除上面外的所有请求全部需要鉴权认证
        .anyRequest().authenticated();
    httpSecurity
        .addFilterBefore(lindTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    // 禁用缓存
    httpSecurity.headers().cacheControl();
  }

  /**
   * 密码生成策略.
   *
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

```

### 授权接口login
对外开放的，需要提供用户名和密码为参数进行登陆，然后返回token码，当然也可以使用手机号和验证码登陆，授权逻辑是一样的，获取用户信息都是使用`UserDetailsService`,
然后开发人员根据自己的业务去重写`loadUserByUsername`来获取用户实体。
> 用户登陆成功后，为它授权及认证，这一步我们会在redis里建立token与用户名的关系。
```$xslt
@GetMapping(LOGIN)
  public ResponseEntity<?> refreshAndGetAuthenticationToken(
      @RequestParam String username,
      @RequestParam String password) throws AuthenticationException {
    return ResponseEntity.ok(generateToken(username, password));
  }

  /**
   * 登陆与授权.
   *
   * @param username .
   * @param password .
   * @return
   */
  private String generateToken(String username, String password) {
    UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
    // Perform the security
    final Authentication authentication = authenticationManager.authenticate(upToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    // Reload password post-security so we can generate token
    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    // 持久化的redis
    String token = CommonUtils.encrypt(userDetails.getUsername());
    redisTemplate.opsForValue().set(token, userDetails.getUsername());
    return token;
  }
```

### LindTokenAuthenticationFilter代码
主要实现了对请求的拦截，获取http头上的`Authorization`元素，token码就在这个键里，我们的token都是采用通用的`Bearer`开头，当你的token没有过期时，会
存储在redis里，key就是用户名的md5码，而value就是用户名，当拿到token之后去数据库或者缓存里拿用户信息进行授权即可。
```$xslt
/**
 * token filter bean.
 */
@Component
public class LindTokenAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  RedisTemplate<String, String> redisTemplate;
  String tokenHead = "Bearer ";
  String tokenHeader = "Authorization";
  @Autowired
  private UserDetailsService userDetailsService;

  /**
   * token filter.
   *
   * @param request     .
   * @param response    .
   * @param filterChain .
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader(this.tokenHeader);
    if (authHeader != null && authHeader.startsWith(tokenHead)) {
      final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
      if (authToken != null && redisTemplate.hasKey(authToken)) {
        String username = redisTemplate.opsForValue().get(authToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
          //可以校验token和username是否有效，目前由于token对应username存在redis，都以默认都是有效的
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
              request));
          logger.info("authenticated user " + username + ", setting security context");
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }

    filterChain.doFilter(request, response);

  }
```
### 测试token授权
```$xslt
get:http://localhost:8080/lind-demo/login?username=admin&password=123

post:http://localhost:8080/lind-demo/user/add
Content-Type:application/json
Authorization:Bearer 21232F297A57A5A743894A0E4A801FC3
```