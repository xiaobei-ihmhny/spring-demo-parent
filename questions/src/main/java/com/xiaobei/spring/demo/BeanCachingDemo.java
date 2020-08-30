package com.xiaobei.spring.demo;

import com.xiaobei.spring.demo.domain.QuestionsDomain;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.SessionScope;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/30 10:54
 */
public class BeanCachingDemo {

    /**
     * <h2>运行结果：</h2>
     * [bean：singletonDomain] 正在初始化 QuestionsDomain...
     * singletonDomain：true
     * singletonDomain：true
     * singletonDomain：true
     * [bean：prototypeDomain] 正在初始化 QuestionsDomain...
     * [bean：prototypeDomain] 正在初始化 QuestionsDomain...
     * prototypeDomain：false
     * [bean：prototypeDomain] 正在初始化 QuestionsDomain...
     * prototypeDomain：false
     * [bean：prototypeDomain] 正在初始化 QuestionsDomain...
     * prototypeDomain：false
     * [bean：singletonDomain] 正在销毁 QuestionsDomain...
     *
     * @param args
     * {@link AbstractApplicationContext#getBean(java.lang.String, java.lang.Class)}
     * {@link AbstractBeanFactory#getBean(java.lang.String, java.lang.Class) }
     * {@link AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)}
     *      {@code if (mbd.isSingleton())} 判断是单例对象时
     *          {@link DefaultSingletonBeanRegistry#getSingleton(java.lang.String, org.springframework.beans.factory.ObjectFactory)}
     *              {@link AbstractAutowireCapableBeanFactory#createBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     *      {@code else if (mbd.isPrototype())} 判断是原型对象时
     * {@link org.springframework.beans.factory.config.Scope}
     * {@link org.springframework.web.context.request.AbstractRequestAttributesScope}
     * {@link RequestScope}
     * {@link SessionScope}
     * {@link ServletRequestAttributes}
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanCachingDemo.class);
        applicationContext.refresh();
        // 单例 Bean
        QuestionsDomain singletonDomain = applicationContext.getBean("singletonDomain", QuestionsDomain.class);
        for (int i = 0; i < 3; i++) {
            System.out.println("singletonDomain：" +
                    (singletonDomain == applicationContext.getBean("singletonDomain", QuestionsDomain.class)));
        }

        QuestionsDomain prototypeDomain = applicationContext.getBean("prototypeDomain", QuestionsDomain.class);

        for (int i = 0; i < 3; i++) {
            System.out.println("prototypeDomain：" +
                    (prototypeDomain == applicationContext.getBean("prototypeDomain", QuestionsDomain.class)));
        }
        applicationContext.close();
    }

    @Bean
    public static QuestionsDomain singletonDomain() {
        QuestionsDomain domain = new QuestionsDomain();
        domain.setId(248);
        domain.setName("加餐2 单例Bean");
        return domain;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static QuestionsDomain prototypeDomain() {
        QuestionsDomain domain = new QuestionsDomain();
        domain.setId(248);
        domain.setName("加餐2 原型Bean");
        return domain;
    }
}
