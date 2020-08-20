package com.xiaobei.spring.demo.data.binding;

import com.xiaobei.spring.demo.data.binding.domain.DataBindingDomain;
import com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.stream.Stream;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-20 13:52:52
 */
public class JavaBeanDemo {

    /**
     * <h2>结果说明：</h2>
     * 其中的javaBean不能是Builder模式，否则相关的方法将无法读取
     *
     *
     * <h2>运行结果：</h2>
     * java.beans.PropertyDescriptor[name=city; propertyType=class com.xiaobei.spring.demo.data.binding.domain.City; readMethod=public com.xiaobei.spring.demo.data.binding.domain.City com.xiaobei.spring.demo.data.binding.domain.DataBindingDomain.getCity()]
     * java.beans.PropertyDescriptor[name=class; propertyType=class java.lang.Class; readMethod=public final native java.lang.Class java.lang.Object.getClass()]
     * java.beans.PropertyDescriptor[name=id; propertyType=class java.lang.Long; readMethod=public java.lang.Long com.xiaobei.spring.demo.data.binding.domain.DataBindingDomain.getId()]
     * java.beans.PropertyDescriptor[name=name; propertyType=class java.lang.String; readMethod=public java.lang.String com.xiaobei.spring.demo.data.binding.domain.DataBindingDomain.getName()]
     *
     *
     * @throws IntrospectionException
     */
    @Test
    public void showPropertyDescriptorsForBuilder() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(DataBindingDomain.class);
        Stream.of(beanInfo.getPropertyDescriptors()).forEach(System.out::println);
    }

    /**
     * <h2>运行结果：</h2>
     * java.beans.PropertyDescriptor[name=city; propertyType=class com.xiaobei.spring.demo.data.binding.domain.City; readMethod=public com.xiaobei.spring.demo.data.binding.domain.City com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.getCity(); writeMethod=public void com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.setCity(com.xiaobei.spring.demo.data.binding.domain.City)]
     * java.beans.PropertyDescriptor[name=class; propertyType=class java.lang.Class; readMethod=public final native java.lang.Class java.lang.Object.getClass()]
     * java.beans.PropertyDescriptor[name=id; propertyType=class java.lang.Long; readMethod=public java.lang.Long com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.getId(); writeMethod=public void com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.setId(java.lang.Long)]
     * java.beans.PropertyDescriptor[name=name; propertyType=class java.lang.String; readMethod=public java.lang.String com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.getName(); writeMethod=public void com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.setName(java.lang.String)]
     *
     *
     * @throws IntrospectionException
     */
    @Test
    public void showPropertyDescriptors() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(JavaBeanDomain.class);
        Stream.of(beanInfo.getPropertyDescriptors()).forEach(System.out::println);
    }

    /**
     * 可以通过重载方法 {@link Introspector#getBeanInfo(java.lang.Class, java.lang.Class)} 来指定需要排除的父类
     *
     * <h2>运行结果：</h2>
     * java.beans.PropertyDescriptor[name=city; propertyType=class com.xiaobei.spring.demo.data.binding.domain.City; readMethod=public com.xiaobei.spring.demo.data.binding.domain.City com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.getCity(); writeMethod=public void com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.setCity(com.xiaobei.spring.demo.data.binding.domain.City)]
     * java.beans.PropertyDescriptor[name=id; propertyType=class java.lang.Long; readMethod=public java.lang.Long com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.getId(); writeMethod=public void com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.setId(java.lang.Long)]
     * java.beans.PropertyDescriptor[name=name; propertyType=class java.lang.String; readMethod=public java.lang.String com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.getName(); writeMethod=public void com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.setName(java.lang.String)]
     *
     * @throws IntrospectionException
     */
    @Test
    public void showPropertyDescriptorsExcludeParent() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(JavaBeanDomain.class, Object.class);
        Stream.of(beanInfo.getPropertyDescriptors()).forEach(System.out::println);
    }

    /**
     * 方法描述中也包含 {@code get} {@code set} 方法
     *
     * <h2>运行结果：</h2>
     * java.beans.MethodDescriptor[name=getName; method=public java.lang.String com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.getName()]
     * java.beans.MethodDescriptor[name=setId; method=public void com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.setId(java.lang.Long)]
     * java.beans.MethodDescriptor[name=getCity; method=public com.xiaobei.spring.demo.data.binding.domain.City com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.getCity()]
     * java.beans.MethodDescriptor[name=setCity; method=public void com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.setCity(com.xiaobei.spring.demo.data.binding.domain.City)]
     * java.beans.MethodDescriptor[name=setName; method=public void com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.setName(java.lang.String)]
     * java.beans.MethodDescriptor[name=getId; method=public java.lang.Long com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.getId()]
     * java.beans.MethodDescriptor[name=toString; method=public java.lang.String com.xiaobei.spring.demo.data.binding.domain.JavaBeanDomain.toString()]
     *
     * @throws IntrospectionException
     */
    @Test
    public void showMethodDescriptors() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(JavaBeanDomain.class, Object.class);
        Stream.of(beanInfo.getMethodDescriptors()).forEach(System.out::println);
    }
}