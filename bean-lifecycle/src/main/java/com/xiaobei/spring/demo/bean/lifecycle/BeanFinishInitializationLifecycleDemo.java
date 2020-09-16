package com.xiaobei.spring.demo.bean.lifecycle;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-12 13:36:36
 */
public class BeanFinishInitializationLifecycleDemo {

    static class FinishInitializationDomain implements SmartInitializingSingleton {

        private Long id;

        private String name;

        public Long getId() {
            return id;
        }

        public FinishInitializationDomain setId(Long id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public FinishInitializationDomain setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public void afterSingletonsInstantiated() {
            this.id = 101L;
            System.out.println("SmartInitializingSingleton#afterSingletonsInstantiated方法执行...");
        }

        @Override
        public String toString() {
            return "FinishInitializationDomain{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    /**
     *
     * <h2>运行结果：</h2>
     * SmartInitializingSingleton#afterSingletonsInstantiated方法执行...
     * 查询结果为：FinishInitializationDomain{id=101, name='XML配置'}
     *
     * 其中 {@link DefaultListableBeanFactory#preInstantiateSingletons()}方法的作用主要有两个
     * 1. 首先初始化所有非延迟加载的bean
     * 2. bean加载成功后，调用{@link SmartInitializingSingleton#afterSingletonsInstantiated()}完成初始化完成阶段的回调
     * 所以调用{@link SmartInitializingSingleton#afterSingletonsInstantiated()}时可以确保所有非延迟bean已经完成的初始化。
     *
     * 如果不使用 {@link ApplicationContext} 而只使用 {@link BeanFactory}
     * 则需要显示的调用方法 {@link DefaultListableBeanFactory#preInstantiateSingletons()}
     *
     * {@link SmartInitializingSingleton}通常在Spring ApplicationContext场景中使用
     * @see SmartInitializingSingleton
     */
    @Test
    public void afterSingletonsInstantiatedByBeanFactory() {
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

    /**
     * <h2>运行结果：</h2>
     * SmartInitializingSingleton#afterSingletonsInstantiated方法执行...
     * 查询结果为：FinishInitializationDomain{id=101, name='null'}
     *
     * <h2>调用顺序如下：</h2>
     * {@link AbstractApplicationContext#refresh}
     * {@link AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory)}
     * {@link DefaultListableBeanFactory#preInstantiateSingletons}
     */
    @Test
    public void afterSingletonsInstantiatedByApplicationContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(FinishInitializationDomain.class);
        applicationContext.refresh();
        // 依赖查找
        FinishInitializationDomain bean = applicationContext.getBean(FinishInitializationDomain.class);
        System.out.println("查询结果为：" + bean);
        applicationContext.close();
    }
}