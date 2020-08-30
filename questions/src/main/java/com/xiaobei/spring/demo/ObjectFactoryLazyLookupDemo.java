package com.xiaobei.spring.demo;

import com.xiaobei.spring.demo.domain.QuestionsDomain;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/30 8:08
 */
public class ObjectFactoryLazyLookupDemo {

    /**
     * <h2>运行结果：</h2>
     * org.springframework.beans.factory.support.DefaultListableBeanFactory$DependencyObjectProvider@7a9273a8
     * org.springframework.beans.factory.support.DefaultListableBeanFactory$DependencyObjectProvider@26a7b76d
     * 正在初始化 ObjectFactoryDomain...
     * ObjectFactoryDomain{id=246, name='加餐1：为什么说ObjectFactory提供的是延迟依赖查找?'}
     * ObjectFactoryDomain{id=246, name='加餐1：为什么说ObjectFactory提供的是延迟依赖查找?'}
     *
     * 说明 {@link ObjectFactory} 和 {@link ObjectProvider} 提供的都是延迟加载的策略
     * @param args
     * @see ObjectFactory
     * @see ObjectProvider
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ObjectFactoryLazyLookupDemo.class);
        applicationContext.refresh();

        ObjectFactoryLazyLookupDemo lookupDemo =
                applicationContext.getBean(ObjectFactoryLazyLookupDemo.class);
        ObjectFactory<QuestionsDomain> domain1 = lookupDemo.domain1;
        ObjectProvider<QuestionsDomain> domain2 = lookupDemo.domain2;
        System.out.println(domain1);
        System.out.println(domain2);

        System.out.println(domain1.getObject());
        System.out.println(domain2.getObject());
        applicationContext.close();
    }

    @Autowired
    private ObjectFactory<QuestionsDomain> domain1;

    @Autowired
    private ObjectProvider<QuestionsDomain> domain2;

    @Bean
    @Lazy
    public static QuestionsDomain domain() {
        QuestionsDomain domain = new QuestionsDomain();
        domain.setId(246);
        domain.setName("加餐1：为什么说ObjectFactory提供的是延迟依赖查找?");
        return domain;
    }

}
