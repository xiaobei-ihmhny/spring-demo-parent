package com.xiaobei.spring.demo.dependency.domain;

import org.springframework.context.annotation.Bean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 17:26
 */
public class ListableBeanConf {

    @Bean
    @MyLesson
    public Lesson chinese() {
        return new Lesson().setId(44).setName("集合类型依赖查找——语言");
    }

    @Bean
    @MyLesson
    public Lesson maths() {
        return new Lesson().setId(44).setName("集合类型依赖查找——数学");
    }

    @Bean
    public Lesson english() {
        return new Lesson().setId(44).setName("集合类型依赖查找——英语");
    }

    @Bean
    public CustomLesson customLesson() {
        return new CustomLesson().setId(44).setName("集合类型依赖查找——自定义课程");
    }


}
