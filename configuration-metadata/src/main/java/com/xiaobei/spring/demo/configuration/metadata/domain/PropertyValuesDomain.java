package com.xiaobei.spring.demo.configuration.metadata.domain;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/13 20:41
 */
public class PropertyValuesDomain {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public PropertyValuesDomain setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PropertyValuesDomain setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "PropertyValuesDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
