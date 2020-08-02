package com.xiaobei.spring.demo.dependency.domain;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 15:36
 */
public class Lookup {

    @Bean
    public Lesson lesson() {
        Lesson lesson = new Lesson();
        lesson.setId(43);
        lesson.setName("43 单一类型依赖查找：如何查找已知名称或类型的Bean对象？");
        return lesson;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public LessonPrototype lessonPrototype() {
        LessonPrototype lesson = new LessonPrototype();
        lesson.setId(43);
        lesson.setName("43 单一类型依赖查找：如何查找已知名称或类型的Bean对象？");
        return lesson;
    }
}
