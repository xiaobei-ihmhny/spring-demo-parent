package com.xiaobei.spring.demo.bean.lifecycle;

import com.xiaobei.spring.demo.bean.lifecycle.domain.City;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-12 08:58:58
 */
@SuppressWarnings("DuplicatedCode")
public class BeanInitializationLifecycleDemo {


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

    /**
     *
     * <h2>运行结果：</h2>
     *
     * <h3>未添加实例化后的处理结果：</h3>
     * LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     *
     * <h3>添加实例化后的处理结果：</h3>
     * LifeCycleDomain{id=98, name='XML配置', city=BIEJING}
     *
     * <h2>Spring Bean 初始化前阶段的详情流程</h2>
     * {@link AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     *  {@code exposedObject = initializeBean(beanName, exposedObject, mbd);}
     *  {@link AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, RootBeanDefinition)}
     *   {@code wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);}
     *   {@link AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization(java.lang.Object, java.lang.String)}
     *
     * @see AbstractBeanDefinition#synthetic 返回此bean定义是否是“合成的”，即不是由应用程序本身定义的。默认情况下是false
     *
     * @apiNote 此阶段要晚于 {@link InstantiationAwareBeanPostProcessor}的各个阶段
     */
    @Test
    public void postProcessBeforeInitialization() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        String location = "META-INF/life-cycle-initialization-domain.xml";
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(location);
        // 查找之前添加BeanPostProcessor处理
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                // 拦截 id="lifeCycleDomain" 的 bean，并做一定的修改
                if(ObjectUtils.nullSafeEquals("lifeCycleDomain", beanName)
                        && LifeCycleDomain.class.equals(bean.getClass())) {
                    // 修改相应bean中的信息
                    LifeCycleDomain domain = (LifeCycleDomain) bean;
                    // 将bean中的id属性设置为98L
                    domain.setId(98L);
                    return domain;
                }
                return null;
            }
        });
        // 依赖查找
        LifeCycleDomain lifeCycleDomain = beanFactory.getBean("lifeCycleDomain", LifeCycleDomain.class);
        System.out.println(lifeCycleDomain);
    }

    static class LifeCycleInitializationDomain implements InitializingBean {

        private Long id;

        private String name;

        public Long getId() {
            return id;
        }

        public LifeCycleInitializationDomain setId(Long id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public LifeCycleInitializationDomain setName(String name) {
            this.name = name;
            return this;
        }


        @Override
        public String toString() {
            return "LifeCycleDomain{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        @PostConstruct
        public void postConstruct() {
            System.out.println("@PostConstruct 执行初始化...");
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("afterPropertiesSet() 执行初始化...");
        }

        public void init() {
            System.out.println("自定义初始化方法 init() ...");
        }
    }

    /**
     * 99 Spring Bean初始化阶段：@PostConstruct、InitializingBean以及自定义方法
     * 1. 需要在 {@code META-INF/life-cycle-initialization-domain.xml} 文件中添加 init-method="init"，来激活init自定义初始化方法
     * 2. 需要在当前的 {@code beanFactory}中添加 {@link CommonAnnotationBeanPostProcessor} 使其具有解析 {@link PostConstruct}的能力
     *
     * 运行结果：
     * @PostConstruct 执行初始化...
     * afterPropertiesSet() 执行初始化...
     * 自定义初始化方法 init() ...
     * 最终获得的 lifeCycleDomainIncludeInitMethod 为：LifeCycleDomain{id=2, name='XML配置'}
     *
     * {@link AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, RootBeanDefinition)}
     *  其中在 {@code wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);}中完成对 {@link PostConstruct}的解析
     *  {@link AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization(java.lang.Object, java.lang.String)}
     *   其中的 {@code Object current = processor.postProcessBeforeInitialization(result, beanName);}
     *   {@link InitDestroyAnnotationBeanPostProcessor#buildLifecycleMetadata(java.lang.Class)}
     *   其中的{@code if (this.initAnnotationType != null && method.isAnnotationPresent(this.initAnnotationType))}
     *    {@link InitDestroyAnnotationBeanPostProcessor.LifecycleMetadata#invokeInitMethods(java.lang.Object, java.lang.String)}
     *     {@link InitDestroyAnnotationBeanPostProcessor.LifecycleElement#invoke(java.lang.Object)}
     *
     * @see PostConstruct
     * @see CommonAnnotationBeanPostProcessor
     */
    @Test
    public void initialization() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        String location = "META-INF/life-cycle-initialization-domain.xml";
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(location);
        // 添加注解驱动支持
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());
        // 依赖查找
        LifeCycleInitializationDomain lifeCycleDomain = beanFactory.getBean("lifeCycleDomainIncludeInitMethod", LifeCycleInitializationDomain.class);
        System.out.println("最终获得的 lifeCycleDomainIncludeInitMethod 为：" + lifeCycleDomain);
    }

    /**
     *
     * <p>运行结果：</p>
     * lifeCycleDomain：LifeCycleDomain{id=100, name='xiaobei_ihmhny', city=BIEJING}
     *
     * <h2>具体调用流程如下：</h2>
     * {@link AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, RootBeanDefinition)}
     * 中的{@code wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);}对所有的after方法回调
     *
     *
     * <h2>总结：</h2>
     * {@link AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, RootBeanDefinition)}方法中，
     * 一共做了4件事件，按顺序依次是：
     * 1. Aware接口回调阶段 {@code invokeAwareMethods(beanName, bean);}
     * 2. 初始化前阶段 {@code wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);}，
     *   包括与 {@link ApplicationContext}相关的aware回调及{@link PostConstruct}的回调
     * 3. 初始化阶段 {@code invokeInitMethods(beanName, wrappedBean, mbd);}
     * 4. 初始化后阶段 {@code wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);}
     *
     * @see AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, RootBeanDefinition)
     */
    @Test
    public void postProcessAfterInitialization() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        String location = "META-INF/life-cycle-initialization-domain.xml";
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(location);
        // 查找之前添加BeanPostProcessor处理
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                // 拦截 id="lifeCycleDomain" 的 bean，并做一定的修改
                if(ObjectUtils.nullSafeEquals("lifeCycleDomain", beanName)
                        && LifeCycleDomain.class.equals(bean.getClass())) {
                    // 修改相应bean中的信息
                    LifeCycleDomain domain = (LifeCycleDomain) bean;
                    // 将bean中的id属性设置为98L
                    domain.setId(100L);
                    return domain;
                }
                return null;
            }
        });
        // 依赖查找
        LifeCycleDomain lifeCycleDomain = beanFactory.getBean("lifeCycleDomain", LifeCycleDomain.class);
        System.out.println("lifeCycleDomain：" + lifeCycleDomain);
    }
}