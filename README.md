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

## License

MIT License

Copyright (c) 2019 QingHang Peng



