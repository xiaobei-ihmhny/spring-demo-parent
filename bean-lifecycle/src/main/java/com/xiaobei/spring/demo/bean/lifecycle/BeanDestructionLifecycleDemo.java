package com.xiaobei.spring.demo.bean.lifecycle;

import com.xiaobei.spring.demo.bean.lifecycle.domain.DestructionDomain;
import com.xiaobei.spring.demo.bean.lifecycle.domain.DestructionDomainDestructionBeanPostProcessor;
import org.junit.Test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

import java.security.AccessControlContext;

/**
 * Bean的销毁阶段
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/12 20:14
 */
public class BeanDestructionLifecycleDemo {

    /**
     * Bean销毁前阶段
     *
     * 运行结果：
     * =============================================
     * DestructionDomain{id=1, name='销毁前的配置'}
     * 开始执行 lifeCycleDomain在销毁前的回调...
     * DestructionDomain{id=102, name='销毁前的配置'}
     * =============================================
     *
     * 调用流程：
     * =============================================
     * @see AbstractBeanFactory#destroyBean(java.lang.String, java.lang.Object)
     * @see AbstractBeanFactory#destroyBean(java.lang.String, java.lang.Object, RootBeanDefinition)
     * @see org.springframework.beans.factory.support.DisposableBeanAdapter#destroy
     * 在该方法的{@code ((DisposableBean) this.bean).destroy();}处，先调用 {@link DisposableBean#destroy()}
     * 再调用自定义销毁方法{@code invokeCustomDestroyMethod(this.destroyMethod);}
     * 其中注解{@link javax.annotation.PreDestroy}的调用是在
     * {@link InitDestroyAnnotationBeanPostProcessor#buildLifecycleMetadata(java.lang.Class)}中的
     * {@code currDestroyMethods.add(new LifecycleElement(method));}处，整个调用要早于前两种
     *
     * @see org.springframework.beans.factory.support.DisposableBeanAdapter#DisposableBeanAdapter(java.lang.Object, java.lang.String, RootBeanDefinition, AccessControlContext)
     * 在其最后一步{@code this.beanPostProcessors = filterPostProcessors(postProcessors, bean);}
     * @see org.springframework.beans.factory.support.DisposableBeanAdapter#filterPostProcessors(java.util.List, java.lang.Object)中的
     * {@code if (dabpp.requiresDestruction(bean))}中进行{@link javax.annotation.PreDestroy}的销毁
     * @see InitDestroyAnnotationBeanPostProcessor#requiresDestruction(java.lang.Object)
     * =============================================
     *
     * Bean销毁前阶段的方法回调位于：{@link DestructionAwareBeanPostProcessor#postProcessBeforeDestruction(java.lang.Object, java.lang.String)}
     *
     *
     * @see DestructionAwareBeanPostProcessor#postProcessBeforeDestruction(java.lang.Object, java.lang.String)
     *
     */
    @Test
    public void postProcessBeforeDestruction() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "META-INF/life-cycle-destruction-domain.xml";
        reader.loadBeanDefinitions(location);
        beanFactory.addBeanPostProcessor(new DestructionDomainDestructionBeanPostProcessor());
        // bean依赖查找
        DestructionDomain domain = beanFactory.getBean("destructionDomain", DestructionDomain.class);
        System.out.println("销毁前 " + domain);
        // 执行bean的销毁操作
        beanFactory.destroyBean("destructionDomain", domain);
        System.out.println("销毁后" + domain);
    }

    /**
     * Bean的销毁阶段
     *
     * 销毁前 DestructionDomain{id=1, name='销毁前的配置'}
     * 开始执行 lifeCycleDomain在销毁前的回调...
     * DestructionDomain{id=102, name='16-1'}
     * DestructionDomain{id=102, name='16-2'}
     * DestructionDomain{id=102, name='16-3'}
     * 销毁后DestructionDomain{id=102, name='16-3'}
     *
     * 具体过程参见：{@link #postProcessBeforeDestruction}
     */
    @Test
    public void postProcessDestruction() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "META-INF/life-cycle-destruction-domain.xml";
        reader.loadBeanDefinitions(location);
        // 执行销毁前的回调
        beanFactory.addBeanPostProcessor(new DestructionDomainDestructionBeanPostProcessor());
        // 处理 {@link PostConstruct}/{@link PreDestroy}注解
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());
        // bean依赖查找
        DestructionDomain domain = beanFactory.getBean("destructionDomain", DestructionDomain.class);
        System.out.println("销毁前 " + domain);
        // 执行bean的销毁操作
        beanFactory.destroyBean("destructionDomain", domain);
        System.out.println("销毁后" + domain);
    }
}
