package com.xiaobei.spring.demo.generic;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * java 5 泛型示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/23 9:35
 */
public class GenericAPIDemo {

    @Test
    public void basicUsage() {
        // 原生类型 primitive types: int float double...
        Class intClass = int.class;
        // intClass: int
        System.out.printf("intClass: %s\n", intClass);
        // 数组类型 array types: int[] object[]...
        Class objectArrayClass = Object[].class;
        // objectArrayClass: class [Ljava.lang.Object;
        System.out.printf("objectArrayClass: %s\n", objectArrayClass);
        // 原始类型 raw types: java.lang.String
        Class rawClass = String.class;
        // rawClass: class java.lang.String
        System.out.printf("rawClass: %s\n", rawClass);
        // 泛型数组类型
        Class<ArrayList> arrayListClass = ArrayList.class;
        // 泛型父类（Super Classes）
        Type genericSuperclass = arrayListClass.getGenericSuperclass();
        // genericSuperclass: java.util.AbstractList<E>
        System.out.printf("genericSuperclass: %s\n", genericSuperclass);
        // 泛型参数
        if(genericSuperclass instanceof ParameterizedType) {
            // 泛型参数（Parameters）
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            // parameterizedType: java.util.AbstractList<E>
            System.out.printf("parameterizedType: %s\n", parameterizedType);
            // 泛型参数 （E）
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            // type: E
            Stream.of(typeArguments).map(TypeVariable.class::cast)
                    .forEach(type -> System.out.printf("type: %s\n", type));
        }
        // 泛型接口（Interfaces）
        Type[] genericInterfaces = arrayListClass.getGenericInterfaces();
        // genericInterfaces: java.util.List<E>
        // genericInterfaces: interface java.util.RandomAccess
        // genericInterfaces: interface java.lang.Cloneable
        // genericInterfaces: interface java.io.Serializable
        Stream.of(genericInterfaces).forEach(
                genericInterface -> System.out.printf("genericInterfaces: %s\n", genericInterface));
    }

    /**
     * TODO 补充
     */
    @Test
    public void basicUsage2() {
        Class userServiceClass = UserService.class;
        Type[] genericInterfaces = userServiceClass.getGenericInterfaces();

    }

    static class User {}

    static class SuperUser extends User {}

    static class Order {}

    static class SuperOrder extends Order {}

    static interface UserService<U extends User, O extends Order> {

        U queryUser(O order);

    }

    static class UserServiceImpl implements UserService<SuperUser, SuperOrder> {

        @Override
        public SuperUser queryUser(SuperOrder order) {
            return null;
        }
    }
}
