[返回目录](../README.md)

### api返回值的标准化
例如
```
{"status":200,"message":"操作成功","data":"{\"id\":1,\"name\":\"张三\"}"}
```

#### 封装返回对象
对象被封装在base.util.ResponseUtils类型下，返回值是标准的ResponseEntity对象，返回体
进行了二次封装，主要有`status`,`messsage`和`data`组成，返回方法有ok和okMessage，如果
真是返回消息，不需要对象，可以选择使用`okMessage`，反之使用`ok`方法。

封装的返回对象：
```
  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  static class ResponseBody {

    private int status;
    private String message;
    private Object data;
  }
```
#### httpError和我们封装的httpError
对于http error来说有很多种，基本可以定为code在400到500之间的，像客户端参数问题就是`400-
bad request`,而没有认证就是`401-Unauthorized`,认证但没有对应的权限就是`403-Forbidden`,请求的
资源没有发现就是`404-Not Found`，请求方式错误（方法是post，你发起请求用了get）就是`405-
Method Not Allowed`等。
* 使用标准http响应状态码
```$xslt
  @GetMapping(GET_HTTP_ERROR)
  ResponseEntity<?> getHttpError() throws IOException {
    return ResponseEntity.badRequest().build();
  }
  @Test
  public void getHttpError() throws Exception {
      mockMvc
          .perform(
              get(LindDemo.GET_HTTP_ERROR)
                  .accept(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(status().is(400));
  
   }
    
```
响应的结果
```$xslt
MockHttpServletResponse:
           Status = 400
    Error message = null
          Headers = {}
     Content type = null
             Body = 
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
```
* 使用我们封装的status状态码
```$xslt
  @GetMapping(GET_ERROR)
  ResponseEntity<?> getError() throws IOException {
    return ResponseUtils.badRequest("传入的参数非法！");
  }
  
  @Test
    public void getError() throws Exception {
      mockMvc
          .perform(
              get(LindDemo.GET_ERROR)
                  .accept(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(status().isOk());
  
    }
```
响应的结果
```$xslt
MockHttpServletResponse:
           Status = 200
    Error message = null
          Headers = {Content-Type=[application/json;charset=UTF-8]}
     Content type = application/json;charset=UTF-8
             Body = {"status":400,"message":"传入的参数非法！","data":{}}
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
```
通过上面的响应结果可以看到，我们封装的请求httpcode还是200，只不过把请求错误400状态码写在了body
对象里，目前这种方法用的比较多，像一些第三方接口用的都是这种方式，他们会规定相应的响应规范。

#### 总结
事实上，两种响应体都没有问题，关键在于开发之间的规则要确定，不要在项目里两者兼用！