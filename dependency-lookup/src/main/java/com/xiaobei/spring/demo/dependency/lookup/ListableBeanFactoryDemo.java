package com.xiaobei.spring.demo.dependency.lookup;

import com.xiaobei.spring.demo.dependency.domain.Lesson;
import com.xiaobei.spring.demo.dependency.domain.ListableBeanConf;
import com.xiaobei.spring.demo.dependency.domain.MyLesson;
import org.junit.Test;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Map;

/**
 * 集合类型的依赖查找 {@link org.springframework.beans.factory.ListableBeanFactory} 示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 17:24
 */
@SuppressWarnings("DuplicatedCode")
public class ListableBeanFactoryDemo {

    /**
     * 通过{@link ListableBeanFactory#getBeanNamesForType(java.lang.Class)}获取同类型Bean名称列表
     *
     * 执行结果如下：
     * 当前依赖查找找到的Lesson类型的bean的名称列表为：[chinese, maths, english]
     */
    @Test
    public void getBeanNameListByType() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 ListableBeanConf 类，以便其内部的相关注解被spring感知
        applicationContext.register(ListableBeanConf.class);
        applicationContext.refresh();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        String[] lessonList = beanFactory.getBeanNamesForType(Lesson.class);
        System.out.println("当前依赖查找找到的Lesson类型的bean的名称列表为：" + Arrays.toString(lessonList));
        applicationContext.close();
    }

    /**
     * TODO 待完善 2020年8月2日17:36:14
     * 通过{@link ListableBeanFactory#getBeanNamesForType(org.springframework.core.ResolvableType)}获取同类型Bean名称列表
     */
    @Test
    public void getBeanNameListByResolvableType() {

    }

    /**
     *
     * 通过{@link ListableBeanFactory#getBeansOfType(java.lang.Class)}获取同类型Bean实例列表
     * notes: 该方法会处罚Bean的初始化，可能导致初始化不完全的问题
     *
     * 运行结果如下：
     * 当前依赖查找找到的Lesson类型的bean的列表为：{chinese=Lesson{id=44, name='集合类型依赖查找——语言'},
     * maths=Lesson{id=44, name='集合类型依赖查找——数学'}, english=Lesson{id=44, name='集合类型依赖查找——英语'}}
     */
    @Test
    public void getBeanListByType() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 ListableBeanConf 类，以便其内部的相关注解被spring感知
        applicationContext.register(ListableBeanConf.class);
        applicationContext.refresh();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        Map<String, Lesson> lessonMap = beanFactory.getBeansOfType(Lesson.class);
        System.out.println("当前依赖查找找到的Lesson类型的bean的列表为：" + lessonMap);
        applicationContext.close();
    }

    /**
     * Spring 4.0获取标注类型Bean名称列表
     * 通过{@link ListableBeanFactory#getBeanNamesForAnnotation(java.lang.Class)}方法获取Bean名称列表
     *
     * 运行结果：
     * 当前依赖查找找到的Lesson类型并包含注解@MyLesson的bean的列表为：[chinese, maths]
     */
    @Test
    public void getBeanNameListByAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 ListableBeanConf 类，以便其内部的相关注解被spring感知
        applicationContext.register(ListableBeanConf.class);
        applicationContext.refresh();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        String[] lessonsWithAnnotation = beanFactory.getBeanNamesForAnnotation(MyLesson.class);
        System.out.println("当前依赖查找找到的Lesson类型并包含注解@MyLesson的bean的名称列表为：" + Arrays.toString(lessonsWithAnnotation));
        applicationContext.close();
    }

    /**
     * Spring 3.0获取标注类型Bean实例列表
     * {@link ListableBeanFactory#getBeansWithAnnotation(java.lang.Class)}
     */
    @Test
    public void getBeanListByAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 ListableBeanConf 类，以便其内部的相关注解被spring感知
        applicationContext.register(ListableBeanConf.class);
        applicationContext.refresh();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        Map<String, Object> lessonMap = beanFactory.getBeansWithAnnotation(MyLesson.class);
        System.out.println("当前依赖查找找到的Lesson类型并包含注解@MyLesson的bean的列表为：" + lessonMap);
        applicationContext.close();
    }

    /**
     * TODO Spring 3.0获取指定名称+标注类型的Bean实例
     * {@link ListableBeanFactory#findAnnotationOnBean(java.lang.String, java.lang.Class)}
     */
    @Test
    public void getBeanByNameAndAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 ListableBeanConf 类，以便其内部的相关注解被spring感知
        applicationContext.register(ListableBeanConf.class);
        applicationContext.refresh();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        // TODO 只获取了对应的注解，如何获取对应的Bean实例呢？
        MyLesson chinese = beanFactory.findAnnotationOnBean("chinese", MyLesson.class);
        System.out.println("当前依赖查找找到的名称为'chinese'并包含注解@MyLesson的bean的注解为：" + chinese);
        applicationContext.close();
    }

}
