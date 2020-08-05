package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.BasicTypeDemo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 基础类型注入示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-05 17:03:03
 */
public class BasicTypeDependencyInjectionDemo {

    @Test
    public void basicTypeDependencyInjectionByXml() {
        String location = "classpath:/META-INF/basic-type-dependency-injection.xml";
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(location);
        // 启动应用上下文
        applicationContext.start();
        // 依赖查找
        BasicTypeDemo basicTypeDemo = applicationContext.getBean(BasicTypeDemo.class);
        System.out.println(basicTypeDemo);
        applicationContext.close();
    }
}