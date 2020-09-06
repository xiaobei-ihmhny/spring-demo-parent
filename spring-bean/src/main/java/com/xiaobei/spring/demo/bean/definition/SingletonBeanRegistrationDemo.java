package com.xiaobei.spring.demo.bean.definition;

import com.xiaobei.spring.demo.bean.definition.domain.Person;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 10:59
 */
public class SingletonBeanRegistrationDemo {

    /**
     * 通过外部单体对象来注册spring bean
     * {@link SingletonBeanRegistry#registerSingleton(java.lang.String, java.lang.Object)}
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        // 创建一个外部单体对象
        Person person = new Person();
        person.setAge(41);
        person.setName("xiaobei");
        // 注册外部单体对象
        beanFactory.registerSingleton("person", person);
        // 启动spring应用上下文
        applicationContext.refresh();
        // 通过依赖查找的方式获取 Person 对象
        Person personByLookup = beanFactory.getBean("person", Person.class);
        System.out.println("person == personByLookup："+(person == personByLookup));
        // 关闭spring应用上下文
        applicationContext.close();
    }
}
