package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.config.AwareConfig;
import org.junit.Test;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringValueResolver;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-05 13:27:27
 */
public class AwareInterfaceDependencyInjectionDemo {

    /**
     * 通过{@link Aware} 接口来“感知”到相关的对象，
     * 这和spring提供的内建的依赖是同一个对象，只是实现手段不同
     *
     * 其中 {@link BeanNameAware} {@link BeanClassLoaderAware} {@link BeanFactoryAware}
     * 的回调位置位于：
     * {@link AbstractAutowireCapableBeanFactory#invokeAwareMethods(java.lang.String, java.lang.Object)}
     *
     * 其中 {@link EnvironmentAware} {@link EmbeddedValueResolverAware} {@link ResourceLoaderAware}
     * {@link ApplicationEventPublisherAware} {@link MessageSourceAware} {@link ApplicationContextAware}
     * 的回调位置位于：
     * {@link org.springframework.context.support.ApplicationContextAwareProcessor#invokeAwareInterfaces(java.lang.Object)}
     *
     * 参见 {@link AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)}
     * 中的方法 {@code beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));}
     *
     * @see AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)
     */
    @Test
    public void awareInterfaceDependencyInjection() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AwareConfig.class);
        applicationContext.refresh();
        AwareConfig awareConfig = applicationContext.getBean(AwareConfig.class);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println(awareConfig.getBeanFactory() == beanFactory);
        System.out.println(awareConfig.getApplicationContext() == applicationContext);
        System.out.println(awareConfig.toString());
        applicationContext.close();

    }
}