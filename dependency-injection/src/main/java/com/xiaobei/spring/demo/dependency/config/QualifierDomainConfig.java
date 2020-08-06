package com.xiaobei.spring.demo.dependency.config;

import com.xiaobei.spring.demo.dependency.annonation.QualifierDomainGroup;
import com.xiaobei.spring.demo.dependency.domain.QualifierDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/5 20:48
 */
public class QualifierDomainConfig {

    @Autowired
    private QualifierDomain myDomain;

    @Autowired
    @Qualifier("domain")
    private QualifierDomain myDomain2;

    @Autowired
    private Collection<QualifierDomain> domains1;

    @Autowired
    @Qualifier
    private List<QualifierDomain> domains2;

    @Autowired
    @QualifierDomainGroup
    private List<QualifierDomain> domains3;

    public List<QualifierDomain> getDomains3() {
        return domains3;
    }

    public Collection<QualifierDomain> getDomains1() {
        return domains1;
    }

    public List<QualifierDomain> getDomains2() {
        return domains2;
    }

    public QualifierDomain getMyDomain() {
        return myDomain;
    }

    public QualifierDomain getMyDomain2() {
        return myDomain2;
    }

    @Bean
    public QualifierDomain domain() {
        return new QualifierDomain().setId(63).setName("限定注入1");
    }

    @Bean
    @Primary
    public QualifierDomain domain2() {
        return new QualifierDomain().setId(63).setName("限定注入2");
    }

    @Bean
    @Qualifier
    public QualifierDomain domain3() {
        return new QualifierDomain().setId(63).setName("限定注入3");
    }

    @Bean
    @Qualifier
    public QualifierDomain domain4() {
        return new QualifierDomain().setId(63).setName("限定注入4");
    }

    @Bean
    @QualifierDomainGroup
    public QualifierDomain domain5() {
        return new QualifierDomain().setId(63).setName("限定注入5");
    }

    @Bean
    @QualifierDomainGroup
    public QualifierDomain domain6() {
        return new QualifierDomain().setId(63).setName("限定注入6");
    }
}
