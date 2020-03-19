[返回目录](../README.md)
参考文档：https://github.com/anxpp/spring-rest-doc
### 添加依赖
```$xslt
testCompile('org.springframework.restdocs:spring-restdocs-mockmvc')
asciidoctor 'org.springframework.restdocs:spring-restdocs-asciidoctor'
```

### snippetsDir插件引用
```$xslt
plugins {
    id "org.asciidoctor.convert" version "1.5.3"
}
```

### 配置三大路径
配置三大路径的地址，三大路径指，asciidoctor文档路径，生成的API文档目录和snippets目录
```$xslt
jar {
    dependsOn asciidoctor
    from ("${asciidoctor.outputDir}/html5") {
        into 'static/docs'
    }
}

ext {
    snippetsDir = file('build/generated-snippets')
}


integTest {
    outputs.dir snippetsDir
}

asciidoctor {
    inputs.dir snippetsDir
    outputDir "build/asciidoc"
    dependsOn integTest
    sourceDir 'src/docs/asciidoc'
}
```
### 注意在单元测试里的命名空间
```$xslt
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
```
### 单元测试的mvcMock对象注册它
```$xslt
 private MockMvc mockMvc;
 @Autowired
 private WebApplicationContext webApplicationContext; 
 @Rule
 public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
 @Before
 public void init() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(this.restDocumentation))
        .alwaysDo(print())
        .build();

  }
```