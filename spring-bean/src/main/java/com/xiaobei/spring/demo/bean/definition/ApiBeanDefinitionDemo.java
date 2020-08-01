package com.xiaobei.spring.demo.bean.definition;

import com.xiaobei.spring.demo.ioc.overview.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * 使用java api的方式注册spring BeanDefinition
 * 1. 命名方式
 * 使用 {@link BeanDefinitionRegistry#registerBeanDefinition(String, BeanDefinition)}
 * 2. 非命名方式
 * 使用 {@link BeanDefinitionReaderUtils#registerWithGeneratedName(AbstractBeanDefinition, BeanDefinitionRegistry)}
 * 3. 配置类方式
 * 使用{@link AnnotatedBeanDefinitionReader#register(java.lang.Class[])}
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/1 19:40
 */
public class ApiBeanDefinitionDemo {

    /**
     * 通过API的方式注册spring bean，并指定bean的名称
     *
     * org.springframework.beans.factory.support.BeanDefinitionRegistry#registerBeanDefinition(java.lang.String, org.springframework.beans.factory.config.BeanDefinition)
     * {xiaobei-huihui=User{id=35, name='Java API命名方式配置元信息'}}
     */
    @Test
    public void registerBeanDefinitionByApiWithBeanName() {
        AnnotationConfigApplicationContext beanFactory = new AnnotationConfigApplicationContext();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id", 35)
                .addPropertyValue("name", "Java API命名方式配置元信息");
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        beanFactory.registerBeanDefinition("xiaobei-huihui", beanDefinition);
        beanFactory.refresh();
        Map<String, User> beansOfType = beanFactory.getBeansOfType(User.class);
        System.out.println(beansOfType);
        beanFactory.close();
    }

    /**
     * {com.xiaobei.spring.demo.ioc.overview.domain.User#0=User{id=35, name='Java API非命名方式配置元信息'}}
     */
    @Test
    public void registerBeanDefinitionByApiWithOutBeanName() {
        AnnotationConfigApplicationContext beanFactory = new AnnotationConfigApplicationContext();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id", 35)
                .addPropertyValue("name", "Java API非命名方式配置元信息");
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, beanFactory);
        beanFactory.refresh();
        Map<String, User> beansOfType = beanFactory.getBeansOfType(User.class);
        System.out.println(beansOfType);
        beanFactory.close();
    }

    /**
     * TODO 具体如何使用？
     * 使用{@link AnnotatedBeanDefinitionReader#register(java.lang.Class[])}
     */
    @Test
    public void registerBeanDefinitionByConfigurationClass() {
        AnnotationConfigApplicationContext beanFactory = new AnnotationConfigApplicationContext();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id", 35)
                .addPropertyValue("name", "Java API非命名方式配置元信息");
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        beanFactory.refresh();
        Map<String, User> beansOfType = beanFactory.getBeansOfType(User.class);
        System.out.println(beansOfType);
        beanFactory.close();
    }
}
