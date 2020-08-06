package com.xiaobei.spring.demo.dependency.config;

import com.xiaobei.spring.demo.dependency.annonation.Source;
import com.xiaobei.spring.demo.dependency.domain.QualifierDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-06 14:13:13
 */
public class SourceAnnotationConfig {

    @Autowired
    private QualifierDomain domain;

    @Source
    private QualifierDomain myDomain;

    public QualifierDomain getDomain() {
        return domain;
    }

    public QualifierDomain getMyDomain() {
        return myDomain;
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