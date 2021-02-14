package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.config.AspectDemo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 注解方式激活 AspectJ
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-14 20:52:52
 */
@ComponentScan("com.xiaobei.spring.aop.demo")
@Configuration
public class AspectJAnnotationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        applicationContext.register(AspectJAnnotationDemo.class);
        applicationContext.refresh();
        AspectDemo aspectDemo = applicationContext.getBean(AspectDemo.class);
        System.out.println(aspectDemo);
        applicationContext.close();
    }
}
