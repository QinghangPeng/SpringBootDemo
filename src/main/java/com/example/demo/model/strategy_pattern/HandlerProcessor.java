package com.example.demo.model.strategy_pattern;

import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName HandlerProcessor
 * @Description
 * @Author jackson
 * @Date 2019/5/22 11:30
 * @Version 1.0
 **/
@Component
@SuppressWarnings("unckecked")
public class HandlerProcessor implements BeanFactoryPostProcessor {

    private static final String HANDLER_PACKAGE = "com.example.demo.model.strategy_pattern.processor";

    /**
     * 扫描@HandlerType 初始化HandlerContext 将其注册到spring容器
     * @param beanFactory bean工厂
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String,Class> handlerMap = Maps.newHashMapWithExpectedSize(3);
        ClassScaner.scan(HANDLER_PACKAGE,HandlerType.class).forEach(clazz->{
            //获取注解中的类型值
            String type = clazz.getAnnotation(HandlerType.class).value();
            //将注解中的类型值作为key，对应的类作为value，保存在map中
            handlerMap.put(type,clazz);
        });
        //初始化HandlerContext,将其注册到spring容器中
        HandlerContext context = new HandlerContext(handlerMap);
        beanFactory.registerSingleton(HandlerContext.class.getName(),context);
    }
}
