package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.QualifierDomain;
import org.junit.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/5 21:47
 */
public class LazyAnnotationDependencyInjectionDemo {

    /**
     *
     * 使用{@link ObjectFactory} 和{@link ObjectProvider}进行延迟注入示例
     *
     * 执行结果如下：
     * QualifierDomain{id=63, name='限定注入1'}
     * QualifierDomain{id=63, name='限定注入1'}
     * [QualifierDomain{id=63, name='限定注入1'}, QualifierDomain{id=63, name='限定注入2'}, QualifierDomain{id=63, name='限定注入3'}, QualifierDomain{id=63, name='限定注入4'}]
     * QualifierDomain{id=63, name='限定注入1'}
     * =============================================
     * QualifierDomain{id=63, name='限定注入1'}
     * QualifierDomain{id=63, name='限定注入2'}
     * QualifierDomain{id=63, name='限定注入3'}
     * QualifierDomain{id=63, name='限定注入4'}
     */
    @Test
    public void lazyAnnotationDependencyInjection() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(LazyAnnotationConfig.class);
        applicationContext.refresh();
        LazyAnnotationConfig bean = applicationContext.getBean(LazyAnnotationConfig.class);
        System.out.println(bean.getDomain());
        System.out.println(bean.getObjectFactoryDomain().getObject());
        System.out.println(bean.getObjectFactoryCollection().getObject());
        System.out.println(bean.getObjectProviderDomain().getObject());
        System.out.println("=============================================");
        bean.getObjectProviderDomain().stream().forEach(System.out::println);
        applicationContext.close();
    }
}
