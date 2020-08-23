package com.xiaobei.spring.demo.generic;

import com.xiaobei.spring.demo.generic.domain.GenericMap;
import com.xiaobei.spring.demo.generic.domain.GenericTypeDomain;
import org.junit.Test;
import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/23 10:14
 */
public class GenericTypeResolverDemo {

    public static void main(String[] args) throws NoSuchMethodException {

    }

    /**
     * 获取方法返回值的类型
     * @throws NoSuchMethodException
     */
    @Test
    public void resolveReturnType() throws NoSuchMethodException {
        resolveReturnType("getString", GenericTypeResolverDemo.class);
        resolveReturnType("listInteger", GenericTypeResolverDemo.class);
    }

    /**
     * <h2>TODO 下面这两个方法如何使用？？？</h2>
     * {@link GenericTypeResolver#resolveType(java.lang.reflect.Type, java.lang.Class)}
     * {@link GenericTypeResolver#resolveType(java.lang.reflect.Type, java.util.Map)}
     */
    public void resolveType() {

    }

    /**
     *
     * 通过 {@link GenericTypeResolver#getTypeVariableMap(java.lang.Class)}
     * 可以获取指定类的父接口或父类中的定义的模糊泛型类型名称及当前类中具体的泛型类型
     * <h2>运行结果：</h2>
     * {M=class java.lang.String, T=class java.lang.Integer, V=class com.xiaobei.spring.demo.generic.GenericDemo, K=class java.lang.Integer}
     */
    @Test
    public void getTypeVariableMap() {
        Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(GenericTypeDomain.class);
        System.out.println(typeVariableMap);
    }

    /**
     * 获取方法返回值类型中的泛型类型
     * @throws NoSuchMethodException
     */
    @Test
    public void resolveReturnTypeArgument() throws NoSuchMethodException {
        resolveReturnTypeArgument("listInteger", GenericTypeResolverDemo.class, List.class);
    }

    /**
     * 获取对应类上的父接口或父类中的参数类型列表
     * <h2>运行结果：</h2>
     * [class java.lang.Integer]
     * [class java.lang.Integer, class com.xiaobei.spring.demo.generic.GenericDemo]
     *
     */
    @Test
    public void resolveTypeArguments() {
        resolveTypeArguments(GenericTypeDomain.class, Comparable.class);
        resolveTypeArguments(GenericTypeDomain.class, GenericMap.class);
    }

    /**
     * 显示返回值类型
     * @param methodName 指定类中的方法名称
     * @param clazz 指定的类
     * @param parameterTypes 方法中的参数类型列表
     * @throws NoSuchMethodException
     */
    private void resolveReturnType(String methodName, Class<?> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = clazz.getMethod(methodName, parameterTypes);
        // 方法返回值类型
        Class<?> returnType = GenericTypeResolver.resolveReturnType(method, clazz);
        System.out.println(returnType);
    }

    /**
     *
     * @param methodName 指定类中的方法名称
     * @param clazz 指定的类
     * @param genericIfc 方法返回值的类型或父接口的类型或父类的类型
     * @param parameterTypes 方法中的参数类型列表
     * @throws NoSuchMethodException
     */
    private void resolveReturnTypeArgument(
            String methodName, Class<?> clazz, Class<?> genericIfc, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = clazz.getMethod(methodName, parameterTypes);
        Class<?> returnTypeArgument = GenericTypeResolver.resolveReturnTypeArgument(method, genericIfc);
        System.out.println(returnTypeArgument);
    }

    /**
     * 解析参数类型
     * @param clazz 指定的类
     * @param genericIfc 方法返回值的类型或父接口的类型或父类的类型
     */
    private void resolveTypeArguments(Class<?> clazz, Class<?> genericIfc) {
        Class<?>[] resolveTypeArguments = GenericTypeResolver.resolveTypeArguments(clazz, genericIfc);
        System.out.println(Arrays.toString(resolveTypeArguments));
    }


    public String getString() {
        return null;
    }

    public List<Integer> listInteger() {
        return null;
    }

    public List<Integer> listInteger(String name, GenericDemo demo) {
        return null;
    }




}
