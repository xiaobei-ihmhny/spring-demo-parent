package com.xiaobei.spring.demo.validation.domain;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-19 10:29:29
 */
public class ValidationDomain {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public ValidationDomain setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ValidationDomain setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ValidationDomain{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}