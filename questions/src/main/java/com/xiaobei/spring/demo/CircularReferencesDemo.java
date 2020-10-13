package com.xiaobei.spring.demo;

import com.xiaobei.spring.demo.domain.ClassRoom;
import com.xiaobei.spring.demo.domain.Student;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/30 18:26
 */
public class CircularReferencesDemo {

    /**
     * TODO 具体流程再梳理
     *
     * <h2>运行结果：</h2>
     * Student{id=249, name='加餐4 BeanFactory如何处理循环依赖的？', classRoom.name=拿铁室}
     * ClassRoom{id=149, name='拿铁室', students=[Student{id=249, name='加餐4 BeanFactory如何处理循环依赖的？', classRoom.name=拿铁室}]}
     *
     * <h2>开启不允许循环依赖后的结果为：</h2>
     * Requested bean is currently in creation: Is there an unresolvable circular reference?
     * debug 条件："student".equals(beanName) || "classRoom".equals(beanName)
     *
     * {@link AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册当前类为 Configuration Class
        applicationContext.register(CircularReferencesDemo.class);

        // 设置不允许循环依赖（或循环查找），开启后会出现异常：Requested bean is currently in creation: Is there an unresolvable circular reference?
        // 默认情况下，该值为 {@code true}
//        applicationContext.setAllowCircularReferences(false);

        // 启动 Spring 应用上下文
        applicationContext.refresh();

//        Student student = applicationContext.getBean(Student.class);
//        ClassRoom classRoom = applicationContext.getBean(ClassRoom.class);
//        System.out.println(student);
//        System.out.println(classRoom);

        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    @Bean
    public static Student student() {
        Student student = new Student();
        student.setId(249);
        student.setName("加餐4 BeanFactory如何处理循环依赖的？");
        return student;
    }

    @Bean
    public static ClassRoom classRoom() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(149);
        classRoom.setName("拿铁室");
        return classRoom;
    }


}
