package com.xiaobei.spring.demo.conversion.domain;

import java.util.Properties;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/20 22:30
 */
public class ConditionalGenericConverterDomain {

    private Long id;

    private String name;

    private Properties properties;

    private String propertiesAsText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getPropertiesAsText() {
        return propertiesAsText;
    }

    public void setPropertiesAsText(String propertiesAsText) {
        this.propertiesAsText = propertiesAsText;
    }

    @Override
    public String toString() {
        return "ConditionalGenericConverterDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", properties=" + properties +
                ", propertiesAsText='" + propertiesAsText + '\'' +
                '}';
    }
}
