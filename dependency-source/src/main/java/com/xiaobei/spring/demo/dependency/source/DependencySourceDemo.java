package com.xiaobei.spring.demo.dependency.source;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

/**
 * 依赖来源示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/6 22:16
 */
public class DependencySourceDemo {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * <p>依赖查找的来源：BeanDefinition、单体对象
     * <p>依赖注入的来源：BeanDefinition、单体对象、ResolvableDependency
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(DependencySourceDemo.class);
        applicationContext.refresh();
        applicationContext.close();
    }

    /**
     * application == beanFactory：false
     * beanFactory == application.getBeanFactory：true
     * application == applicationEventPublisher：true
     * application == resourceLoader：true
     *
     * <h2>注意：</h2>
     * 其中 {@link ApplicationContext#getAutowireCapableBeanFactory()}
     * 在 {@link AbstractApplicationContext} 中的实现实际就是直接调用 {@link AbstractApplicationContext#getBeanFactory()}
     */
    @PostConstruct
    public void initByInject() {
        System.out.println("application == beanFactory：" + (applicationContext == beanFactory));
        System.out.println("beanFactory == application.getBeanFactory："
                + (beanFactory == applicationContext.getAutowireCapableBeanFactory()));
        System.out.println("application == applicationEventPublisher：" + (applicationContext == applicationEventPublisher));
        System.out.println("application == resourceLoader：" + (applicationContext == resourceLoader));
    }

    /**
     * No qualifying bean of type 'org.springframework.beans.factory.BeanFactory' available
     * No qualifying bean of type 'org.springframework.context.ApplicationContext' available
     * No qualifying bean of type 'org.springframework.context.ApplicationEventPublisher' available
     * No qualifying bean of type 'org.springframework.core.io.ResourceLoader' available
     */
    @PostConstruct
    public void initByLookup() {
        getBean(BeanFactory.class);
        getBean(ApplicationContext.class);
        getBean(ApplicationEventPublisher.class);
        getBean(ResourceLoader.class);
    }

    public <T> T getBean(Class<T> classType) {
        try {
            return beanFactory.getBean(classType);
        } catch (NoSuchBeanDefinitionException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
