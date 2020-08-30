package com.xiaobei.spring.demo.domain;

import org.springframework.beans.factory.BeanNameAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/30 10:04
 */
public class QuestionsDomain implements BeanNameAware {

    private Integer id;

    private String name;

    private String beanName;

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
        System.out.printf("[bean：%s] 正在初始化 QuestionsDomain...\n", this.beanName);
    }

    @PreDestroy
    public void destroy() {
        System.out.printf("[bean：%s] 正在销毁 QuestionsDomain...\n", this.beanName);
    }

    @Override
    public String toString() {
        return "QuestionsDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
