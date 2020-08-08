package com.xiaobei.spring.demo.ioc.bean.scope.domain;

import org.springframework.beans.factory.BeanNameAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/8 18:46
 */
public class ScopeDomain implements BeanNameAware {

    private Long id;

    private String name;

    private String beanName;

    public Long getId() {
        return id;
    }

    public ScopeDomain setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ScopeDomain setName(String name) {
        this.name = name;
        return this;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public String toString() {
        return "ScopeDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", beanName='" + beanName + '\'' +
                '}';
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    /**
     * 初始化回调方法
     */
    @PostConstruct
    public void init() {
        System.out.printf("当前bean [%s] 正在进行初始化...%n", beanName);
    }

    /**
     * bean销毁回调方法
     */
    @PreDestroy
    public void destroy() {
        System.out.printf("当前bean [%s] 正在进行销毁...%n", beanName);
    }
}
