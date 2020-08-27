package com.xiaobei.spring.demo.annotation.enable.importbeandefinitionregistrar;

import com.xiaobei.spring.demo.annotation.enable.configuration.HelloWorldConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-27 15:53:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({HelloWorldImportBeanDefinitionRegistrar.class})
public @interface EnableHelloWorld {
}