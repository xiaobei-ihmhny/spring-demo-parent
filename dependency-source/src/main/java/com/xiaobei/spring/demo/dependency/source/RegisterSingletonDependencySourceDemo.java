package com.xiaobei.spring.demo.dependency.source;

import com.xiaobei.spring.demo.dependency.source.domain.Domain;
import org.junit.Test;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
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
     * Domain{id=1, name='xiaobei'}
     *
     * <h2>根据类型查询时的流程如下：</h2>
     * {@link DefaultListableBeanFactory#getBean(java.lang.Class)}
     *  {@link DefaultListableBeanFactory#getBean(java.lang.Class, java.lang.Object...)}
     *   {@link DefaultListableBeanFactory#resolveBean(org.springframework.core.ResolvableType, java.lang.Object[], boolean)}
     *    {@link DefaultListableBeanFactory#resolveNamedBean(org.springframework.core.ResolvableType, java.lang.Object[], boolean)}
     *     {@link DefaultListableBeanFactory#getBeanNamesForType(org.springframework.core.ResolvableType)}
     *      {@link DefaultListableBeanFactory#getBeanNamesForType(org.springframework.core.ResolvableType, boolean, boolean)}
     *       {@link DefaultListableBeanFactory#getBeanNamesForType(java.lang.Class, boolean, boolean)}
     *        {@link DefaultListableBeanFactory#doGetBeanNamesForType(org.springframework.core.ResolvableType, boolean, boolean)}
     *         在 {@code for (String beanName : this.manualSingletonNames)} 中会获取到单例bean
     * <h2>根据名称查询时的流程如下：</h2>
     * {@link AbstractBeanFactory#getBean(java.lang.String)}
     *  {@link AbstractBeanFactory#doGetBean(String, Class, Object[], boolean)}
     *   {@link DefaultSingletonBeanRegistry#getSingleton(java.lang.String)}
     *   {@link AbstractBeanFactory#getObjectForBeanInstance(Object, String, String, RootBeanDefinition)} 获取到单例bean，并返回
     */
    @Test
    public void registerSingletonAfterContextRefreshed() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 启动应用上下文
        applicationContext.refresh();
        // 注册单例bean
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        beanFactory.registerSingleton("name", new Domain().setId(1L).setName("xiaobei"));
        // 通过类型查找单例bean
        Domain bean = beanFactory.getBean(Domain.class);
        System.out.println(bean);
        // 通过名称查找单例bean
        Domain getBeanByName = (Domain) beanFactory.getBean("name");
        System.out.println(getBeanByName);
        applicationContext.close();
    }

    /**
     * BeanDefinition在容器启动之后也是可以注册的，所以可以动态的添加指定的bean
     *
     * <h2>运行结果：</h2>
     * 启动之后的bean的数量：5
     * 再次手动注册之后的bean的数量：6
     * Domain{id=2, name='xiaobei'}
     */
    @Test
    public void registerBeanDefinitionAfterContextRefreshed() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 启动应用上下文
        applicationContext.refresh();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        System.out.printf("启动之后的bean数组为 %s, bean的数量 %s\n" , Arrays.toString(beanDefinitionNames), beanDefinitionNames.length);
        // 注册单例bean
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(Domain.class);
        builder.addPropertyValue("id", 2L);
        builder.addPropertyValue("name", "xiaobei");
        applicationContext.registerBeanDefinition("domain", builder.getBeanDefinition());
        beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        System.out.printf("再次手动注册之后的bean数组为 %s, bean的数量 %s\n" , Arrays.toString(beanDefinitionNames), beanDefinitionNames.length);
        Domain bean = applicationContext.getBean(Domain.class);
        System.out.println(bean);
        applicationContext.close();
    }
}