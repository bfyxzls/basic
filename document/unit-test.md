[返回目录](../README.md)

###  单元测试
单元测试保证的软件的质量，对于一个web项目来的，你对外暴露的方法都应该由测试保证它的正确性，
对于测试粒度可以分为单元测试和集成测试，一般我们写的公用方法都会对应写一个单元测试，而系统
的web->service->data会写一个对应的集成测试。

### 单元测试和集成测试注意点
1. 不要使用跨网络的资源，应该使用本地资源
1. 尽可能多的添加测试的场景
1. mock各种外部服务

### 一些成熟的mock框架
1. amqp: org.apache.qpid:qpid-broker:6.1.2
1. redis: com.github.kstyrc:embedded-redis:0.6
1. mongodb: de.flapdoodle.embed:de.flapdoodle.embed.mongo
1. database: com.h2database:h2

### WebTestClient实现的web测试（异步）
>安装包：'org.springframework.boot:spring-boot-starter-webflux',

```$xslt
  @Autowired
  protected WebTestClient http;
  
  @Test
  public void get(){
   http.get().uri("/api/v1/")
          .exchange()
          .expectStatus().isOk()
          .expectBody()
          .jsonPath("$.status").isEqualTo("200");
   }
   
    @Test
    public void put() {
      http.put().uri("api/v1")
          .syncBody(UserRequest.builder().id(10481779L).status(User.Status.Normal.name()).build())
          .exchange()
          .expectStatus().isOk();
    }
    
    @Test
     public void post() {
        http.post()
            .uri(uriBuilder -> uriBuilder.path("/api/v1")
                .queryParam("mobileNumbers", "17694873618")
                .queryParam("mobileNumbers", "17694873618")
                .build())
            .syncBody(UserRequest.builder().id(10481779L).status(User.Status.Normal.name()).build())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.data[1].mobileNumber").isEqualTo("17694873618");
     }
```

### MockMvc实现的web测试（同步）
```$xslt
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class RequestTest {
  private MockMvc mockMvc;
  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void init() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

  }

  @Test
  public void getTest() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get(LindDemo.HELLO200)
                .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
  }

  @Test
  public void postTest() throws Exception {
    mockMvc
        .perform(
            post(LindDemo.POSTDO)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("zzl"))
        .andExpect(status().isOk());
  }
}

```