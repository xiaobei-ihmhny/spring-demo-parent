package com.xiaobei.spring.demo.bean.definition.initialization;

import org.junit.Test;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 7:33
 */
public class BeanInitializationDemo {

    /**
     * 通过xml的init-method标签设置bean的初始化方法
     *
     * 自定义初始化方法：XML配置：<bean init-method="init" .../>
     */
    @Test
    public void initializeBeanByInitMethodWithXml() {
        new ClassPathXmlApplicationContext("META-INF/bean-initialization-context.xml");
    }

    /**
     * 通过annotation的initMethod属性指定bean的初始化方法
     * 自定义初始化方法：Java注解：@Bean(initMethod="init")
     */
    @Test
    public void initializeBeanByInitMethodWithAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationBeanInitialization.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 关闭应用上下文
        applicationContext.close();
    }

    /**
     * 通过api的方法指定bean的initMethod方法名称
     */
    @Test
    public void initializeBeanByApi() {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(BeanInitialization.class);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        // 设置beanDefinition对应的init方法
        beanDefinition.setInitMethodName("initBeanMethodByApi");
        applicationContext.registerBeanDefinition("beanInitializationByApi", beanDefinition);
        // 启动应用上下文
        applicationContext.refresh();
        applicationContext.close();
    }

    /**
     * 通过实现InitializationBean的afterPropertiesSet方法实现bean的初始化..
     */
    @Test
    public void initializeBeanByImplementsInitializationBean() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInitializationByImplementsInitializationBean.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 关闭应用上下文
        applicationContext.close();
    }


    /**
     * 通过注解@PostConstruct来实现bean的初始化
     */
    @Test
    public void initializeBeanByPostConstruct() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInitializationByPostConstruct.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 关闭应用上下文
        applicationContext.close();
    }

    /**
     * 三种bean初始化方式的优先级如下：
     *
     * BeanInitializationVs：通过注解@PostConstruct来实现bean的初始化
     * BeanInitializationVs：通过实现InitializationBean的afterPropertiesSet方法实现bean的初始化..
     * BeanInitializationVs：自定义初始化方法
     */
    @Test
    public void initializeBeanCompare() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInitializationVsConfig.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 关闭应用上下文
        applicationContext.close();
    }
}
