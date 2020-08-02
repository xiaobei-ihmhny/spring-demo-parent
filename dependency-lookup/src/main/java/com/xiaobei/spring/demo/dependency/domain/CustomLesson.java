package com.xiaobei.spring.demo.dependency.domain;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 15:37
 */
public class CustomLesson {

    private Integer id;

    private String name;

    public CustomLesson() {
    }

    public CustomLesson(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public CustomLesson setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CustomLesson setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "CustomLesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
