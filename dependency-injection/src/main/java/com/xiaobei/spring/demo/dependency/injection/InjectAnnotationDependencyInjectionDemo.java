package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.QualifierDomain;
import org.junit.Test;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.inject.Inject;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-06 10:16:16
 */
public class InjectAnnotationDependencyInjectionDemo {

    /**
     * <h2>{@link Inject inject} 注解的处理过程</h2>
     * 处理元信息：{@link AutowiredAnnotationBeanPostProcessor#postProcessMergedBeanDefinition(RootBeanDefinition, Class, String)}
     * ......
     * {@link AutowiredAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)}
     * {@code InjectionMetadata metadata = findAutowiringMetadata(beanName, bean.getClass(), pvs);}
     * {@link AutowiredAnnotationBeanPostProcessor#findAutowiringMetadata(String, Class, PropertyValues)}
     * {@code metadata = buildAutowiringMetadata(clazz);}
     * {@link AutowiredAnnotationBeanPostProcessor#buildAutowiringMetadata(java.lang.Class)}
     * {@code MergedAnnotation<?> ann = findAutowiredAnnotation(field);}
     * {@link AutowiredAnnotationBeanPostProcessor#findAutowiredAnnotation(java.lang.reflect.AccessibleObject)}
     * 中包会按照先后顺序对 {@link Autowired @Autowired}、{@link Value @Value} 及 {@link Inject @Inject} 注解的处理
     * {@code metadata.inject(bean, beanName, pvs);}
     * {@link InjectionMetadata#inject(Object, String, PropertyValues)}
     * {@code element.inject(target, beanName, pvs);}
     * {@link AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject(Object, String, PropertyValues)}
     * {@code value = beanFactory.resolveDependency(desc, beanName, autowiredBeanNames, typeConverter);}
     *   其中详情的处理过程参见：{@link DependencyProcessDemo#dependencyAutowiredIncludePrimaryBean()}
     * {@code ReflectionUtils.makeAccessible(field); field.set(bean, value);} 利用反射进行字段的设置
     *
     * 如果JSR-330存在于ClassPath中，复用{@link AutowiredAnnotationBeanPostProcessor}实现
     * {@link javax.inject.Inject}和{@link Autowired} 一样可以被spring处理
     *
     * <h2>运行结果：</h2>
     * QualifierDomain{id=63, name='限定注入1'}
     */
    @Test
    public void injectAnnotationDependencyInjection() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(InjectAnnotationConfig.class);
        applicationContext.refresh();
        InjectAnnotationConfig bean = applicationContext.getBean(InjectAnnotationConfig.class);
        // 获取JSR-330中的{@link Inject}注解的依赖注入
        System.out.println(bean.injectDomain);
        applicationContext.close();
    }

    static class InjectAnnotationConfig {

        @Inject
        private QualifierDomain injectDomain;

        @Bean
        @Primary
        public QualifierDomain domain() {
            return new QualifierDomain().setId(63).setName("限定注入1");
        }

        @Bean
        public QualifierDomain domain2() {
            return new QualifierDomain().setId(63).setName("限定注入2");
        }
    }

}