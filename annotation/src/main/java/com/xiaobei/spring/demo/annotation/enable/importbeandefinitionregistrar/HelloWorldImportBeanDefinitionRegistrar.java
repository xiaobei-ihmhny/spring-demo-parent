package com.xiaobei.spring.demo.annotation.enable.importbeandefinitionregistrar;

import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * TODO 感觉还是有点欠缺！！！
 * 使用 {@link ImportBeanDefinitionRegistrar} 实现 {@code Enable} 驱动
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-27 15:54:54
 * @see ImportBeanDefinitionRegistrar
 */
public class HelloWorldImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(String.class);
        beanDefinitionBuilder.addConstructorArgValue("hello, world");
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // 注解Bean
        registry.registerBeanDefinition("helloWorld", beanDefinition);
    }
}