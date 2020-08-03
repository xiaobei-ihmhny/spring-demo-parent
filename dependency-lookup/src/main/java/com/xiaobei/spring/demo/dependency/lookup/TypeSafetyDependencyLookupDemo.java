package com.xiaobei.spring.demo.dependency.lookup;

import com.xiaobei.spring.demo.dependency.domain.Lesson;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/3 20:28
 */
@SuppressWarnings("DuplicatedCode")
public class TypeSafetyDependencyLookupDemo {

    /**
     *
     * 示例 {@link BeanFactory#getBean(java.lang.Class)}——单一类型非安全查找
     * 运行直接报错：
     * org.springframework.beans.factory.NoSuchBeanDefinitionException:
     * No qualifying bean of type 'com.xiaobei.spring.demo.dependency.domain.Lesson' available
     */
    @Test
    public void unsafe_Lookup_By_BeanFactory_getBean() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);
        applicationContext.refresh();
        // 依赖查找不存在的bean
        Lesson lesson = applicationContext.getBean(Lesson.class);
        System.out.println(lesson);
        applicationContext.close();
    }

    /**
     * 示例：{@link ObjectFactory#getObject()}——单一类型非安全查找
     *
     * 运行结果：
     * org.springframework.beans.factory.NoSuchBeanDefinitionException:
     * No qualifying bean of type 'com.xiaobei.spring.demo.dependency.domain.Lesson' available
     */
    @Test
    public void unsafe_Lookup_By_ObjectFactory_getObject() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);
        applicationContext.refresh();
        // 依赖查找不存在的bean
        ObjectFactory<Lesson> objectFactory = applicationContext.getBeanProvider(Lesson.class);
        Lesson lesson = objectFactory.getObject();
        System.out.println(lesson);
        applicationContext.close();
    }

    /**
     * 示例：{@link ObjectProvider#getIfAvailable()}——单一类型安全查找
     */
    @Test
    public void safe_Lookup_by_ObjectProvider_getIfAvailable() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);
        applicationContext.refresh();
        // 依赖查找不存在的bean
        ObjectProvider<Lesson> objectProvider = applicationContext.getBeanProvider(Lesson.class);
        Lesson lesson = objectProvider.getIfAvailable();
        System.out.println(lesson);
        applicationContext.close();
    }

    /**
     * 示例：{@link ListableBeanFactory#getBeansOfType(java.lang.Class)}——集合类型安全查找
     */
    @Test
    public void safe_Lookup_by_ListableBeanFactory_getBeansOfType() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);
        applicationContext.refresh();
        // 依赖查找不存在的bean
        ListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        Map<String, Lesson> maps = beanFactory.getBeansOfType(Lesson.class);
        System.out.println(maps);
        applicationContext.close();
    }

    /**
     * 示例：{@link ObjectProvider#stream()}——集合类型安全查找
     */
    @Test
    public void safe_Lookup_by_ObjectProvider_stream() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);
        applicationContext.refresh();
        // 依赖查找不存在的bean
        ObjectProvider<Lesson> beanProvider = applicationContext.getBeanProvider(Lesson.class);
        beanProvider.stream().forEach(System.out::println);
        applicationContext.close();
    }
}
