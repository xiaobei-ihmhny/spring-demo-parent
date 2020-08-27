package com.xiaobei.spring.demo.annotation.attribute.overrides;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-27 14:56:56
 */
@CustomizedComponentScan(basePackages = "com.xiaobei.spring.demo.annotation.attribute.overrides") // 隐性覆盖
//@CustomizedComponentScan(scanBasePackages = "com.xiaobei.spring.demo.annotation.attribute.overrides") // 显性覆盖
//@CustomizedComponentScan2(scanBasePackages2 = "com.xiaobei.spring.demo.annotation.attribute.overrides") // 传递显性覆盖
public class CustomizedComponentScanDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(CustomizedComponentScanDemo.class);
        applicationContext.refresh();
        // 依赖查找
        ComponentScanDomain bean = applicationContext.getBean("componentScanDomain", ComponentScanDomain.class);
        System.out.println(bean);
        applicationContext.close();
    }
}