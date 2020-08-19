package com.xiaobei.spring.demo.data.binding.domain;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/19 22:37
 */
public class DataBindingDomain {

    private Long id;

    private String name;

    private City city;

    public Long getId() {
        return id;
    }

    public DataBindingDomain setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DataBindingDomain setName(String name) {
        this.name = name;
        return this;
    }

    public City getCity() {
        return city;
    }

    public DataBindingDomain setCity(City city) {
        this.city = city;
        return this;
    }

    @Override
    public String toString() {
        return "DataBindingDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                '}';
    }
}
