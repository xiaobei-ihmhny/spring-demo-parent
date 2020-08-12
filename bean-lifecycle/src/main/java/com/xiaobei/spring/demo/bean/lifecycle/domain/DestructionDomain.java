package com.xiaobei.spring.demo.bean.lifecycle.domain;

import org.springframework.beans.factory.DisposableBean;

import javax.annotation.PreDestroy;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/12 20:32
 */
public class DestructionDomain implements DisposableBean {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public DestructionDomain setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DestructionDomain setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "DestructionDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @PreDestroy
    public void preDestroy() {
        this.name = "16-1";
        System.out.println(this);
    }

    /**
     * Bean的销毁回调
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        this.name = "16-2";
        System.out.println(this);
    }

    public void initDestroy() {
        this.name = "16-3";
        System.out.println(this);
    }
}
