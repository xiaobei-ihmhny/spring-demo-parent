package com.xiaobei.spring.demo.domain;

import javax.annotation.PostConstruct;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/30 10:04
 */
public class ObjectFactoryDomain {

    private Integer id;

    private String name;

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

    @PostConstruct
    public void init() {
        System.out.println("正在初始化 ObjectFactoryDomain...");
    }

    @Override
    public String toString() {
        return "ObjectFactoryDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
