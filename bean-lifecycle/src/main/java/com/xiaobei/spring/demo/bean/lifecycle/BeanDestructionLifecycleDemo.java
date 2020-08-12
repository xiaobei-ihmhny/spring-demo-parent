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

import java.security.AccessControlContext;

/**
 * Bean的销毁阶段
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/12 20:14
 */
public class BeanDestructionLifecycleDemo {

    /**
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
        String location = "META-INF/life-cycle-before-destruction-domain.xml";
        reader.loadBeanDefinitions(location);
        beanFactory.addBeanPostProcessor(new DestructionDomainDestructionBeanPostProcessor());
        // bean依赖查找
        DestructionDomain domain = beanFactory.getBean("beforeDestructionDomain", DestructionDomain.class);
        System.out.println(domain);
        // 执行bean的销毁操作
        beanFactory.destroyBean("beforeDestructionDomain", domain);
        System.out.println(domain);
    }
}
