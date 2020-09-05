package com.xiaobei.spring.demo.ioc.overview.repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/9/5 19:22
 */
public class LazyDomain {

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

    @Override
    public String toString() {
        return "LazyDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @PostConstruct
    public void init() {
        System.out.println("初始化 LazyDomain 对象...");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("销毁 LazyDomain 对象...");
    }
}
