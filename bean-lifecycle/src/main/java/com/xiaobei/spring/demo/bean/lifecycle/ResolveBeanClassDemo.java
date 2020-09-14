package com.xiaobei.spring.demo.bean.lifecycle;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.ResolvableType;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-09-14 15:04:04
 */
public class ResolveBeanClassDemo {

    /**
     * <h2>{@link DefaultListableBeanFactory#getBean(java.lang.Class) getBean} 时，涉及到的 Class 加载的详细流程</h2>
     * {@link DefaultListableBeanFactory#getBean(java.lang.Class)}
     *  {@link DefaultListableBeanFactory#getBean(java.lang.Class, java.lang.Object...)}
     *   {@link DefaultListableBeanFactory#resolveBean(ResolvableType, Object[], boolean)}
     *    {@link DefaultListableBeanFactory#resolveNamedBean(ResolvableType, Object[], boolean)}
     *     {@code return new NamedBeanHolder<>(beanName, (T) getBean(beanName, requiredType.toClass(), args));}
     *     {@link AbstractBeanFactory#getBean(String, Class, Object...)}
     *      {@link AbstractBeanFactory#doGetBean(String, Class, Object[], boolean)}
     *       {@link DefaultSingletonBeanRegistry#getSingleton(String, ObjectFactory)}
     *        {@code singletonObject = singletonFactory.getObject();}
     *       {@link AbstractAutowireCapableBeanFactory#createBean(String, RootBeanDefinition, Object[])}
     *        {@code Class<?> resolvedClass = resolveBeanClass(mbd, beanName);}
     *        {@link AbstractBeanFactory#resolveBeanClass(RootBeanDefinition, String, Class[])}
     *         {@link AbstractBeanFactory#doResolveBeanClass(RootBeanDefinition, Class[])}
     *          {@link AbstractBeanDefinition#resolveBeanClass(java.lang.ClassLoader)}
     *           利用 {@code ClassUtils.forName(className, classLoader);} 将 String 类型的 Class 转换为真正的 Class
     *           并赋值给 {@link AbstractBeanDefinition#beanClass} 完成 Class 加载
     * @param args
     */
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader  = new XmlBeanDefinitionReader(beanFactory);
        String location = "META-INF/life-cycle-metadata-domain.xml";
        reader.loadBeanDefinitions(location);
        // 依赖查找
        BeanMetadataConfigurationDemo.LifeCycleDomain lifeCycleDomain
                = beanFactory.getBean(BeanMetadataConfigurationDemo.LifeCycleDomain.class);
        System.out.println(lifeCycleDomain);
    }
}