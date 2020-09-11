package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.annonation.CustomSource;
import com.xiaobei.spring.demo.dependency.annonation.Source;
import com.xiaobei.spring.demo.dependency.domain.QualifierDomain;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.inject.Inject;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-06 14:09:09
 */
public class CustomAnnotationDependencyInjectionDemo {

    /**
     * 自定义注解{@link Source}上直接使用{@link Autowired}作为元注解来
     *
     * <h2>运行结果：</h2>
     * @Autowired 注入：QualifierDomain{id=911, name='自定义依赖注入注解2'}
     * @Source 注入QualifierDomain{id=911, name='自定义依赖注入注解2'}
     *
     */
    @Test
    public void dependencyInjectionWithAutowiredMetaAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SourceAnnotationConfig.class);
        applicationContext.refresh();
        // 开始依赖查找
        SourceAnnotationConfig bean = applicationContext.getBean(SourceAnnotationConfig.class);
        System.out.println("@Autowired 注入：" + bean.domain);
        System.out.println("@Source 注入" + bean.myDomain);
        applicationContext.close();
    }

    static class SourceAnnotationConfig {

        @Autowired
        private QualifierDomain domain;

        /**
         * 其中 {@link Source @Source} 直接使用 {@link Autowired @Autowired} 进行原标注
         */
        @Source
        private QualifierDomain myDomain;

        @Bean
        public QualifierDomain domain() {
            return new QualifierDomain().setId(911).setName("自定义依赖注入注解");
        }

        @Bean
        @Primary
        public QualifierDomain domain2() {
            return new QualifierDomain().setId(911).setName("自定义依赖注入注解2");
        }

    }

    /**
     * 使用完全自定义注解{@link CustomSource}并配置自定义bean {@link AutowiredAnnotationBeanPostProcessor}
     * 来实现自定义依赖注入注解
     *
     * <h2>运行结果：</h2>
     * @Autowired 注入：QualifierDomain{id=911, name='自定义依赖注入注解2'}
     * @Inject 注入：QualifierDomain{id=911, name='自定义依赖注入注解2'}
     * @CustomSource 注入QualifierDomain{id=911, name='自定义依赖注入注解2'}
     */
    @Test
    public void dependencyInjectionWithCustomSourceAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(CustomSourceAnnotationConfig.class);
        applicationContext.refresh();
        // 开始依赖查找
        CustomSourceAnnotationConfig bean = applicationContext.getBean(CustomSourceAnnotationConfig.class);
        System.out.println("@Autowired 注入：" + bean.domain);
        System.out.println("@Inject 注入：" + bean.domain2);
        System.out.println("@CustomSource 注入" + bean.myDomain);
        applicationContext.close();
    }

    static class CustomSourceAnnotationConfig {

        @Autowired
        private QualifierDomain domain;

        @Inject
        private QualifierDomain domain2;

        @CustomSource
        private QualifierDomain myDomain;

        /**
         * 此处需要使用静态方法，保证方法 {@link #beanPostProcessor()}
         * 的加载要早于当前的配置 bean {@link CustomSourceAnnotationConfig}。
         * 同时该 bean 的名称不可以是 {@link AnnotationConfigUtils#AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME}
         * 否则默认的 {@link Autowired}、{@link Value} 以及 {@link Inject} 的解析将会失效，
         * 参见：{@link AnnotationConfigUtils#registerAnnotationConfigProcessors(BeanDefinitionRegistry, Object)}
         * @return
         * @see AutowiredAnnotationBeanPostProcessor#setAutowiredAnnotationType(Class)
         * @see AnnotationConfigUtils#AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME
         */
        @Bean
        public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
            AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
            // 此处会清空原有的配置（autowiredAnnotationTypes）信息，
            // 但为什么仍然有效的原因是：IoC容器中会同时存在默认的{@link AutowiredAnnotationBeanPostProcessor}
            // 以及此外的自定义实现。
            processor.setAutowiredAnnotationType(CustomSource.class);
            return processor;
        }

        @Bean
        public QualifierDomain domain() {
            return new QualifierDomain().setId(911).setName("自定义依赖注入注解");
        }

        @Bean
        @Primary
        public QualifierDomain domain2() {
            return new QualifierDomain().setId(911).setName("自定义依赖注入注解2");
        }

    }

    /**
     * TODO 完全通过自定义，需要补上！！
     */
    @Test
    public void dependencyInjectionWithCustomImplementation() {

    }

}