## SpringBoot-For-Recorded-History

[![Version](https://img.shields.io/badge/Version-1.0.1-0065FF.svg)](#)
[![Build Status](https://travis-ci.org/QinghangPeng/SpringBootDemo.svg?branch=master)](https://travis-ci.org/QinghangPeng/SpringBootDemo)
[![license: MIT](https://img.shields.io/badge/license-MIT-FF5630.svg)](https://opensource.org/licenses/MIT)

**Micro service Architecture**

- 基于springBoot 2.x版本
- 提供swagger-ui展示接口内容

## 核心依赖

依赖 | 版本
---  |  ---
Spring Boot  |  2.0.0.RELEASE
Swagger  |  2.6.1
Mybatis  |  1.3.2
Elasticsearch  |  6.4.3
Elasticsearch High Level Client  |  6.4.3
Kafka  |  2.1.12.RELEASE
Fastjson  |  1.2.37
Easyexcel |  1.1.2-beta5
Commons-Lang3  |  3.4


## 模块说明
```lua
SpringBootDemo
├── controller
      ├── AsynchronousController  -- 异步请求相关接口
      ├── CacheController  -- springboot整合cache的相关接口
      ├── SwaggerController  -- 各种数据库连接、kafka、excel导出等接口
      ├── MongoController  -- mongo相关接口
├── model
      ├── Flyweight_Pattern  -- 享元模式
      ├── decorator_pattern  -- 装饰模式
      ├── singleton_pattern  -- 单例模式
      ├── strategy_pattern  -- 策略模式
├── util
      ├── GeoUtil  -- 判断进出区
      ├── GPSUtil  -- 坐标系之间的相互转换
```

### 问题记录
```lua
1.导出swagger接口文档时，遭遇版本问题：
  解决方法：swagger2版本统一2.6.1  导入的包spring-restdocs-mockmvc中去除冲突版本spring-restdocs-core
            mapstruct引入时，在maven中一定要放在swagger2引入申明的上面
```


### feign文件上传
如果调用其他服务的上传接口，那么file文件是不能直接通过feign上传的。需要额外配置
```xml
        <dependency>
            <groupId>io.github.openfeign</groupId>
            <artifactId>feign-core</artifactId>
            <version>10.1.0</version>
        </dependency>
        <dependency>
            <groupId>io.github.openfeign.form</groupId>
            <artifactId>feign-form</artifactId>
            <version>3.8.0</version>
        </dependency>
        <dependency>
            <groupId>io.github.openfeign.form</groupId>
            <artifactId>feign-form-spring</artifactId>
            <version>3.8.0</version>
        </dependency>
```
在feign的client接口调用其他服务的上传接口示例
```java
 @FeignClient(
        name = "nccc-basic-file-manage",
        configuration = FileUploadClient.MultipartSupportConfig.class
)
public interface FileUploadClient {

    /**
     * 文件上传
     * @param encryptCode
     * @param file
     * @return
     */
    @PostMapping(
            value = "/file_manages/upload/{encryptCode}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    ResponseBody uploadFile(@PathVariable("encryptCode") String encryptCode, @RequestPart("file") MultipartFile file);

    class MultipartSupportConfig {
        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;
        @Bean
        public Encoder feignFormEncoder () {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }
    }

}
```


## License

MIT License

Copyright (c) 2019 QingHang Peng



