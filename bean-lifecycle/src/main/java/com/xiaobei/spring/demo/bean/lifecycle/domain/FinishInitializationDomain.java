package com.xiaobei.spring.demo.bean.lifecycle.domain;

import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-12 13:37:37
 */
public class FinishInitializationDomain implements SmartInitializingSingleton {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public FinishInitializationDomain setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public FinishInitializationDomain setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.id = 101L;
        System.out.println("SmartInitializingSingleton#afterSingletonsInstantiated方法执行...");
    }

    @Override
    public String toString() {
        return "FinishInitializationDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}