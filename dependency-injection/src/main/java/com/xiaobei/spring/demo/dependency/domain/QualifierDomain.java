package com.xiaobei.spring.demo.dependency.domain;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/5 20:47
 */
public class QualifierDomain {

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public QualifierDomain setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public QualifierDomain setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "QualifierDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
