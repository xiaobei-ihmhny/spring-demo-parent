package com.xiaobei.spring.demo.domain;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/30 18:27
 */
public class Student {

    private Integer id;

    private String name;

    @Autowired
    private ClassRoom classRoom;

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

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classRoom.name=" + classRoom.getName() +
                '}';
    }
}
