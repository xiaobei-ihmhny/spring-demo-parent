package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.User;
import org.junit.Test;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.xml.ws.WebServiceRef;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/9/10 22:31
 */
public class CommonAnnotationBeanPostProcessorDemo {

    /**
     * <h2>Java通用注解的注入原理</h2>
     * {@link CommonAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)}
     * {@link CommonAnnotationBeanPostProcessor#postProcessMergedBeanDefinition(RootBeanDefinition, Class, String)}
     * ......
     * {@link CommonAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)}
     * {@link CommonAnnotationBeanPostProcessor#findResourceMetadata(String, Class, PropertyValues)}
     * {@link CommonAnnotationBeanPostProcessor#buildResourceMetadata(java.lang.Class)}
     * 中含有对注解 {@link WebServiceRef @WebServiceRef}、{@link EJB @EJB}、{@link Resource @Resource}在字段及方法上的处理逻辑
     *
     * {@link InjectionMetadata#inject(Object, String, PropertyValues)}
     *   对 EJB的处理 ：{@link CommonAnnotationBeanPostProcessor.EjbRefElement#getResourceToInject(java.lang.Object, java.lang.String)}
     *   对 WebServiceRef的处理：{@link CommonAnnotationBeanPostProcessor.WebServiceRefElement#getResourceToInject(java.lang.Object, java.lang.String)}
     *   对 Resource的处理：{@link CommonAnnotationBeanPostProcessor.ResourceElement#getResourceToInject(java.lang.Object, java.lang.String)}
     * ......
     * 无论哪种，最终都会进入到 {@link CommonAnnotationBeanPostProcessor#getResource(CommonAnnotationBeanPostProcessor.LookupElement, java.lang.String)}
     * {@link CommonAnnotationBeanPostProcessor#autowireResource(BeanFactory, CommonAnnotationBeanPostProcessor.LookupElement, java.lang.String)}
     * 最终都会进入到依赖处理过程：{@link DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, java.lang.String, java.util.Set, TypeConverter)}
     *
     * 之后就和 {@link DependencyProcessDemo#dependencyAutowiredIncludePrimaryBean()} 依赖处理过程一样了
     */
    @Test
    public void commonAnnotationForInjection() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(CommonAnnotationConfig.class);
        applicationContext.refresh();
        CommonAnnotationConfig bean = applicationContext.getBean(CommonAnnotationConfig.class);
        System.out.println("bean.webServiceRefUser：" + bean.webServiceRefUser);
        System.out.println("bean.ejbUser：" + bean.ejbUser);
        System.out.println("bean.resourceUser：" + bean.resourceUser);
        applicationContext.close();
    }

    @Configuration
    static class CommonAnnotationConfig {

        /**
         * TODO 待完善，该注解 {@link WebServiceRef @WebServiceRef} 的具体使用方式
         */
//        @WebServiceRef(wsdlLocation = "http://localhost:8080/ws/MyOtherService/the.wsdl", value = Service.class)
        public User webServiceRefUser;

        /**
         * TODO 待完善，该注解 {@link EJB @EJB} 的具体使用方式
         */
//        @EJB(name = "user")
        public User ejbUser;

        @Resource
        public User resourceUser;

        @Bean
        public User user() {
            return new User().setAge(20200910).setName("naTie");
        }

    }

    /**
     * TODO 细节完善！！！！
     * <h2>处理过程</h2>
     * 初始化过程：{@link InitDestroyAnnotationBeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)}
     * 销毁过程：{@link InitDestroyAnnotationBeanPostProcessor#postProcessBeforeDestruction(java.lang.Object, java.lang.String)}
     */
    @Test
    public void commonAnnotationForLifeCycle() {

    }
}
