package com.xiaobei.spring.demo.dependency.source.domain;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-07 11:06:06
 */
public class Domain {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public Domain setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Domain setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}