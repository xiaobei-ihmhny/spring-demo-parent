package com.xiaobei.spring.demo.dependency.config;

import com.xiaobei.spring.demo.dependency.annonation.CustomSource;
import com.xiaobei.spring.demo.dependency.annonation.Source;
import com.xiaobei.spring.demo.dependency.domain.QualifierDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.inject.Inject;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-06 14:13:13
 */
public class CustomSourceAnnotationConfig {

    @Autowired
    private QualifierDomain domain;

    @Inject
    private QualifierDomain domain2;

    @CustomSource
    private QualifierDomain myDomain;

    public QualifierDomain getDomain() {
        return domain;
    }

    public QualifierDomain getMyDomain() {
        return myDomain;
    }

    public QualifierDomain getDomain2() {
        return domain2;
    }

    /**
     * TODO 此处需要使用静态方法，保证优先加载？？
     * @return
     */
    @Bean
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
        // TODO 此处会清空原有的配置（autowiredAnnotationTypes）信息，但为什么仍然有效？？
        processor.setAutowiredAnnotationType(CustomSource.class);
        return processor;
    }

    @Bean
    public QualifierDomain domain() {
        return new QualifierDomain().setId(69).setName("自定义依赖注入注解");
    }

    @Bean
    @Primary
    public QualifierDomain domain2() {
        return new QualifierDomain().setId(69).setName("自定义依赖注入注解2");
    }

}