### 使用方式

- **pom依赖**
  ```
  <dependency>
      <groupId>cn.digitlink</groupId>
      <artifactId>ftp-spring-boot-starter</artifactId>
      <version>0.0.1.RELEASE</version>
  </dependency>
  ```

- **配置文件**
  ```
  ftp.type=sftp
  ftp.host=127.0.0.1
  ftp.port=22
  ftp.username=ftpuser
  ftp.password=123456
  ftp.base-path=/
  ```

- **测试用例**
  ```
  @SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = FtpConfig.class)
  class SpringdemoApplicationTests {
  
      @Resource
      private FtpTemplate ftpTemplate;
  
      @Test
      void contextLoads() {
          List<String> fileList = ftpTemplate.getFileList("/", ".*");
          System.out.println(fileList);
      }
  
  }
  ```
