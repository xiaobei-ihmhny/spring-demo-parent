package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.annonation.QualifierDomainGroup;
import com.xiaobei.spring.demo.dependency.config.QualifierDomainConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/5 20:46
 */
public class QualifierAnnotationDependencyInjectionDemo {

    /**
     * 示例{@link Qualifier}进行名称限定
     * QualifierDomain{id=63, name='限定注入2'}
     * QualifierDomain{id=63, name='限定注入1'}
     */
    @Test
    public void qualifierAnnotationLimitedByName() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(QualifierDomainConfig.class);
        applicationContext.refresh();
        QualifierDomainConfig qualifierDomainConfig = applicationContext.getBean(QualifierDomainConfig.class);
        System.out.println(qualifierDomainConfig.getMyDomain());
        System.out.println(qualifierDomainConfig.getMyDomain2());
        applicationContext.close();
    }


    /**
     * 示例{@link Qualifier}对Bean进行分组
     * [QualifierDomain{id=63, name='限定注入1'}, QualifierDomain{id=63, name='限定注入2'}, QualifierDomain{id=63, name='限定注入3'}, QualifierDomain{id=63, name='限定注入4'}]
     * [QualifierDomain{id=63, name='限定注入3'}, QualifierDomain{id=63, name='限定注入4'}]
     */
    @Test
    public void qualifierAnnotationLimitedByGroup() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(QualifierDomainConfig.class);
        applicationContext.refresh();
        QualifierDomainConfig qualifierDomainConfig = applicationContext.getBean(QualifierDomainConfig.class);
        System.out.println(qualifierDomainConfig.getDomains1());
        System.out.println(qualifierDomainConfig.getDomains2());
        applicationContext.close();
    }

    /**
     * 示例{@link Qualifier}的扩展注解{@link QualifierDomainGroup}对Bean进行分组
     * 例如 spring-cloud中的 {@link LoadBalanced} 就是对{@link Qualifier}扩展
     * [QualifierDomain{id=63, name='限定注入3'}, QualifierDomain{id=63, name='限定注入4'}, QualifierDomain{id=63, name='限定注入5'}, QualifierDomain{id=63, name='限定注入6'}]
     * [QualifierDomain{id=63, name='限定注入5'}, QualifierDomain{id=63, name='限定注入6'}]
     */
    @Test
    public void qualifierAnnotationLimitedByExpand() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(QualifierDomainConfig.class);
        applicationContext.refresh();
        QualifierDomainConfig qualifierDomainConfig = applicationContext.getBean(QualifierDomainConfig.class);
        System.out.println(qualifierDomainConfig.getDomains2());
        System.out.println(qualifierDomainConfig.getDomains3());
        applicationContext.close();
    }

}
