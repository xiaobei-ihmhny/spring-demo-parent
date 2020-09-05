package com.xiaobei.spring.demo.bean.definition.domain;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/1 21:40
 */
public class Person implements InitializingBean {

    private Integer age;

    private String name;

    public Person() {
    }

    public Person(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Person getInstance() {
        Person person = new Person();
        person.setAge(36);
        person.setName("36 实例化Spring Bean：Bean实例化的姿势有多少种？");
        return person;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Person 初始化111...");
    }
}
