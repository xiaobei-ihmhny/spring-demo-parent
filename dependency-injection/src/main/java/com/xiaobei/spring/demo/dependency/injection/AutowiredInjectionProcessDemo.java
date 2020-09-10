package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.User;
import org.junit.Test;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/9/10 20:17
 */
public class AutowiredInjectionProcessDemo {

    /**
     * TODO xml方式的 {@link Autowired autowired} 处理
     */
    @Test
    public void autowiredInjectionWithXml() {

    }

    /**
     * <h2>autowired 注入规则及原理</h2>
     * 处理元信息：{@link AutowiredAnnotationBeanPostProcessor#postProcessMergedBeanDefinition(RootBeanDefinition, Class, String)}
     * ......
     * {@link AutowiredAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)}
     * {@code metadata.inject(bean, beanName, pvs);}
     * {@link InjectionMetadata#inject(Object, String, PropertyValues)}
     * {@code element.inject(target, beanName, pvs);}
     * {@link AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject(Object, String, PropertyValues)}
     * {@code value = beanFactory.resolveDependency(desc, beanName, autowiredBeanNames, typeConverter);}
     *   其中详情的处理过程参见：{@link DependencyProcessDemo#dependencyAutowiredIncludePrimaryBean()}
     * {@code ReflectionUtils.makeAccessible(field); field.set(bean, value);} 利用反射进行字段的设置
     *
     * <h2>运行结果：</h2>
     * User{age=20200910, name='naTie'}
     */
    @Test
    public void autowiredInjectionWithAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AutowiredAnnotationConfig.class);
        applicationContext.refresh();
        AutowiredAnnotationConfig bean = applicationContext.getBean(AutowiredAnnotationConfig.class);
        System.out.println(bean.user);
        applicationContext.close();
    }

    @Configuration
    static class AutowiredAnnotationConfig {

        @Autowired
        private User user;

        @Bean
        public User user() {
            return new User().setAge(20200910).setName("naTie");
        }

    }


}
