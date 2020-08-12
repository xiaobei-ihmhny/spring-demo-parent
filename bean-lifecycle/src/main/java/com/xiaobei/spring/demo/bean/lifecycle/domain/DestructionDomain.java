package com.xiaobei.spring.demo.bean.lifecycle.domain;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/12 20:32
 */
public class DestructionDomain {

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
}
