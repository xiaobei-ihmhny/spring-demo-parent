package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.QualifierDomain;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.inject.Inject;
import java.util.Collection;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/5 21:48
 */
public class InjectAnnotationConfig {

    @Autowired
    private QualifierDomain domain;

    @Inject
    private QualifierDomain injectDomain;

    public QualifierDomain getInjectDomain() {
        return injectDomain;
    }

    public QualifierDomain getDomain() {
        return domain;
    }

    @Bean
    @Primary
    public QualifierDomain domain() {
        return new QualifierDomain().setId(63).setName("限定注入1");
    }

    @Bean
    public QualifierDomain domain2() {
        return new QualifierDomain().setId(63).setName("限定注入2");
    }

    @Bean
    public QualifierDomain domain3() {
        return new QualifierDomain().setId(63).setName("限定注入3");
    }

    @Bean
    public QualifierDomain domain4() {
        return new QualifierDomain().setId(63).setName("限定注入4");
    }
}
