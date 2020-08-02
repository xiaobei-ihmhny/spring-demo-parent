package com.xiaobei.spring.demo.bean.definition.destroy;

import org.junit.Test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 9:43
 */
public class BeanDestroyDemo {

    /**
     * 通过 {@link javax.annotation.PreDestroy} 来定义销毁Bean的方法
     * spring启动完成...
     * {@code @PreDestroy：执行销毁bean的方法}
     * spring销毁完成
     */
    @Test
    public void destroyBeanByPreDestroy() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanDestroyByPreDestroy.class);
        applicationContext.refresh();
        System.out.println("spring启动完成...");
        applicationContext.close();
        System.out.println("spring销毁完成");
    }

    /**
     * 通过实现{@link DisposableBean#destroy()}来执行销毁bean的方法
     * spring启动完成...
     * DisposableBean#destroy：执行销毁bean的方法
     * spring销毁完成
     */
    @Test
    public void destroyBeanByDisposableBean() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanDestroyByDisposableBean.class);
        applicationContext.refresh();
        System.out.println("spring启动完成...");
        applicationContext.close();
        System.out.println("spring销毁完成");
    }

    /**
     * 通过 <bean destory="destroy" .../> 的方式实现bean的销毁
     * 信息为：通过xml中的bean标签实现bean的销毁
     * 通过xml中的bean标签实现bean的销毁：自定义bean的销毁方法
     * 信息为：通过xml中的bean标签实现bean的销毁
     * 应用上下文启动成功
     * 通过xml中的bean标签实现bean的销毁：自定义bean的销毁方法
     */
    @Test
    public void destroyBeanByCustomMethodWithXml() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("META-INF/bean-destroy-context.xml");
        applicationContext.refresh();
        System.out.println("应用上下文启动成功");
        applicationContext.close();
    }

    /**
     * 通过 {@code @Bean(destroyMethod = "destroyCustom")} 的方法来执行销毁bean
     * 信息为：销毁Bean方法：@Bean(destroyMethod="destroyCustom")
     * 销毁Bean方法：@Bean(destroyMethod="destroyCustom")：自定义bean的销毁方法
     */
    @Test
    public void destroyBeanByCustomMethodWithAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanDestroyByCustomMethodConfig.class);
        applicationContext.refresh();
        applicationContext.close();
    }

    /**
     * 通过 java api {@link AbstractBeanDefinition#setDestroyMethodName(java.lang.String)} 的方式指定销毁bean的方法
     *
     * 在spring 5.1+的版本上可以用 {@link BeanDefinition#setDestroyMethodName(java.lang.String)} 代替！
     *
     * 信息为：通过java api指定销毁bean的方法
     * 通过java api指定销毁bean的方法：自定义bean的销毁方法
     */
    @Test
    public void destroyBeanByApi() {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(BeanDestroyByCustomMethod.class);
        builder.addConstructorArgValue("通过java api指定销毁bean的方法");
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        beanDefinition.setDestroyMethodName("destroyCustom");
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, applicationContext);
        applicationContext.refresh();
        applicationContext.close();
    }

    /**
     * 比较三种不同方法，同时指定时的执行优先级
     *
     * {@code
     *   @PreDestroy：指定销毁bean的方法
     *   DisposableBean#destroy：指定销毁bean的方法
     *   自定义方法destroyCustom：指定销毁bean的方法
     * }
     */
    @Test
    public void destroyBeanCompare() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanDestroyVsConfig.class);
        applicationContext.refresh();
        applicationContext.close();
    }

}
