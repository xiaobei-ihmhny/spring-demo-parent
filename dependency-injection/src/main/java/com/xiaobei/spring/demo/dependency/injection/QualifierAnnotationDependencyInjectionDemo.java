package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.annonation.QualifierDomainGroup;
import com.xiaobei.spring.demo.dependency.config.QualifierDomainConfig;
import com.xiaobei.spring.demo.dependency.domain.QualifierDomain;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/5 20:46
 */
public class QualifierAnnotationDependencyInjectionDemo {

    /**
     * 示例 {@link Qualifier} 进行名称限定
     * QualifierDomain{id=2, name='添加 @Primary 的 Bean'}
     * QualifierDomain{id=1, name='普通 bean'}
     */
    @Test
    public void qualifierAnnotationLimitedByName() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(QualifierByNameConf.class);
        applicationContext.refresh();
        QualifierByNameConf qualifierDomainConfig = applicationContext.getBean(QualifierByNameConf.class);
        System.out.println(qualifierDomainConfig.domain);
        System.out.println(qualifierDomainConfig.namedDomain);
        applicationContext.close();
    }

    static class QualifierByNameConf {

        @Autowired
        private QualifierDomain domain;

        @Autowired
        @Qualifier("domain")
        private QualifierDomain namedDomain;

        @Bean
        public QualifierDomain domain() {
            return new QualifierDomain().setId(1).setName("普通 bean");
        }

        @Bean
        @Primary
        public QualifierDomain domain2() {
            return new QualifierDomain().setId(2).setName("添加 @Primary 的 Bean");
        }

    }


    /**
     * 示例{@link Qualifier}对Bean进行分组，
     * <p>1. 有 {@link Qualifier @Qualifier} 和 {@link Autowired @Autowired} 标注的，
     * 只会注入标记有 {@link Qualifier @Qualifier} 指定类型的 bean
     * <p>2. 只有 {@link Autowired @Autowired} 标注的，
     * 会注入标所有指定类型的 bean
     * [QualifierDomain{id=1, name='普通bean 无 @Primary 无 @Qualifier'}, QualifierDomain{id=2, name='@Primary bean 无 @Qualifier'}, QualifierDomain{id=3, name='普通 bean 有 @Qualifier'}, QualifierDomain{id=4, name='普通 bean 有 @Qualifier'}]
     * [QualifierDomain{id=3, name='普通 bean 有 @Qualifier'}, QualifierDomain{id=4, name='普通 bean 有 @Qualifier'}]
     */
    @Test
    public void qualifierAnnotationLimitedByGroup() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(QualifierByGroupConf.class);
        applicationContext.refresh();
        QualifierByGroupConf qualifierDomainConfig = applicationContext.getBean(QualifierByGroupConf.class);
        System.out.println(qualifierDomainConfig.domains1);
        System.out.println(qualifierDomainConfig.domains2);
        applicationContext.close();
    }

    static class QualifierByGroupConf {

        @Autowired
        private Collection<QualifierDomain> domains1;

        @Autowired
        @Qualifier
        private List<QualifierDomain> domains2;

        @Bean
        public QualifierDomain domain() {
            return new QualifierDomain().setId(1).setName("普通bean 无 @Primary 无 @Qualifier");
        }

        @Bean
        @Primary
        public QualifierDomain domain2() {
            return new QualifierDomain().setId(2).setName("@Primary bean 无 @Qualifier");
        }

        @Bean
        @Qualifier
        public QualifierDomain domain3() {
            return new QualifierDomain().setId(3).setName("普通 bean 有 @Qualifier");
        }

        @Bean
        @Qualifier
        public QualifierDomain domain4() {
            return new QualifierDomain().setId(4).setName("普通 bean 有 @Qualifier");
        }

    }

    /**
     * 示例{@link Qualifier}的扩展注解{@link QualifierDomainGroup}对Bean进行分组
     * 例如 spring-cloud中的 {@link org.springframework.cloud.client.loadbalancer.LoadBalanced} 就是对{@link Qualifier}扩展
     * [QualifierDomain{id=2, name='限定注入2'}, QualifierDomain{id=3, name='限定注入3'}, QualifierDomain{id=4, name='限定注入4'}]
     * [QualifierDomain{id=3, name='限定注入3'}, QualifierDomain{id=4, name='限定注入4'}]
     */
    @Test
    public void qualifierAnnotationLimitedByExpand() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(QualifierByExpand.class);
        applicationContext.refresh();
        QualifierByExpand qualifierDomainConfig = applicationContext.getBean(QualifierByExpand.class);
        System.out.println(qualifierDomainConfig.domains1);
        System.out.println(qualifierDomainConfig.domains2);
        applicationContext.close();
    }

    static class QualifierByExpand {

        @Autowired
        @Qualifier
        private List<QualifierDomain> domains1;

        @Autowired
        @QualifierDomainGroup
        private List<QualifierDomain> domains2;

        @Bean
        public QualifierDomain domain1() {
            return new QualifierDomain().setId(1).setName("限定注入1");
        }

        @Bean
        @Qualifier
        public QualifierDomain domain2() {
            return new QualifierDomain().setId(2).setName("限定注入2");
        }

        @Bean
        @QualifierDomainGroup
        public QualifierDomain domain3() {
            return new QualifierDomain().setId(3).setName("限定注入3");
        }

        @Bean
        @QualifierDomainGroup
        public QualifierDomain domain4() {
            return new QualifierDomain().setId(4).setName("限定注入4");
        }

    }

}
