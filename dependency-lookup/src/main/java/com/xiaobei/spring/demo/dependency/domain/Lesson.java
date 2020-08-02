package com.xiaobei.spring.demo.dependency.domain;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 15:37
 */
public class Lesson {

    private Integer id;

    private String name;

    public Lesson() {
    }

    public Lesson(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public Lesson setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Lesson setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
