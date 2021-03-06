package com.xiaobei.spring.demo.dependency.domain;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 15:37
 */
public class LessonPrototype {

    private Integer id;

    private String name;

    public LessonPrototype() {
    }

    public LessonPrototype(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LessonPrototype{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
