package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.config.AwareConfig;
import org.junit.Test;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-05 13:27:27
 */
public class AwareInterfaceDependencyInjectionDemo {

    /**
     * 通过{@link Aware} 接口来“感知”到相关的对象，
     * 这和spring提供的内建的依赖是同一个对象，只是实现手段不同
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