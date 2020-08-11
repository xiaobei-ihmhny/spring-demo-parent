package com.xiaobei.spring.demo.bean.lifecycle;

import com.xiaobei.spring.demo.bean.lifecycle.domain.LifeCycleDomain;
import com.xiaobei.spring.demo.bean.lifecycle.domain.SuperLifeCycleDomain;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.util.ObjectUtils;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/9 21:05
 */
public class BeanInstantiationLifecycleDemo {

    /**
     * Spring Bean 实例化前阶段
     * 整个调用过程：
     * {@link AbstractBeanFactory#getBean(java.lang.String, java.lang.Class)}
     * {@link AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)}
     * {@link AbstractBeanFactory#createBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     * {@link AbstractAutowireCapableBeanFactory#resolveBeforeInstantiation(java.lang.String, RootBeanDefinition)}
     * {@link AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInstantiation(java.lang.Class, java.lang.String)}
     * 运行结果：
     * <p>
     * LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     * SuperLifeCycleDomain{child='null'} LifeCycleDomain{id=93, name='null', city=null}
     *
     * @param args
     */
    @Test
    public void postProcessBeforeInstantiation() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 添加 {@link BeanPostProcessor} 的实例
        beanFactory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
            @Override
            public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
                if (ObjectUtils.nullSafeEquals("superLifeCycle", beanName) && SuperLifeCycleDomain.class.equals(beanClass)) {
                    return new SuperLifeCycleDomain().setId(93L);
                }
                return null;
            }
        });
        String location = "META-INF/life-cycle-domain.xml";
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(location);
        // 进行依赖查找
        LifeCycleDomain lifeCycleDomain = beanFactory.getBean("lifeCycleDomain", LifeCycleDomain.class);
        System.out.println(lifeCycleDomain);
        LifeCycleDomain superLifeCycle = beanFactory.getBean("superLifeCycle", LifeCycleDomain.class);
        System.out.println(superLifeCycle);

    }

    /**
     * Spring Bean 实例化后阶段
     *
     * 整个调用链路如下：
     *
     * @see AbstractBeanFactory#getBean(java.lang.String, java.lang.Class)
     * @see AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)
     * @see AbstractBeanFactory#createBean(java.lang.String, RootBeanDefinition, java.lang.Object[])
     * @see AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
     * @see AbstractAutowireCapableBeanFactory#populateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, org.springframework.beans.BeanWrapper)
     * @see InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation(java.lang.Object, java.lang.String)
     */
    @Test
    public void postProcessAfterInstantiation() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
            @Override
            public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
                if (ObjectUtils.nullSafeEquals("superLifeCycle", beanName)
                        && SuperLifeCycleDomain.class.equals(bean.getClass())) {
                    // 直接返回false时，对应的 "superLifeCycle"的bean将不会被赋值
                    return false;
                } else if (ObjectUtils.nullSafeEquals("lifeCycleDomain", beanName)
                        && LifeCycleDomain.class.equals(bean.getClass())) {
                    // 如果需要覆盖相应bean的默认值，可以直接配置
                    LifeCycleDomain lifeCycleDomain = (LifeCycleDomain) bean;
                    // 只配置相应bean的id为 95L，由于此时bean并没有被赋值，故其他属性均为null
                    lifeCycleDomain.setId(95L);
                    return false;
                }
                return true;
            }
        });
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "META-INF/life-cycle-domain.xml";
        reader.loadBeanDefinitions(location);
        // 进行依赖查找
        LifeCycleDomain lifeCycleDomain = beanFactory.getBean("lifeCycleDomain", LifeCycleDomain.class);
        System.out.println(lifeCycleDomain);
        LifeCycleDomain superLifeCycle = beanFactory.getBean("superLifeCycle", LifeCycleDomain.class);
        System.out.println(superLifeCycle);
    }
}
