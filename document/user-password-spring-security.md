[返回目录](../README.md)

### 用户密码授权模式
spring的授权组件，本身提供多种授权模式，默认为用户名密码的方式，开发人员可以重写对的方法，来实现
自己的功能，spring的很多组件都是这样，提供了一种问题的解决方法，其中的细节开发人员可以自己重写，
这让我们想起了`模板方法`模式，它确实了解决问题的一套流程，然后由开发人员自己去实现或者重写里面的细节 。

> 在spring-security里，所有的授权都先走`WebSecurityConfig`对象，在这里面会有授权的策略，如你将哪
些页面添加到白名单，登陆页面的设置，授权过滤器的配置，密码组件的注入等等。

### 在WebSecurityConfig里开启它
```
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  LindAuthenticationSuccessHandler lindAuthenticationSuccessHandler;

  @Autowired
  LindAuthenticationFailHandler lindAuthenticationFailHandler;
  @Autowired
  LindAuthenticationProvider lindAuthenticationProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/", "/index").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")//按路由授权
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/hello")//默认登录成功后跳转的页面
        .successHandler(lindAuthenticationSuccessHandler)
        .failureHandler(lindAuthenticationFailHandler)
        .permitAll()
        .and()
        .addFilterAt(lindAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).authorizeRequests().and()
        .logout()
        .permitAll();
  }

  /**
   * 自定义的Filter.
   */
  @Bean
  LindUserNameAuthenticationFilter lindAuthenticationFilter() {
    LindUserNameAuthenticationFilter phoneAuthenticationFilter = new LindUserNameAuthenticationFilter();
    ProviderManager providerManager =
        new ProviderManager(Collections.singletonList(lindAuthenticationProvider));
    phoneAuthenticationFilter.setAuthenticationManager(providerManager);
    phoneAuthenticationFilter.setAuthenticationSuccessHandler(lindAuthenticationSuccessHandler);
    phoneAuthenticationFilter.setAuthenticationFailureHandler(lindAuthenticationFailHandler);
    return phoneAuthenticationFilter;
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
### AuthenticationProvider
* 默认实现：DaoAuthenticationProvider

授权方式提供者，判断授权有效性，用户有效性，在判断用户是否有效性，它依赖于UserDetailsService实例，开发人员可以自定义UserDetailsService的实现。
1. additionalAuthenticationChecks方法校验密码有效性
2. retrieveUser方法根据用户名获取用户
3. createSuccessAuthentication完成授权持久化
```
@Component
@Slf4j
public class LindAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * 校验密码有效性.
   *
   * @param userDetails    .
   * @param authentication .
   * @throws AuthenticationException .
   */
  @Override
  protected void additionalAuthenticationChecks(
      UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
    if (authentication.getCredentials() == null) {
      logger.debug("Authentication failed: no credentials provided");

      throw new BadCredentialsException(messages.getMessage(
          "AbstractUserDetailsAuthenticationProvider.badCredentials",
          "Bad credentials"));
    }

    String presentedPassword = authentication.getCredentials().toString();

    if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
      logger.debug("Authentication failed: password does not match stored name");

      throw new BadCredentialsException(messages.getMessage(
          "AbstractUserDetailsAuthenticationProvider.badCredentials",
          "Bad credentials"));
    }
  }

  /**
   * 获取用户.
   *
   * @param username       .
   * @param authentication .
   * @return
   * @throws AuthenticationException .
   */
  @Override
  protected UserDetails retrieveUser(
      String username, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
    UserDetails loadedUser = userDetailsService.loadUserByUsername(username);
    if (loadedUser == null) {
      throw new InternalAuthenticationServiceException(
          "UserDetailsService returned null, which is an interface contract violation");
    }
    return loadedUser;
  }
}
 /**
   * 授权持久化.
   */
  @Override
  protected Authentication createSuccessAuthentication(Object principal,
                                                       Authentication authentication, UserDetails user) {
    return super.createSuccessAuthentication(principal, authentication, user);
  }
```

### AuthenticationFilter
* 默认实现：UsernamePasswordAuthenticationFilter

授权过滤器，你可以自定义它，并把它添加到默认过滤器前或者后去执行，主要用来到授权的持久化，它可以从请求上下文中获取你的user,password等信息，然后去判断它是否符合规则，最后通过authenticate方法去授权。默认的`UsernamePasswordAuthenticationFilter`过滤器，主要判断请求方式是否为post，并且对username和password进行了默认值的处理，总之，在这个过滤器里不会涉及到具体业务。
```
public class LindUserNameAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public LindUserNameAuthenticationFilter() {
    super(new AntPathRequestMatcher("/login", "GET"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if (username == null) {
      throw new InternalAuthenticationServiceException("Failed to get the username");
    }

    if (password == null) {
      throw new InternalAuthenticationServiceException("Failed to get the password");
    }

    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
        username, password);
    authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    return this.getAuthenticationManager().authenticate(authRequest);
  }
}
```

### UserDetialsService
这是一个接口，有默认的实现方式，一般的，我们需要根据业务去重新实现它，比如从你的用户表获取当前授权的用户信息，你需要在UserDetialsService实现类里对用户表进行读取操作；它一般会在AuthenticationProvider里的retrieveUser方法中被使用，这就像面向对象里的模板方法模式一样，springSecurity把检验的步骤设计好了，咱们开发只要根据规则去实现具体细节就好。
```
@Component
public class MyUserDetailService implements UserDetailsService {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    /*
      设置用户和角色需要注意：
      1. commaSeparatedStringToAuthorityList放入角色时需要加前缀ROLE_，而在controller使用时不需要加ROLE_前缀
      2. 放入的是权限时，不能加ROLE_前缀，hasAuthority与放入的权限名称对应即可
    */
    List<UserDetails> userDetailsList = new ArrayList<>();
    userDetailsList.add(User.builder()
        .username("admin")
        .password(passwordEncoder.encode("123"))
        .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("read,ROLE_ADMIN")).build());
    userDetailsList.add(User.builder()
        .username("user")
        .password(passwordEncoder.encode("123"))
        .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("read,ROLE_USER"))
        .build());

    //获取用户
    return userDetailsList.stream()
        .filter(o -> o.getUsername().equals(name))
        .findFirst()
        .orElse(null);

  }
}
```


### 认证时执行的顺序
1. LindUserNameAuthenticationFilter
1. LindAuthenticationProvider.retrieveUser
1. LindAuthenticationProvider.additionalAuthenticationChecks
1. UserDetailsService
1. Authentication

springSecurity源码：https://github.com/spring-projects/spring-security