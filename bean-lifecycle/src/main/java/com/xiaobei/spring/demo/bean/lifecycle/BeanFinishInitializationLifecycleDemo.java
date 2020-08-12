package com.xiaobei.spring.demo.bean.lifecycle;

import com.xiaobei.spring.demo.bean.lifecycle.domain.FinishInitializationDomain;
import org.junit.Test;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-12 13:36:36
 */
public class BeanFinishInitializationLifecycleDemo {

    /**
     *
     * 其中 {@link DefaultListableBeanFactory#preInstantiateSingletons()}方法的作用主要有两个
     * 1. 首先初始化所有非延迟加载的bean
     * 2. bean加载成功后，调用{@link SmartInitializingSingleton#afterSingletonsInstantiated()}完成初始化完成阶段的回调
     *
     * 所以调用{@link SmartInitializingSingleton#afterSingletonsInstantiated()}时可以确保所有非延迟bean已经完成的初始化。
     *
     * 调用顺序如下：
     *
     * @see AbstractApplicationContext#refresh
     * @see AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory)
     * @see DefaultListableBeanFactory#preInstantiateSingletons
     *
     *
     * {@link SmartInitializingSingleton}通常在Spring ApplicationContext场景中使用
     * @see SmartInitializingSingleton
     */
    @Test
    public void afterSingletonsInstantiated() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        String location = "META-INF/life-cycle-finish-domain.xml";
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(location);
        // 手动调用
        beanFactory.preInstantiateSingletons();
        // 依赖查找
        FinishInitializationDomain bean = beanFactory.getBean(FinishInitializationDomain.class);
        System.out.println("查询结果为：" + bean);
    }
}