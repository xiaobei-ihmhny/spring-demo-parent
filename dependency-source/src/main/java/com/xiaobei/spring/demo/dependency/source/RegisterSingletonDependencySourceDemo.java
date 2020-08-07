package com.xiaobei.spring.demo.dependency.source;

import com.xiaobei.spring.demo.dependency.source.domain.Domain;
import org.junit.Test;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-07 11:00:00
 */
@SuppressWarnings("DuplicatedCode")
public class RegisterSingletonDependencySourceDemo {

    /**
     * 测试启动容器后进行单例bean的注册
     *
     * 示例：
     * Domain{id=1, name='xiaobei'}
     */
    @Test
    public void registerSingletonAfterContextRefreshed() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 启动应用上下文
        applicationContext.refresh();
        // 注册单例bean
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        beanFactory.registerSingleton("name", new Domain().setId(1L).setName("xiaobei"));
        Domain bean = beanFactory.getBean(Domain.class);
        System.out.println(bean);
        applicationContext.close();
    }

    /**
     * TODO BeanDefinition在容器启动之后也是可以注册的？？？
     */
    @Test
    public void registerBeanDefinitionAfterContextRefreshed() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 启动应用上下文
        applicationContext.refresh();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        System.out.println("启动之后的bean的数量：" + beanDefinitionNames.length);
        // 注册单例bean
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(Domain.class);
        builder.addPropertyValue("id", 2L);
        builder.addPropertyValue("name", "xiaobei");
        applicationContext.registerBeanDefinition("domain", builder.getBeanDefinition());
        beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        System.out.println("再次手动注册之后的bean的数量：" + beanDefinitionNames.length);
        Domain bean = applicationContext.getBean(Domain.class);
        System.out.println(bean);
        applicationContext.close();
    }
}