package com.xiaobei.spring.demo.dependency.lookup;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 示例依赖查找中的常见异常 {@link org.springframework.beans.BeansException}
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/4 7:10
 */
public class ClassicExceptionInLookupDemo {

    @Test
    public void NoSuchBeanDefinitionException() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ClassicExceptionInLookupDemo.class);
        applicationContext.refresh();
        // 获取一个不存在的bean
        String stringBean = applicationContext.getBean(String.class);
        System.out.println(stringBean);
        applicationContext.close();
    }
}
