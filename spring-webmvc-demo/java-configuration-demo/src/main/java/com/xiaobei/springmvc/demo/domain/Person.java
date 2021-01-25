package com.xiaobei.springmvc.demo.domain;

import java.io.Serializable;

/**
 * {@link Person} 类
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-25 23:20:20
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 2039020278015475205L;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 姓名
     */
    private String first_name;

    /**
     * 出生日期：
     * 日期类型
     */
    private String birth_day;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(String birth_day) {
        this.birth_day = birth_day;
    }
}
