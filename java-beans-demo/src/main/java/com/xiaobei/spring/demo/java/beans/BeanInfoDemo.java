package com.xiaobei.spring.demo.java.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyEditorSupport;
import java.util.stream.Stream;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-07-27 13:56:56
 */
public class BeanInfoDemo {

    public static void main(String[] args) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        showPropertyDescriptors(beanInfo);
    }

    /**
     * TODO 应用场景是什么？？
     * @param beanInfo
     */
    static void showPropertyEditor(BeanInfo beanInfo) {
        Stream.of(beanInfo.getPropertyDescriptors())
                .forEach(propertyDescriptor -> {
                    Class<?> propertyType = propertyDescriptor.getPropertyType();
                    String name = propertyDescriptor.getName();
                    if ("age".equals(name)) {
                        // 为age字段添加propertyEditor
                        // String -> Integer
                        // Integer.valueOf("");
                        propertyDescriptor.setPropertyEditorClass(StringToIntegerPropertyEditor.class);
                    }
                });
    }

    static class StringToIntegerPropertyEditor extends PropertyEditorSupport {

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Integer value = Integer.valueOf(text);
            setValue(value);
        }
    }

    /**
     * 打印出 PropertyDescriptor 的内容
     * <p>
     * java.beans.PropertyDescriptor[name=age; propertyType=class java.lang.Integer; readMethod=public java.lang.Integer com.xiaobei.spring.demo.java.beans.Person.getAge(); writeMethod=public void com.xiaobei.spring.demo.java.beans.Person.setAge(java.lang.Integer)]
     * java.beans.PropertyDescriptor[name=name; propertyType=class java.lang.String; readMethod=public java.lang.String com.xiaobei.spring.demo.java.beans.Person.getName(); writeMethod=public void com.xiaobei.spring.demo.java.beans.Person.setName(java.lang.String)]
     *
     * @param beanInfo bean信息
     */
    static void showPropertyDescriptors(BeanInfo beanInfo) {
        Stream.of(beanInfo.getPropertyDescriptors())
                .forEach(System.out::println);
    }
}