package com.xiaobei.spring.demo.bean.lifecycle;

import com.xiaobei.spring.demo.bean.lifecycle.domain.LifeCycleDomain;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-12 08:58:58
 */
@SuppressWarnings("DuplicatedCode")
public class BeanInitializationLifecycleDemo {

    /**
     * 不执行 {@code beanFactory.addBeanPostProcessor}的运行结果为：
     * LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     * SuperLifeCycleDomain{child='子类', number=null} LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     *
     * 执行 {@code beanFactory.addBeanPostProcessor}的运行结果为：
     * LifeCycleDomain{id=98, name='XML配置', city=BIEJING}
     * SuperLifeCycleDomain{child='子类', number=null} LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     *
     * @see AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, RootBeanDefinition, java.lang.Object[])
     * 其中的 {@code exposedObject = initializeBean(beanName, exposedObject, mbd);}
     * @see AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, RootBeanDefinition)
     * 其中的 {@code wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);}
     * @see AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization(java.lang.Object, java.lang.String)
     *
     * @see AbstractBeanDefinition#synthetic 返回此bean定义是否是“合成的”，即不是由应用程序本身定义的。默认情况下是false
     *
     * @apiNote 此阶段要晚于 {@link InstantiationAwareBeanPostProcessor}的各个阶段
     */
    @Test
    public void postProcessBeforeInitialization() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        String location = "META-INF/life-cycle-domain.xml";
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
        LifeCycleDomain superLifeCycle = beanFactory.getBean("superLifeCycle", LifeCycleDomain.class);
        System.out.println(superLifeCycle);
    }

    /**
     * 99 Spring Bean初始化阶段：@PostConstruct、InitializingBean以及自定义方法
     * 1. 需要在 {@code META-INF/life-cycle-domain.xml} 文件中添加 init-method="init"，来激活init自定义初始化方法
     * 2. 需要在当前的 {@code beanFactory}中添加 {@link CommonAnnotationBeanPostProcessor} 使其具有解析 {@link PostConstruct}的能力
     *
     * 运行结果：
     * postConstruct() -> LifeCycleDomain{id=2, name='xiaobei-ihmhny', city=BIEJING}
     * afterPropertiesSet() -> LifeCycleDomain{id=2, name='xiaobei', city=BIEJING}
     * postConstruct() -> LifeCycleDomain{id=2, name='xiaobei_ihmhny', city=BIEJING}
     * 最终获得的 lifeCycleDomain为：LifeCycleDomain{id=2, name='xiaobei_ihmhny', city=BIEJING}
     *
     * @see AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, RootBeanDefinition)
     * 其中在 {@code wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);}中完成对 {@link PostConstruct}的解析
     * @see AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization(java.lang.Object, java.lang.String)
     * 其中的 {@code Object current = processor.postProcessBeforeInitialization(result, beanName);}
     * @see InitDestroyAnnotationBeanPostProcessor#buildLifecycleMetadata(java.lang.Class)
     * 其中的{@code if (this.initAnnotationType != null && method.isAnnotationPresent(this.initAnnotationType))}
     * @see InitDestroyAnnotationBeanPostProcessor.LifecycleMetadata#invokeInitMethods(java.lang.Object, java.lang.String)
     * @see InitDestroyAnnotationBeanPostProcessor.LifecycleElement#invoke(java.lang.Object)
     *
     * @see PostConstruct
     * @see CommonAnnotationBeanPostProcessor
     */
    @Test
    public void initialization() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        String location = "META-INF/life-cycle-domain.xml";
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(location);
        // 添加注解驱动支持
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());
        // 依赖查找
        LifeCycleDomain lifeCycleDomain = beanFactory.getBean("lifeCycleDomain", LifeCycleDomain.class);
        System.out.println("最终获得的 lifeCycleDomain为：" + lifeCycleDomain);
    }
}