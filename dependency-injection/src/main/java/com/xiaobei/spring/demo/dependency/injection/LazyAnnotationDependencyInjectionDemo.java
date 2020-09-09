package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.QualifierDomain;
import org.junit.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Collection;


/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/5 21:47
 */
public class LazyAnnotationDependencyInjectionDemo {

    /**
     * 使用{@link ObjectFactory} 和{@link ObjectProvider}进行延迟注入示例
     * <p>
     * 执行结果如下：
     * bean.domain：QualifierDomain{id=1, name='限定注入1'}
     * bean.objectProviderDomain：QualifierDomain{id=1, name='限定注入1'}
     * bean.objectFactoryDomain：QualifierDomain{id=1, name='限定注入1'}
     * bean.objectFactoryCollection：[QualifierDomain{id=1, name='限定注入1'}, QualifierDomain{id=2, name='限定注入2'}, QualifierDomain{id=3, name='限定注入3'}, QualifierDomain{id=4, name='限定注入4'}]
     * bean.objectProviderCollection：[QualifierDomain{id=1, name='限定注入1'}, QualifierDomain{id=2, name='限定注入2'}, QualifierDomain{id=3, name='限定注入3'}, QualifierDomain{id=4, name='限定注入4'}]
     * =============================================
     * bean.objectProviderDomain$：QualifierDomain{id=1, name='限定注入1'}
     * bean.objectProviderDomain$：QualifierDomain{id=2, name='限定注入2'}
     * bean.objectProviderDomain$：QualifierDomain{id=3, name='限定注入3'}
     * bean.objectProviderDomain$：QualifierDomain{id=4, name='限定注入4'}
     */
    @Test
    public void lazyAnnotationDependencyInjection() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(LazyAnnotationConfig.class);
        applicationContext.refresh();
        LazyAnnotationConfig bean = applicationContext.getBean(LazyAnnotationConfig.class);
        System.out.println("bean.domain：" + bean.domain);
        System.out.println("bean.objectProviderDomain：" + bean.objectProviderDomain.getObject());
        System.out.println("bean.objectFactoryDomain：" + bean.objectFactoryDomain.getObject());
        System.out.println("bean.objectFactoryCollection：" + bean.objectFactoryCollection.getObject());
        System.out.println("bean.objectProviderCollection：" + bean.objectProviderCollection.getObject());
        System.out.println("=============================================");
        bean.objectProviderDomain.stream().forEach(domain ->
                System.out.println("bean.objectProviderDomain$：" + domain)
        );
        applicationContext.close();
    }

    static class LazyAnnotationConfig {

        @Autowired
        private QualifierDomain domain;

        @Autowired
        private ObjectProvider<QualifierDomain> objectProviderDomain;

        @Autowired
        private ObjectFactory<QualifierDomain> objectFactoryDomain;

        @Autowired
        private ObjectFactory<Collection<QualifierDomain>> objectFactoryCollection;

        @Autowired
        private ObjectProvider<Collection<QualifierDomain>> objectProviderCollection;

        @Bean
        @Primary
        public QualifierDomain domain() {
            return new QualifierDomain().setId(1).setName("限定注入1");
        }

        @Bean
        public QualifierDomain domain2() {
            return new QualifierDomain().setId(2).setName("限定注入2");
        }

        @Bean
        public QualifierDomain domain3() {
            return new QualifierDomain().setId(3).setName("限定注入3");
        }

        @Bean
        public QualifierDomain domain4() {
            return new QualifierDomain().setId(4).setName("限定注入4");
        }
    }
}
