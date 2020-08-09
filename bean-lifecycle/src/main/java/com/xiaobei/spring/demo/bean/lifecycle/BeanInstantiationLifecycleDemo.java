package com.xiaobei.spring.demo.bean.lifecycle;

import com.xiaobei.spring.demo.bean.lifecycle.domain.LifeCycleDomain;
import com.xiaobei.spring.demo.bean.lifecycle.domain.SuperLifeCycleDomain;
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
     *
     * 整个调用过程：
     * {@link AbstractBeanFactory#getBean(java.lang.String, java.lang.Class)}
     * {@link AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)}
     * {@link AbstractBeanFactory#createBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     * {@link AbstractAutowireCapableBeanFactory#resolveBeforeInstantiation(java.lang.String, RootBeanDefinition)}
     * {@link AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInstantiation(java.lang.Class, java.lang.String)}
     * 运行结果：
     *
     * LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     * SuperLifeCycleDomain{child='null'} LifeCycleDomain{id=93, name='null', city=null}
     * @param args
     */
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 添加 {@link BeanPostProcessor} 的实例
        beanFactory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
            @Override
            public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
                if(ObjectUtils.nullSafeEquals("superLifeCycle", beanName) && SuperLifeCycleDomain.class.equals(beanClass)) {
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
}
