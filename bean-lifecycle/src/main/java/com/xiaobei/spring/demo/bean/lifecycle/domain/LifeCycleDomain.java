package com.xiaobei.spring.demo.bean.lifecycle.domain;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/9 15:17
 */
public class LifeCycleDomain {

    private Long id;

    private String name;

    private City city;

    public Long getId() {
        return id;
    }

    public LifeCycleDomain setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LifeCycleDomain setName(String name) {
        this.name = name;
        return this;
    }

    public City getCity() {
        return city;
    }

    public LifeCycleDomain setCity(City city) {
        this.city = city;
        return this;
    }

    @Override
    public String toString() {
        return "LifeCycleDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                '}';
    }
}
