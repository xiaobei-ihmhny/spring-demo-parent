package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.config.AspectDemo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * xml 的方式来激活 @AspectJ 模块
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-14 21:02:02
 */
public class AspectJXmlDemo {

    /**
     * 运行结果：com.xiaobei.spring.aop.demo.config.AspectDemo@262b2c86
     * @param args
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext("classpath:/META-INF/spring-aop-config.xml");
        AspectDemo aspectDemo = applicationContext.getBean(AspectDemo.class);
        System.out.println(aspectDemo);
        applicationContext.close();
    }
}
