package com.xiaobei.spring.demo.dependency.source;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * 非Spring容器管理对象作为依赖来源
 *
 * 只能用于类型方式的依赖注入，不能用于名称方式的依赖注入，也不能用于依赖查找
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-07 09:51:51
 */
public class ResolvableDependencySourceDemo {

    @Autowired
    private String name;

    @PostConstruct
    public void init() {
        System.out.println(name);
    }

    /**
     * 通过{@code applicationContext.getDefaultListableBeanFactory();}
     * 来获取{@link DefaultListableBeanFactory}进行非spring管理对象的注册
     */
    @Test
    public void resolvableByGetDefaultListableBeanFactory() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ResolvableDependencySourceDemo.class);
        DefaultListableBeanFactory defaultListableBeanFactory = applicationContext.getDefaultListableBeanFactory();
        defaultListableBeanFactory.registerResolvableDependency(String.class, "xiaobei");
        applicationContext.refresh();
        applicationContext.close();
    }

    /**
     * 通过{@code applicationContext.getBeanFactory();}
     * 来获取{@link ConfigurableListableBeanFactory}进行非spring管理对象的注册
     */
    @Test
    public void resolvableByGetBeanFactory() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ResolvableDependencySourceDemo.class);
        ConfigurableListableBeanFactory defaultListableBeanFactory = applicationContext.getBeanFactory();
        defaultListableBeanFactory.registerResolvableDependency(String.class, "xiaobei");
        applicationContext.refresh();
        applicationContext.close();
    }

    /**
     * 通过{@code applicationContext.addBeanFactoryPostProcessor();}添加bean工厂的回调处理
     * 来获取{@link ConfigurableListableBeanFactory}进行非spring管理对象的注册
     */
    @Test
    public void resolvableByAddBeanFactoryPostProcessor() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ResolvableDependencySourceDemo.class);
        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerResolvableDependency(String.class, "xiaobei");
        });
        applicationContext.refresh();
        applicationContext.close();
    }

}