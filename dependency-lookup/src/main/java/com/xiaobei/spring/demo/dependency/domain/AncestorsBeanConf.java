package com.xiaobei.spring.demo.dependency.domain;

import org.springframework.context.annotation.Bean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-03 13:22:22
 */
public class AncestorsBeanConf {

    @Bean
    @MyLesson
    public Lesson chinese() {
        return new Lesson().setId(44).setName("ancestors依赖查找——语言");
    }

    @Bean
    public Lesson maths() {
        return new Lesson().setId(44).setName("ancestors依赖查找——数学");
    }

    @Bean
    @MyLesson
    public Lesson english() {
        return new Lesson().setId(44).setName("ancestors依赖查找——英语");
    }

    @Bean
    public LessonPrototype lessonPrototype() {
        LessonPrototype lessonPrototype = new LessonPrototype();
        lessonPrototype.setId(45);
        lessonPrototype.setName("ancestors依赖查找——自定义课程");
        return lessonPrototype;
    }
}