package com.example.demo.config.mongoConfig;

import com.mongodb.MongoClientURI;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @ClassName AdminMongoTemplateConfig
 * @Description
 * @Author jackson
 * @Date 2019/8/27 15:51
 * @Version 1.0
 **/
@Configuration
@Log
@EnableMongoRepositories(basePackages = "com.example.demo.dao.admin",mongoTemplateRef = "adminMongoTemplate")
public class AdminMongoTemplateConfig {

    @Bean("adminMongoProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.admindb")
    public MongoProperties fmsMongoProperties() {
        return new MongoProperties();
    }

    @Bean(name = "adminMongoTemplate")
    public MongoTemplate fmsMongoTemplate(@Qualifier("adminMongoProperties") MongoProperties properties) throws Exception {
        MongoDbFactory factory = new SimpleMongoDbFactory(new MongoClientURI(properties.getUri()));
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
        converter.afterPropertiesSet();
        // Don't save _class to mongo
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return new MongoTemplate(factory, converter);
    }

}
