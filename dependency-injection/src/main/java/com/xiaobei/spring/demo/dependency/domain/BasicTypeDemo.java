package com.xiaobei.spring.demo.dependency.domain;

import org.springframework.core.io.Resource;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-05 17:08:08
 */
public class BasicTypeDemo {

    /**
     * 原生类型——byte
     */
    private byte byteType;

    /**
     * 标量类型——Boolean
     */
    private Boolean basicType;

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

    public BasicTypeDemo setByteType(byte byteType) {
        this.byteType = byteType;
        return this;
    }

    public Boolean getBasicType() {
        return basicType;
    }

    public BasicTypeDemo setBasicType(Boolean basicType) {
        this.basicType = basicType;
        return this;
    }

    public EnumType getEnumType() {
        return enumType;
    }

    public BasicTypeDemo setEnumType(EnumType enumType) {
        this.enumType = enumType;
        return this;
    }

    public Resource getResourceLocation() {
        return resourceLocation;
    }

    public BasicTypeDemo setResourceLocation(Resource resourceLocation) {
        this.resourceLocation = resourceLocation;
        return this;
    }

    public Class getClazz() {
        return clazz;
    }

    public BasicTypeDemo setClazz(Class clazz) {
        this.clazz = clazz;
        return this;
    }

    @Override
    public String toString() {
        return "BasicTypeDemo{" +
                "\n  byteType=" + byteType +
                ", \n  basicType=" + basicType +
                ", \n  enumType=" + enumType +
                ", \n  resourceLocation=" + resourceLocation +
                ", \n  clazz=" + clazz +
                "\n  }\n";
    }
}