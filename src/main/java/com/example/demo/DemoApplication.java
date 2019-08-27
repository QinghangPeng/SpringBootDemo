package com.example.demo;

import com.example.demo.config.DynamicProperties;
import com.example.demo.config.RestConfig;
import com.google.common.base.Predicates;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@EnableScheduling
@ComponentScan
@MapperScan(basePackageClasses = DemoApplication.class)
@EnableConfigurationProperties({RestConfig.class,DynamicProperties.class})
//@EnableCaching
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

    }

    @Bean
    public Docket useApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error.*"))) //错误路径不监控
                .paths(PathSelectors.regex("/.*")) //对所有根路径进行监控
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Swagger-2.0")
                .contact(new Contact("jackSon","","voyagejk.peng@gmail.com"))
                /*.description("This is a api document by Swagger_2")
                .termsOfServiceUrl("No terms of service")
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("v1.0")*/
                .build();
    }
}
