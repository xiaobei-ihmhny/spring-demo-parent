package com.xiaobei.spring.demo.bean.definition.instantiation;

import com.xiaobei.spring.demo.bean.definition.domain.Person;
import com.xiaobei.spring.demo.bean.definition.factory.DefaultPersonFactory;
import com.xiaobei.spring.demo.bean.definition.factory.PersonFactory;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.serviceloader.ServiceLoaderFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/1 21:33
 */
public class BeanInstantiationDemo {

    /**
     * 使用构造器通过XML方式实例化Bean
     * Person{age=1, name='xiaobei'}
     */
    @Test
    public void instanceBeanByConstructorWithXml() {
        BeanFactory beanFactory =
                new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");
        Person person = beanFactory.getBean("person-by-constructor-with-xml", Person.class);
        System.out.println(person);
    }

    /**
     * 使用静态工厂方法通过xml方式实例化Bean
     * Person{age=36, name='36 实例化Spring Bean：Bean实例化的姿势有多少种？'}
     */
    @Test
    public void instanceBeanByStaticFactoryMethodWithXml() {
        BeanFactory beanFactory =
                new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");
        Person person = beanFactory.getBean("person-by-factory-method-with-xml", Person.class);
        System.out.println(person);
    }


    /**
     * 通过实例工厂创建spring bean
     *
     * Person{age=36, name='36 实例化Spring Bean：通过Bean工厂(实例工厂)方法，XML文件'}
     */
    @Test
    public void instanceBeanByInstanceFactoryWithXml() {
        BeanFactory beanFactory =
                new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");
        Person person = beanFactory.getBean("person-by-instance-factory-with-xml", Person.class);
        System.out.println(person);
    }

    /**
     * 通过FactoryBean方法实例化bean
     *
     * Person{age=36, name='36 实例化Spring Bean：通过FactoryBean，XML文件'}
     */
    @Test
    public void instanceBeanByFactoryBeanWithXml() {
        BeanFactory beanFactory =
                new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");
        Person person = beanFactory.getBean("person-by-factory-bean-with-xml", Person.class);
        System.out.println(person);
    }

    /**
     * java SPI 示例
     *
     * com.xiaobei.spring.demo.bean.definition.factory.DefaultPersonFactory@73c6c3b2
     */
    @Test
    public void javaServiceLoaderDemo() {
        ServiceLoader<PersonFactory> serviceLoader =
                ServiceLoader.load(PersonFactory.class, Thread.currentThread().getContextClassLoader());
        for (PersonFactory personFactory : serviceLoader) {
            System.out.println(personFactory);
            System.out.println(personFactory.getInstance());
        }
    }

    /**
     * 通过ServiceLoaderFactoryBean的方式实例化bean
     *
     * TODO 但此种方式注册的bean是一个ServiceLoader类型，并不是真实需要的bean？？？
     */
    @Test
    @SuppressWarnings("unchecked")
    public void instanceBeanByServiceLoaderFactoryBeanWithXml() {
        BeanFactory beanFactory =
                new ClassPathXmlApplicationContext("classpath:/META-INF/special-bean-instantiation-context.xml");
        ServiceLoader<PersonFactory> personFactoryServiceLoader = beanFactory.getBean("person-by-service-loader-factory-bean-with-xml", ServiceLoader.class);
        for (PersonFactory personFactory : personFactoryServiceLoader) {
            System.out.println(personFactory);
            System.out.println(personFactory.getInstance());
        }
    }

    /**
     * 通过AutowireCapableBeanFactory#createBean(java.lang.Class, int ,boolean)
     * TODO 这样操作的话，new ClassPathXmlApplicationContext()... 就没有用了？？？？
     */
    @Test
    public void instanceBeanByAutowireCapableBeanFactory() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/special-bean-instantiation-context.xml");
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        PersonFactory personFactory = beanFactory.createBean(DefaultPersonFactory.class);
        System.out.println(personFactory.getInstance());

    }
}
