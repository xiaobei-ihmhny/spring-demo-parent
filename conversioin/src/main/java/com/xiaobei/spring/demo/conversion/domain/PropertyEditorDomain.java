package com.xiaobei.spring.demo.conversion.domain;

import java.util.Date;
import java.util.Properties;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/20 22:30
 */
public class PropertyEditorDomain {

    private Long id;

    private String name;

    private Date startDate;

    public Long getId() {
        return id;
    }

    public PropertyEditorDomain setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PropertyEditorDomain setName(String name) {
        this.name = name;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public PropertyEditorDomain setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    @Override
    public String toString() {
        return "PropertyEditorDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
