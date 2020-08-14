package com.xiaobei.spring.demo.configuration.metadata.domain;

import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;

/**
 * 演示 {@link AbstractBeanDefinitionReader}的两种实现类：XML、Properties的元信息配置方式
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-14 09:49:49
 *
 * @see XmlBeanDefinitionReader
 * @see PropertiesBeanDefinitionReader
 */
public class ResourceDomain {

    /**
     * 原生类型——byte
     */
    private byte byteType;

    /**
     * 标量类型——Boolean
     */
    private Boolean basicType;

    /**
     * 标量类型——String
     */
    private String name;

    /**
     * 标量类型——Long
     */
    private Long id;

    /**
     * 标量类型——枚举类型
     */
    private EnumType enumType;

    /**
     * Spring类型
     */
    private Resource resourceLocation;

    private Class clazz;

    public byte getByteType() {
        return byteType;
    }

    public void setByteType(byte byteType) {
        this.byteType = byteType;
    }

    public Boolean getBasicType() {
        return basicType;
    }

    public void setBasicType(Boolean basicType) {
        this.basicType = basicType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumType getEnumType() {
        return enumType;
    }

    public void setEnumType(EnumType enumType) {
        this.enumType = enumType;
    }

    public Resource getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(Resource resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "ResourceDomain{" +
                "byteType=" + byteType +
                ", basicType=" + basicType +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", enumType=" + enumType +
                ", resourceLocation=" + resourceLocation +
                ", clazz=" + clazz +
                '}';
    }
}