package com.xiaobei.spring.demo.dependency.lookup;

import com.xiaobei.spring.demo.dependency.domain.*;
import org.junit.Test;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 示例依赖查找中的常见异常 {@link org.springframework.beans.BeansException}
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/4 7:10
 */
public class ClassicExceptionInLookupDemo {

    /**
     * 示例{@link NoSuchBeanDefinitionException} 当查找Bean不存在于IoC容器时的异常
     * org.springframework.beans.factory.NoSuchBeanDefinitionException:
     * No qualifying bean of type 'java.lang.String' available
     */
    @Test
    public void NoSuchBeanDefinitionException() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ClassicExceptionInLookupDemo.class);
        applicationContext.refresh();
        // 获取一个不存在的bean
        String stringBean = applicationContext.getBean(String.class);
        System.out.println(stringBean);
        applicationContext.close();
    }

    /**
     * 示例{@link NoUniqueBeanDefinitionException} 类型依赖查找时，IoC容器存在多个Bean实例
     * org.springframework.beans.factory.NoUniqueBeanDefinitionException:
     * No qualifying bean of type 'com.xiaobei.spring.demo.dependency.domain.Lesson' available:
     * expected single matching bean but found 3: chinese,maths,english
     */
    @Test
    public void NoUniqueBeanDefinitionException() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ListableBeanConf.class);
        applicationContext.refresh();
        // 存在多个类型相同的bean时，以类型查找单一的bean
        Lesson lesson = applicationContext.getBean(Lesson.class);
        System.out.println(lesson);
        applicationContext.close();
    }

    /**
     * 示例{@link BeanInstantiationException} 当Bean所对应的类型为非具体类时
     * 比如：接口、抽象类、注解等
     *
     * 运行结果：
     * Caused by: org.springframework.beans.BeanInstantiationException:
     * Failed to instantiate [com.xiaobei.spring.demo.dependency.domain.MyLesson]: Specified class is an interface
     */
    @Test
    public void BeanInstantiationException() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AbstractQueuedSynchronizer.class);
        applicationContext.registerBeanDefinition("builder", builder.getBeanDefinition());
        applicationContext.refresh();
        applicationContext.close();
    }

    /**
     * 示例：{@link BeanCreationException}当Bean初始化过程中
     *
     * 运行结果：
     * org.springframework.beans.factory.BeanCreationException:
     * Error creating bean with name 'beanCreationExceptoinDemo': Invocation of init method failed;
     * nested exception is java.lang.RuntimeException: bean 初始化异常
     */
    @Test
    public void BeanCreationException() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanCreationExceptoinDemo.class);
        applicationContext.refresh();
        applicationContext.close();
    }

    /**
     * 示例：{@link BeanDefinitionStoreException} 当BeanDefinition配置元信息非法时
     * org.springframework.beans.factory.BeanDefinitionStoreException:
     * IOException parsing XML document from class path resource [META-INF/no-exists.xml]; nested exception is java.io.FileNotFoundException:
     * class path resource [META-INF/no-exists.xml] cannot be opened because it does not exist
     */
    @Test
    public void BeanDefinitionStoreException() {
        // 加载一个不存在的xml文件
        new ClassPathXmlApplicationContext("classpath:/META-INF/no-exists.xml");
    }
}
