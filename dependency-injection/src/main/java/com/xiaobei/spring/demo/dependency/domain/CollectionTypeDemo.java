package com.xiaobei.spring.demo.dependency.domain;

import java.util.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-05 17:08:08
 */
public class CollectionTypeDemo {

    /**
     * 原生类型——byte
     */
    private byte[] byteArray;

    /**
     * 标量类型——Boolean
     */
    private List<Boolean> booleanList;

    /**
     * 标量类型——枚举类型
     */
    private List<EnumType> enumTypeList;

    private EnumType[] enumTypeArray;

    /**
     * Spring类型
     */
    private Properties properties;

    private SortedSet<Class> clazzSortedSet;

    public byte[] getByteArray() {
        return byteArray;
    }

    public CollectionTypeDemo setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
        return this;
    }

    public List<Boolean> getBooleanList() {
        return booleanList;
    }

    public CollectionTypeDemo setBooleanList(List<Boolean> booleanList) {
        this.booleanList = booleanList;
        return this;
    }

    public List<EnumType> getEnumTypeList() {
        return enumTypeList;
    }

    public EnumType[] getEnumTypeArray() {
        return enumTypeArray;
    }

    public CollectionTypeDemo setEnumTypeArray(EnumType[] enumTypeArray) {
        this.enumTypeArray = enumTypeArray;
        return this;
    }

    public CollectionTypeDemo setEnumTypeList(List<EnumType> enumTypeList) {
        this.enumTypeList = enumTypeList;
        return this;
    }

    public Properties getProperties() {
        return properties;
    }

    public CollectionTypeDemo setProperties(Properties properties) {
        this.properties = properties;
        return this;
    }

    public SortedSet<Class> getClazzSortedSet() {
        return clazzSortedSet;
    }

    public CollectionTypeDemo setClazzSortedSet(SortedSet<Class> clazzSortedSet) {
        this.clazzSortedSet = clazzSortedSet;
        return this;
    }

    @Override
    public String toString() {
        return "CollectionTypeDemo{" +
                "byteArray=" + Arrays.toString(byteArray) +
                ", booleanList=" + booleanList +
                ", enumTypeList=" + enumTypeList +
                ", enumTypeArray=" + Arrays.toString(enumTypeArray) +
                ", properties=" + properties +
                ", clazzSortedSet=" + clazzSortedSet +
                '}';
    }
}