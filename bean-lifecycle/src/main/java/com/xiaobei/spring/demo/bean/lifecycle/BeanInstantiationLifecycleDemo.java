package com.xiaobei.spring.demo.bean.lifecycle;

import com.xiaobei.spring.demo.bean.lifecycle.domain.LifeCycleDomain;
import com.xiaobei.spring.demo.bean.lifecycle.domain.SuperLifeCycleDomain;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
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
@SuppressWarnings("DuplicatedCode")
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

    /**
     * 未添加 {@code }时的运行结果为：
     * LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     * SuperLifeCycleDomain{child='子类', number=null} LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     *
     * 添加之后的运行结果为：
     * LifeCycleDomain{id=2, name='XML配置', city=SHANGHAI}
     * SuperLifeCycleDomain{child='子类', number=111} LifeCycleDomain{id=2, name='XML配置', city=SHANGHAI}
     *
     * <p> 说明：
     * 若一个bean已经配置了{@link InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation(java.lang.Object, java.lang.String)}
     * 或者配置了{@link InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation(java.lang.Class, java.lang.String)}时
     * 则方法{@link InstantiationAwareBeanPostProcessor#postProcessProperties(PropertyValues, java.lang.Object, java.lang.String)}将不再执行
     *
     * @see AbstractAutowireCapableBeanFactory#populateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, org.springframework.beans.BeanWrapper)
     * 实际进行属性赋值的地方：
     * @see AbstractAutowireCapableBeanFactory#applyPropertyValues(java.lang.String, org.springframework.beans.factory.config.BeanDefinition, org.springframework.beans.BeanWrapper, org.springframework.beans.PropertyValues)
     * @see InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation(java.lang.Class, java.lang.String)
     * @see InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation(java.lang.Object, java.lang.String)
     * @see InstantiationAwareBeanPostProcessor#postProcessProperties(PropertyValues, java.lang.Object, java.lang.String)
     */
    @Test
    public void postProcessProperties() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
            @Override
            public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
                final MutablePropertyValues propertyValues;
                if(pvs instanceof MutablePropertyValues) {
                    propertyValues = (MutablePropertyValues) pvs;
                } else {
                    propertyValues = new MutablePropertyValues();
                }
                if (ObjectUtils.nullSafeEquals("superLifeCycle", beanName)
                        && SuperLifeCycleDomain.class.equals(bean.getClass())) {
                    // 直接添加一个属性信息
                    String propertyName = "number";
                    propertyValues.addPropertyValue(propertyName,"111");
                    return propertyValues;
                } else if (ObjectUtils.nullSafeEquals("lifeCycleDomain", beanName)
                        && LifeCycleDomain.class.equals(bean.getClass())) {
                    // 先删除一个属性配置，
                    String propertyName = "city";
//                    propertyValues.removePropertyValue(propertyName);
                    // 再添加一个新的值
                    propertyValues.addPropertyValue(propertyName, "SHANGHAI");
                    return propertyValues;
                }
                return null;
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
