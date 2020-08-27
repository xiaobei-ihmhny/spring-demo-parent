package com.xiaobei.spring.demo.annotation.enable;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-27 15:42:42
 */
//@com.xiaobei.spring.demo.annotation.enable.configuration.EnableHelloWorld // 基于 Configuration Class 实现
@com.xiaobei.spring.demo.annotation.enable.importselector.EnableHelloWorld // 基于ImportSelector 实现
//@com.xiaobei.spring.demo.annotation.enable.importbeandefinitionregistrar.EnableHelloWorld // 基于 ImportBeanDefinitionRegistrar 实现
public class EnableModuleDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(EnableModuleDemo.class);
        applicationContext.refresh();
        String helloWorld = applicationContext.getBean("helloWorld", String.class);
        System.out.println(helloWorld);
        applicationContext.close();
    }
}