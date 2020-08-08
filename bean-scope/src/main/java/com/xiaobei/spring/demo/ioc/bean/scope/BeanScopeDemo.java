package com.xiaobei.spring.demo.ioc.bean.scope;

import com.xiaobei.spring.demo.ioc.bean.scope.config.BeanScopeDemoConfig;
import com.xiaobei.spring.demo.ioc.bean.scope.domain.ScopeDomain;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/8 18:45
 */
@SuppressWarnings("DuplicatedCode")
public class BeanScopeDemo {


    /**
     * 运行结果表示：
     * {@link ConfigurableBeanFactory#SCOPE_SINGLETON}类型的bean，依赖查找时，得到的都是同一个类型的bean
     * {@link ConfigurableBeanFactory#SCOPE_PROTOTYPE}类型的bean，依赖查找时，得到的都是新生成对象，
     * 且这种类型的Bean没有完整的生命周期（只有bean的初始化回调，没有bean的销毁回调）
     *
     * 运行结果：
     *
     * 当前bean [singletonDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * singletonDomain = ScopeDomain{id=1596885561971, name='', beanName='singletonDomain'}
     * 当前bean [prototypeDomain] 正在进行初始化...
     * prototypeDomain = ScopeDomain{id=1596885562030, name='', beanName='prototypeDomain'}
     * singletonDomain = ScopeDomain{id=1596885561971, name='', beanName='singletonDomain'}
     * 当前bean [prototypeDomain] 正在进行初始化...
     * prototypeDomain = ScopeDomain{id=1596885562096, name='', beanName='prototypeDomain'}
     * 当前bean [singletonDomain] 正在进行销毁...
     */
    @Test
    public void scopeBeanByLookup() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanScopeDemoConfig.class);
        applicationContext.refresh();
        for (int i = 0; i < 2; i++) {
            ScopeDomain singletonDomain = applicationContext.getBean("singletonDomain", ScopeDomain.class);
            System.out.println("singletonDomain = " + singletonDomain);
            ScopeDomain prototypeBean = applicationContext.getBean("prototypeDomain", ScopeDomain.class);
            System.out.println("prototypeDomain = " + prototypeBean);
        }
        applicationContext.close();
    }

    /**
     * 从运行结果看：
     * {@link ConfigurableBeanFactory#SCOPE_SINGLETON}类型的bean，依赖注入时，都是同一个类型的bean
     * {@link ConfigurableBeanFactory#SCOPE_PROTOTYPE}类型的bean，依赖注入时，均为新生成对象，
     * 且这种类型的Bean没有完整的生命周期（只有bean的初始化回调，没有bean的销毁回调），
     * 如果依赖注入集合对象，Singleton Bean 和 Prototype Bean均会存在一个，
     * 且集合中的Prototype Bean 有别于其他地方的依赖注入
     *
     * 运行结果：
     *
     * 当前bean [singletonDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * ScopeDomain{id=1596885053758, name='', beanName='singletonDomain'}
     * ScopeDomain{id=1596885053758, name='', beanName='singletonDomain'}
     * ScopeDomain{id=1596885053720, name='', beanName='prototypeDomain'}
     * ScopeDomain{id=1596885053774, name='', beanName='prototypeDomain'}
     * ScopeDomain{id=1596885053787, name='', beanName='prototypeDomain'}
     * {singletonDomain=ScopeDomain{id=1596885053758, name='', beanName='singletonDomain'}, prototypeDomain=ScopeDomain{id=1596885053763, name='', beanName='prototypeDomain'}}
     * 当前bean [singletonDomain] 正在进行销毁...
     */
    @Test
    public void scopeBeanByInject() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanScopeDemoConfig.class);
        applicationContext.refresh();
        BeanScopeDemoConfig bean = applicationContext.getBean(BeanScopeDemoConfig.class);
        System.out.println(bean.singletonDomain);
        System.out.println(bean.singletonDomain2);
        System.out.println(bean.prototypeDomain);
        System.out.println(bean.prototypeDomain2);
        System.out.println(bean.prototypeDomain3);
        System.out.println(bean.scopeDomains);
        applicationContext.close();
    }

    /**
     *
     * 若为java注解的方式，可以通过实现配置类的销毁方法，
     * 在其销毁方法中手动调用对应prototype类型的bean的销毁方法；
     * 若注入的是集合类型，比如{@link Map <String, Object>}，
     * 可以在配置类中注入非spring管理依赖中的{@link BeanFactory}，
     * 通过{@link BeanFactory}获取对应集合中的所有bean的
     * {@link ConfigurableListableBeanFactory#getBeanDefinition(java.lang.String)}方法
     * 获{@link BeanDefinition}并通过判断{@link BeanDefinition#isPrototype()}来筛选类型为：
     * {@link ConfigurableBeanFactory#SCOPE_PROTOTYPE}的bean后，再调用其销毁方法。
     *
     * 运行结果：
     *
     * 当前bean [singletonDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行销毁...
     * 当前bean [prototypeDomain] 正在进行销毁...
     * 当前bean [prototypeDomain] 正在进行销毁...
     * 当前bean [prototypeDomain] 正在进行销毁...
     * 当前bean [singletonDomain] 正在进行销毁...
     */
    @Test
    public void destroyPrototypeBean() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanScopeDemoConfig.class);
        applicationContext.refresh();
        applicationContext.close();
    }
}
