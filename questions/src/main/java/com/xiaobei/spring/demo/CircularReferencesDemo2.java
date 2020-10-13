package com.xiaobei.spring.demo;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-10-13 15:55:55
 */
public class CircularReferencesDemo2 {

    /**
     * 循环依赖处理过程：
     * 1. 获取 student 实例：
     * {@link AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)}
     *  {@link DefaultSingletonBeanRegistry#getSingleton(java.lang.String, boolean)}
     *  此时 {@link DefaultSingletonBeanRegistry#singletonObjects} 中并没有 student 实例，返回 null
     *  接着创建 student 实例：{@code sharedInstance = getSingleton(beanName, () -> {return createBean(beanName, mbd, args);}}
     *   {@link DefaultSingletonBeanRegistry#getSingleton(java.lang.String, org.springframework.beans.factory.ObjectFactory)}
     *   {@link AbstractAutowireCapableBeanFactory#createBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     *    {@code Object beanInstance = doCreateBean(beanName, mbdToUse, args);}
     *    {@link AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     *    当 student 实例创建成功后，开始初始化 classRoom 属性：{@code populateBean(beanName, mbd, instanceWrapper);}
     *    {@link AbstractAutowireCapableBeanFactory#populateBean(java.lang.String, RootBeanDefinition, BeanWrapper)}
     *    ...
     *    此时需要获取 classRoom 实例 {@link AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)}
     *     {@link DefaultSingletonBeanRegistry#getSingleton(java.lang.String, boolean)}
     *     此时 {@link DefaultSingletonBeanRegistry#singletonObjects} 中并没有 classRoom 实例，返回 null
     *     接着创建 classRoom 实例：{@code sharedInstance = getSingleton(beanName, () -> {return createBean(beanName, mbd, args);}}
     *      {@link DefaultSingletonBeanRegistry#getSingleton(java.lang.String, org.springframework.beans.factory.ObjectFactory)}
     *      {@link AbstractAutowireCapableBeanFactory#createBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     *      {@code Object beanInstance = doCreateBean(beanName, mbdToUse, args);}
     *      当 classRoom 实例创建成功后，开始初始化 student 属性：{@code populateBean(beanName, mbd, instanceWrapper);}
     *      {@link AbstractAutowireCapableBeanFactory#populateBean(java.lang.String, RootBeanDefinition, BeanWrapper)}
     *      {@link DefaultSingletonBeanRegistry#getSingleton(java.lang.String, boolean)}
     *      此时的 beanName = "student" 且 {@code if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) 为真}
     *      从 {@link DefaultSingletonBeanRegistry#singletonFactories} 中获取到 student 的代理对象，通过代理对象获取到未初始化的 student 实例，
     *      此时 student 中的 classRoom 实例仍然为 null。把该实例存入：{@link DefaultSingletonBeanRegistry#earlySingletonObjects} 中，并将该实例
     *      从 {@link DefaultSingletonBeanRegistry#singletonFactories} 中移除。
     *     此时 {@code Object sharedInstance = getSingleton(beanName);} 获取到相应的 student 实例
     *     {@code bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);}
     *     执行到 {@code return super.getObjectForBeanInstance(beanInstance, name, beanName, mbd);} 至此，classRoom 实例设置完成
     *     ......
     * @param args
     */
    public static void main(String[] args) {

    }
}