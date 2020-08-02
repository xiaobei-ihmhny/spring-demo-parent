package com.xiaobei.spring.demo.dependency.lookup;

import com.xiaobei.spring.demo.dependency.domain.Lesson;
import com.xiaobei.spring.demo.dependency.domain.Lookup;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.ResolvableType;

/**
 * 单一类型依赖查找
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 15:29
 */
public class SingleTypeLookupDemo {

    /**
     * 根据Bean名称进行查找
     * 通过{@link BeanFactory#getBean(java.lang.String)} 进行依赖查找
     * TODO 通过{@link BeanFactory#getBean(java.lang.String, Object...)} 进行依赖查找
     */
    @Test
    public void lookupByName() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册LookUp类，以加载类中的相关Bean
        applicationContext.register(Lookup.class);
        applicationContext.refresh();
        // 根据名称依赖查找 getBean(java.lang.String)
        Object lookupByName = applicationContext.getBean("lesson");
        System.out.println(lookupByName);
        // TODO 根据名称依赖查找 getBean(java.lang.String, java.lang.Object...)，报错！！！
        // Object lookupByNameAndObject = applicationContext.getBean("lessonPrototype", 1, "修改后的值");
        // System.out.println(lookupByNameAndObject);
        applicationContext.close();
    }

    /**
     * 根据Bean的类型进行查找
     * 通过{@link BeanFactory#getBean(java.lang.Class)} 进行依赖查找
     * TODO 通过{@link BeanFactory#getBean(java.lang.Class, java.lang.Object...)} 进行依赖查找
     */
    @Test
    public void lookupByTypeInRealTime() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册LookUp类，以加载类中的相关Bean
        applicationContext.register(Lookup.class);
        applicationContext.refresh();
        // 根据名称依赖查找 getBean(Class<T> requiredType)
        Lesson lookupByType = applicationContext.getBean(Lesson.class);
        System.out.println(lookupByType);
        // TODO 根据名称依赖查找 getBean(java.lang.Class<T>, java.lang.Object...)，报错！！！
        // Object lookupByNameAndObject = applicationContext.getBean(LessonPrototype.class, 1, "修改后的值");
        // System.out.println(lookupByNameAndObject);
        applicationContext.close();
    }

    /**
     * spring 5.1 Bean延迟查找
     * 通过{@link BeanFactory#getBeanProvider(java.lang.Class)}进行延迟查找
     * TODO 通过{@link BeanFactory#getBeanProvider(ResolvableType)}进行延迟查找
     */
    @Test
    public void lookupByTypeInLazy() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册LookUp类，以加载类中的相关Bean
        applicationContext.register(Lookup.class);
        applicationContext.refresh();
        // 根据名称依赖查找 getBeanProvider(Class<T> requiredType)
        ObjectProvider<Lesson> lookupByTypeInLazy = applicationContext.getBeanProvider(Lesson.class);
        Lesson lesson = lookupByTypeInLazy.getObject();
        System.out.println(lesson);
        // TODO 根据名称依赖查找 {@link BeanFactory#getBeanProvider(ResolvableType)}具体写法
        applicationContext.close();
    }

    /**
     * 根据名称和类型进行bean的依赖查找
     * {@link BeanFactory#getBean(java.lang.String, java.lang.Class)}
     */
    @Test
    public void lookupByNameAndType() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册LookUp类，以加载类中的相关Bean
        applicationContext.register(Lookup.class);
        applicationContext.refresh();
        // 根据名称依赖查找 {@link BeanFactory#getBean(java.lang.String, java.lang.Class)}
        Lesson lookupByNameAndType = applicationContext.getBean("lesson", Lesson.class);
        System.out.println(lookupByNameAndType);
        applicationContext.close();
    }


}
