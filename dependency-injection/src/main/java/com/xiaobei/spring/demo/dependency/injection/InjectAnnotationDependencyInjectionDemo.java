package com.xiaobei.spring.demo.dependency.injection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-06 10:16:16
 */
public class InjectAnnotationDependencyInjectionDemo {

    /**
     * 如果JSR-330存在于ClassPath中，复用{@link AutowiredAnnotationBeanPostProcessor}实现
     * {@link javax.inject.Inject}和{@link Autowired} 一样可以被spring处理
     */
    @Test
    public void injectAnnotationDependencyInjection() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(InjectAnnotationConfig.class);
        applicationContext.refresh();
        InjectAnnotationConfig bean = applicationContext.getBean(InjectAnnotationConfig.class);
        // 获取{@link Autowired}的依赖注入
        System.out.println(bean.getDomain());
        // 获取JSR-330中的{@link Inject}注解的依赖注入
        System.out.println(bean.getInjectDomain());
        applicationContext.close();
    }
}