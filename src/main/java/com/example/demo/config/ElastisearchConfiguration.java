package com.example.demo.config;

import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.net.URL;

/**
 * @ClassName ElastisearchConfiguration
 * @Description
 * @Author jackson
 * @Date 2019/1/25 11:13
 * @Version 1.0
 **/
@Configuration
public class ElastisearchConfiguration implements FactoryBean<RestHighLevelClient>, InitializingBean, DisposableBean {

    public static final Logger logger =  LoggerFactory.getLogger(ElastisearchConfiguration.class);

    /*@Value("${spring.data.elasticsearch.clusterNodes}")
    private String clusterNodes;*/

    @Autowired
    private RestConfig restConfig;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    //配置用户名、密码
    /*@Value("${spring.data.elasticsearch.username}")
    private String username;
    @Value("${spring.data.elasticsearch.password}")
    private String password;*/

    private RestHighLevelClient restHighLevelClient;

    @Override
    public void destroy() throws Exception {
        try {
            logger.info("Closing elasticSearch client");
            if (restHighLevelClient != null) {
                restHighLevelClient.close();
            }
        }catch (Exception e){
            logger.error("Error closing elasticSearch client:",e);
        }

    }

    @Override
    public RestHighLevelClient getObject() throws Exception {
        return restHighLevelClient;
    }

    @Override
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(username,password));

        String[] hostsStr = restConfig.getClusterNodes().split(",");
        HttpHost[] hosts = new HttpHost[hostsStr.length];
        for (int i=0;i<hosts.length;i++) {
            URL url = new URL(hostsStr[i]);
            hosts[i] = new HttpHost(url.getHost(),url.getPort(),url.getProtocol());
        }

        RestClientBuilder builder = RestClient.builder(hosts);
        builder.setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        restHighLevelClient = new RestHighLevelClient(builder);
    }
}
