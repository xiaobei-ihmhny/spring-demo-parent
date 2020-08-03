package com.xiaobei.spring.demo.dependency.lookup;

import com.xiaobei.spring.demo.dependency.domain.AncestorsBeanConf;
import com.xiaobei.spring.demo.dependency.domain.Lesson;
import com.xiaobei.spring.demo.dependency.domain.LessonPrototype;
import com.xiaobei.spring.demo.dependency.domain.ListableBeanConf;
import org.junit.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 延迟依赖查找示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-03 17:24:24
 */
@SuppressWarnings("DuplicatedCode")
public class LazyDependencyLookupDemo {

    /**
     * 使用{@link ObjectProvider#getIfAvailable(java.util.function.Supplier)}和
     * {@link ObjectProvider#ifAvailable(java.util.function.Consumer)}
     * 演示spring 5对java 8的扩展
     */
    @Test
    public void getIfAvailableAndIfAvailable() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//        applicationContext.register(ListableBeanConf.class);
         applicationContext.register(AncestorsBeanConf.class);
        applicationContext.refresh();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        // 查询类型为LessonPrototype的bean
        ObjectProvider<LessonPrototype> lessonPrototypeObjectProvider
                = applicationContext.getBeanProvider(LessonPrototype.class);
        lessonPrototypeObjectProvider.ifAvailable(l -> System.out.println("1 当前已存在对应的bean"));
        LessonPrototype lessonPrototype = lessonPrototypeObjectProvider.getIfAvailable(() -> {
            LessonPrototype prototype = new LessonPrototype();
            prototype.setId(46);
            prototype.setName("46 延迟依赖查找：非延迟初始化Bean也能实现延迟查找？");
            // 创建好了之后注册为springBean
            beanFactory.registerSingleton("lessonPrototype", prototype);
            return prototype;
        });
        lessonPrototypeObjectProvider.ifAvailable(l -> System.out.println("2 当前已存在对应的bean"));
        // 原容器中存在指定类型bean时，直接使用；原容器不存在指定类型bean时，按给定逻辑创建
        LessonPrototype bean = applicationContext.getBean(LessonPrototype.class);
        System.out.println(lessonPrototype);
        System.out.println(bean);
        applicationContext.close();
    }

    /**
     * 使用{@link ObjectProvider#stream()}演示spring 5对java 8的扩展
     *
     * 执行结果：
     * Lesson{id=44, name='集合类型依赖查找——语言'}
     * Lesson{id=44, name='集合类型依赖查找——数学'}
     * Lesson{id=44, name='集合类型依赖查找——英语'}
     */
    @Test
    public void spring5Stream() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ListableBeanConf.class);
        applicationContext.refresh();
        // 查询类型为Lesson的bean
        ObjectProvider<Lesson> lessonObjectProvider
                = applicationContext.getBeanProvider(Lesson.class);
        // 循环输出查找到的Lesson类型的bean
        lessonObjectProvider.stream().forEach(System.out::println);
        applicationContext.close();
    }
}