package com.xiaobei.spring.demo.bean.lifecycle;

import com.xiaobei.spring.demo.bean.lifecycle.domain.LifeCycleDomain;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/9 19:50
 */
public class MergedBeanDefinitionDemo {

    /**
     *
     * @see DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, java.lang.String, java.util.Set, TypeConverter)
     * @see ConfigurableBeanFactory#getMergedBeanDefinition(java.lang.String)
     * @see AbstractBeanFactory#getMergedBeanDefinition(java.lang.String)
     *
     * TODO 需要再读！！！
     * 实际执行操作逻辑的位置：
     * @see AbstractBeanFactory#getMergedBeanDefinition(java.lang.String, BeanDefinition, BeanDefinition)
     *
     * 运行结果：
     *
     * LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     * SuperLifeCycleDomain{child='子类'} LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     * @param args
     */
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "META-INF/life-cycle-domain.xml";
        reader.loadBeanDefinitions(location);
        // 依赖查找
        LifeCycleDomain lifeCycleDomain = beanFactory.getBean("lifeCycleDomain", LifeCycleDomain.class);
        System.out.println(lifeCycleDomain);
        LifeCycleDomain superLifeCycle = beanFactory.getBean("superLifeCycle", LifeCycleDomain.class);
        System.out.println(superLifeCycle);
    }
}
