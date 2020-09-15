package com.xiaobei.spring.demo.bean.lifecycle;

import com.xiaobei.spring.demo.bean.lifecycle.domain.City;
import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
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


    static class LifeCycleDomain {

        private Long id;

        private String name;

        private City city;

        public Long getId() {
            return id;
        }

        public LifeCycleDomain setId(Long id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public LifeCycleDomain setName(String name) {
            this.name = name;
            return this;
        }

        public City getCity() {
            return city;
        }

        public LifeCycleDomain setCity(City city) {
            this.city = city;
            return this;
        }

        @Override
        public String toString() {
            return "LifeCycleDomain{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", city=" + city +
                    '}';
        }
    }

    static class SuperLifeCycleDomain extends LifeCycleDomain {

        private String child;

        private Long number;

        public String getChild() {
            return child;
        }

        public SuperLifeCycleDomain setChild(String child) {
            this.child = child;
            return this;
        }

        public Long getNumber() {
            return number;
        }

        public SuperLifeCycleDomain setNumber(Long number) {
            this.number = number;
            return this;
        }

        @Override
        public String toString() {
            return "SuperLifeCycleDomain{" +
                    "child='" + child + '\'' +
                    ", number=" + number +
                    "} " + super.toString();
        }
    }

    /**
     * <p>Spring Bean 实例化前阶段</p>
     * 整个调用过程：
     * {@link AbstractBeanFactory#getBean(java.lang.String, java.lang.Class)}
     * {@link AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)}
     * {@link AbstractBeanFactory#createBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     * {@link AbstractAutowireCapableBeanFactory#createBean(String, RootBeanDefinition, Object[])}
     * {@link AbstractAutowireCapableBeanFactory#resolveBeforeInstantiation(java.lang.String, RootBeanDefinition)}
     * {@link AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInstantiation(java.lang.Class, java.lang.String)}
     * <p>运行结果：</p>
     * LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     * SuperLifeCycleDomain{child='null', number=null} LifeCycleDomain{id=93, name='null', city=null}
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
        String location = "META-INF/life-cycle-instantiation-domain.xml";
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(location);
        // 进行依赖查找
        LifeCycleDomain lifeCycleDomain = beanFactory.getBean("lifeCycleDomain", LifeCycleDomain.class);
        System.out.println(lifeCycleDomain);
        LifeCycleDomain superLifeCycle = beanFactory.getBean("superLifeCycle", LifeCycleDomain.class);
        System.out.println(superLifeCycle);

    }

    /**
     * TODO 待完善
     * <p>Bean的实例化阶段</p>
     * 整个调用链路如下：
     *
     * {@link AbstractBeanFactory#getBean(java.lang.String, java.lang.Class)}
     * {@link AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)}
     * {@link AbstractBeanFactory#createBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     * {@link AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, RootBeanDefinition, Object[])}
     * {@code instanceWrapper = createBeanInstance(beanName, mbd, args);}
     * {@link AbstractAutowireCapableBeanFactory#createBeanInstance(String, RootBeanDefinition, Object[])}
     *  {@link } 特殊处理
     *  {@code instantiateBean(beanName, mbd);} 一般处理
     *  {@link AbstractAutowireCapableBeanFactory#instantiateBean(String, RootBeanDefinition)}
     */
    @Test
    public void instantiation() {
        //......
    }

    /**
     * <p>Spring Bean 实例化后阶段</p>
     * 整个调用链路如下：
     * @see AbstractBeanFactory#getBean(java.lang.String, java.lang.Class)
     * @see AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)
     * @see AbstractBeanFactory#createBean(java.lang.String, RootBeanDefinition, java.lang.Object[])
     * @see AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, RootBeanDefinition, java.lang.Object[])
     * @see AbstractAutowireCapableBeanFactory#populateBean(java.lang.String, RootBeanDefinition, org.springframework.beans.BeanWrapper)
     * @see InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation(java.lang.Object, java.lang.String)
     * <h2>运行结果：</h2>
     *
     * <h3>未添加实例化后的处理结果：</h3>
     * LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     * SuperLifeCycleDomain{child='子类', number=null} LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     *
     * <h3>添加实例化后的处理结果：</h3>
     * LifeCycleDomain{id=95, name='null', city=null}
     * SuperLifeCycleDomain{child='null', number=null} LifeCycleDomain{id=null, name='null', city=null}
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
        String location = "META-INF/life-cycle-instantiation-domain.xml";
        reader.loadBeanDefinitions(location);
        // 进行依赖查找
        LifeCycleDomain lifeCycleDomain = beanFactory.getBean("lifeCycleDomain", LifeCycleDomain.class);
        System.out.println(lifeCycleDomain);
        LifeCycleDomain superLifeCycle = beanFactory.getBean("superLifeCycle", LifeCycleDomain.class);
        System.out.println(superLifeCycle);
    }

    /**
     * <p>Spring Bean 属性赋值前阶段</p>
     * <h3>注意：</h3>
     * 若一个bean已经配置了{@link InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation(java.lang.Object, java.lang.String)}
     * 或者配置了{@link InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation(java.lang.Class, java.lang.String)}时
     * 则方法{@link InstantiationAwareBeanPostProcessor#postProcessProperties(PropertyValues, java.lang.Object, java.lang.String)}将不再执行
     *
     * {@link AbstractBeanFactory#getBean(java.lang.String, java.lang.Class)}
     * {@link AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)}
     * {@link AbstractBeanFactory#createBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     * {@link AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, RootBeanDefinition, Object[])}
     * {@link AbstractAutowireCapableBeanFactory#populateBean(String, RootBeanDefinition, BeanWrapper)}
     * 实际进行属性赋值的地方：{@link AbstractAutowireCapableBeanFactory#applyPropertyValues(String, BeanDefinition, BeanWrapper, PropertyValues)}
     *
     * @see AbstractAutowireCapableBeanFactory#applyPropertyValues(String, BeanDefinition, BeanWrapper, PropertyValues)
     * @see InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation(java.lang.Class, java.lang.String)
     * @see InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation(java.lang.Object, java.lang.String)
     * @see InstantiationAwareBeanPostProcessor#postProcessProperties(PropertyValues, java.lang.Object, java.lang.String)
     *
     * <h2>运行结果：</h2>
     *
     * <h3>未添加实例化后的处理结果：</h3>
     * LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     * SuperLifeCycleDomain{child='子类', number=null} LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     *
     * <h3>添加实例化后的处理结果：</h3>
     * LifeCycleDomain{id=2, name='XML配置', city=SHANGHAI}
     * SuperLifeCycleDomain{child='子类', number=111} LifeCycleDomain{id=2, name='XML配置', city=SHANGHAI}
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
        String location = "META-INF/life-cycle-instantiation-domain.xml";
        reader.loadBeanDefinitions(location);
        // 进行依赖查找
        LifeCycleDomain lifeCycleDomain = beanFactory.getBean("lifeCycleDomain", LifeCycleDomain.class);
        System.out.println(lifeCycleDomain);
        LifeCycleDomain superLifeCycle = beanFactory.getBean("superLifeCycle", LifeCycleDomain.class);
        System.out.println(superLifeCycle);
    }
}
