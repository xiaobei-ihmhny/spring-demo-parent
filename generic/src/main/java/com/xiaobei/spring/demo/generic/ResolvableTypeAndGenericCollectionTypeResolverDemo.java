package com.xiaobei.spring.demo.generic;

import com.xiaobei.spring.demo.generic.domain.GenericCollectionDomain;
import com.xiaobei.spring.demo.generic.domain.ResolvableTypeDomain;
import org.junit.Test;
import org.springframework.core.GenericCollectionTypeResolver;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * {@link GenericCollectionTypeResolver} 使用示例以及其替换实现 {@link ResolvableType} 的示例
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/23 12:41
 */
public class ResolvableTypeAndGenericCollectionTypeResolverDemo {

    private static GenericCollectionDomain domain;

    private static List<Integer> list;

    /**
     * <h2>运行结果：</h2>
     * class com.xiaobei.spring.demo.generic.GenericDemo
     * null
     * <p>
     * <p>
     * {@link GenericCollectionTypeResolver} 在 spring 5 之后版本中已移除
     *
     * @see GenericCollectionTypeResolver
     */
    @Test
    public void getCollectionElementType() {
        // 返回具体化泛型参数类型集合的成员类型
        Class<?> collectionType = GenericCollectionTypeResolver.getCollectionType(GenericCollectionDomain.class);
        // class com.xiaobei.spring.demo.generic.GenericDemo
        System.out.println(collectionType);
        // 泛型未具体化时，将返回 {@code null}
        Class<?> arrayListElementType = GenericCollectionTypeResolver.getCollectionType(ArrayList.class);
        // null
        System.out.println(arrayListElementType);
    }

    /**
     * <h2>运行结果：</h2>
     * class com.xiaobei.spring.demo.generic.GenericDemo
     * class java.lang.Integer
     *
     * @throws NoSuchFieldException {@link GenericCollectionTypeResolver} 在 spring 5 之后版本中已移除
     */
    @Test
    public void getCollectionFieldType() throws NoSuchFieldException {
        // 获取集合类型字段中的成员变量类型
        Field field = ResolvableTypeAndGenericCollectionTypeResolverDemo.class.getDeclaredField("domain");
        Class<?> collectionFieldType = GenericCollectionTypeResolver.getCollectionFieldType(field);
        System.out.println(collectionFieldType);
        // 获取集合类型字段中的成员变量类型
        Field listField = ResolvableTypeAndGenericCollectionTypeResolverDemo.class.getDeclaredField("list");
        Class<?> listFieldType = GenericCollectionTypeResolver.getCollectionFieldType(listField);
        System.out.println(listFieldType);
    }

    /**
     * 获取类的父类信息
     *
     * <h2>运行结果：</h2>
     * 当前类的父类的类型：class com.xiaobei.spring.demo.generic.domain.GenericMap
     * 当前类的父类的类型中的第一个泛型的类型：class java.lang.Integer
     * 当前类的父类的类型中的第二个泛型的类型：class com.xiaobei.spring.demo.generic.GenericDemo
     * 获取所有的泛型信息 class java.lang.Integer
     * 获取所有的泛型信息 class com.xiaobei.spring.demo.generic.GenericDemo
     */
    @Test
    public void displaySuperClassesInfo() {
        ResolvableType resolvableType = ResolvableType.forClass(ResolvableTypeDomain.class);
        // 获取父类 ResolvableType 对象
        ResolvableType superType = resolvableType.getSuperType();
        // 获取父类类型
        Class<?> superClass = superType.getRawClass();
        System.out.printf("当前类的父类的类型：%s\n", superClass);
        Class<?> firstRawClass = superType.getGeneric(0).getRawClass();
        System.out.printf("当前类的父类的类型中的第一个泛型的类型：%s\n", firstRawClass);
        Class<?> secondRawClass = superType.getGeneric(1).getRawClass();
        System.out.printf("当前类的父类的类型中的第二个泛型的类型：%s\n", secondRawClass);
        // 获取父类类型中的泛型类型信息
        ResolvableType[] superClassGenerics = superType.getGenerics();
        Arrays.stream(superClassGenerics)
                .map(ResolvableType::getRawClass)
                .forEach(rawClass -> System.out.printf("获取所有的泛型信息 %s\n", rawClass));
    }

    /**
     * 获取类的父接口信息
     *
     * <h2>运行结果：</h2>
     * 当前父接口的类型：interface java.lang.Comparable
     * 当前父接口[interface java.lang.Comparable]中获取到的所有的泛型类型信息 class java.lang.Integer
     * 当前父接口的类型：interface com.xiaobei.spring.demo.generic.domain.GenericList
     * 当前父接口[interface com.xiaobei.spring.demo.generic.domain.GenericList]中获取到的所有的泛型类型信息 class java.lang.String
     */
    @Test
    public void displaySuperInterfacesInfo() {
        ResolvableType resolvableType = ResolvableType.forClass(ResolvableTypeDomain.class);
        ResolvableType[] interfaces = resolvableType.getInterfaces();
        Arrays.stream(interfaces)
                .forEach(type -> {
                    Class<?> currentSuperClass = type.getRawClass();
                    System.out.printf("当前父接口的类型：%s\n", currentSuperClass);
                    ResolvableType[] generics = type.getGenerics();
                    Arrays.stream(generics)
                            .map(ResolvableType::getRawClass)
                            .forEach(rawClass -> System.out.printf("当前父接口[%s]中获取到的所有的泛型类型信息 %s\n", currentSuperClass, rawClass));
                });
    }

    /**
     * 获取字段类型信息
     *
     * <h2>运行结果：</h2>
     * 当前字段类型：class com.xiaobei.spring.demo.generic.domain.GenericMap
     * 当前泛型类型为：class java.lang.Integer
     * 当前类[interface java.util.List]的泛型类型为：class java.lang.String
     * 当前泛型类型为：class java.lang.String
     *
     * @throws NoSuchFieldException
     */
    @Test
    public void displayFieldInfoOfGenericMap() throws NoSuchFieldException {
        displayFieldInfo("genericMap");
    }

    /**
     * 获取字段信息
     *
     * <h2>运行结果：</h2>
     * 当前字段类型：interface com.xiaobei.spring.demo.generic.domain.GenericList
     * 当前类[interface java.util.List]的泛型类型为：interface java.util.Map
     * 当前类[interface java.util.Map]的泛型类型为：class java.lang.String
     * 当前泛型类型为：class java.lang.String
     * 当前类[interface java.util.Map]的泛型类型为：class java.lang.Integer
     * 当前泛型类型为：class java.lang.Integer
     * @throws NoSuchFieldException
     */
    @Test
    public void displayFieldInfoOfList() throws NoSuchFieldException {
        displayFieldInfo("list");
    }

    /**
     * 获取构造器信息
     *
     * <h2>运行结果：</h2>
     * 当前类[class com.xiaobei.spring.demo.generic.domain.GenericMap]的泛型类型为：class java.lang.Integer
     * 当前泛型类型为：class java.lang.Integer
     * 当前类[class com.xiaobei.spring.demo.generic.domain.GenericMap]的泛型类型为：interface java.util.List
     * 当前类[interface java.util.List]的泛型类型为：class java.lang.String
     * 当前泛型类型为：class java.lang.String
     * 当前类[interface com.xiaobei.spring.demo.generic.domain.GenericList]的泛型类型为：interface java.util.List
     * 当前类[interface java.util.List]的泛型类型为：interface java.util.Map
     * 当前类[interface java.util.Map]的泛型类型为：class java.lang.String
     * 当前泛型类型为：class java.lang.String
     * 当前类[interface java.util.Map]的泛型类型为：class java.lang.Integer
     * 当前泛型类型为：class java.lang.Integer
     * 当前类[interface java.util.List]的泛型类型为：class java.lang.String
     * 当前泛型类型为：class java.lang.String
     */
    @Test
    public void displayConstructorInfo() {
        Constructor<?>[] constructors = ResolvableTypeDomain.class.getConstructors();
        Stream.of(constructors).forEach(constructor -> {
            int parameterCount = constructor.getParameterCount();
            for (int i = 0; i < parameterCount; i++) {
                ResolvableType resolvableType = ResolvableType.forConstructorParameter(constructor, i);
                displayGenerics(resolvableType);
            }
        });
    }

    /**
     * 获取方法参数信息
     * <h2>运行结果：</h2>
     * 当前方法为：compareTo
     * 当前泛型类型为：class java.lang.Integer
     * =============================
     * 当前方法为：compareTo
     * 当前泛型类型为：class java.lang.Object
     * =============================
     * 当前方法为：genericMap
     * 当前类[interface java.util.List]的泛型类型为：interface java.util.Map
     * 当前类[interface java.util.Map]的泛型类型为：class java.lang.String
     * 当前泛型类型为：class java.lang.String
     * 当前类[interface java.util.Map]的泛型类型为：class java.math.BigDecimal
     * 当前泛型类型为：class java.math.BigDecimal
     * =============================
     * 当前方法为：genericMap
     * 当前泛型类型为：class java.lang.String
     * =============================
     * 当前方法为：genericList
     * 当前类[class com.xiaobei.spring.demo.generic.domain.GenericMap]的泛型类型为：class java.lang.Double
     * 当前泛型类型为：class java.lang.Double
     * 当前类[class com.xiaobei.spring.demo.generic.domain.GenericMap]的泛型类型为：class java.lang.Float
     * 当前泛型类型为：class java.lang.Float
     * =============================
     * 当前方法为：wait
     * 当前泛型类型为：long
     * =============================
     * 当前方法为：wait
     * 当前泛型类型为：int
     * =============================
     * 当前方法为：wait
     * 当前泛型类型为：long
     * =============================
     * 当前方法为：equals
     * 当前泛型类型为：class java.lang.Object
     * =============================
     *
     */
    @Test
    public void displayMethodParameterInfo() {
        Method[] methods = ResolvableTypeDomain.class.getMethods();
        Stream.of(methods).forEach(method -> {
            int parameterCount = method.getParameterCount();
            for (int i = 0; i < parameterCount; i++) {
                ResolvableType methodParameter = ResolvableType.forMethodParameter(method, i);
                System.out.printf("当前方法为：%s\n", method.getName());
                displayGenerics(methodParameter);
                System.out.println("=============================");
            }
        });
    }

    /**
     * 获取方法返回值类型信息
     *
     * <h2>运行结果：</h2>
     * 当前方法为：compareTo
     * 当前泛型类型为：int
     * =============================
     * 当前方法为：compareTo
     * 当前泛型类型为：int
     * =============================
     * 当前方法为：genericMap
     * 当前类[class com.xiaobei.spring.demo.generic.domain.GenericMap]的泛型类型为：class java.lang.String
     * 当前泛型类型为：class java.lang.String
     * 当前类[class com.xiaobei.spring.demo.generic.domain.GenericMap]的泛型类型为：class java.lang.Boolean
     * 当前泛型类型为：class java.lang.Boolean
     * =============================
     * 当前方法为：genericMap
     * 当前类[class com.xiaobei.spring.demo.generic.domain.GenericMap]的泛型类型为：class java.lang.String
     * 当前泛型类型为：class java.lang.String
     * 当前类[class com.xiaobei.spring.demo.generic.domain.GenericMap]的泛型类型为：class java.lang.Boolean
     * 当前泛型类型为：class java.lang.Boolean
     * =============================
     * 当前方法为：genericList
     * 当前类[interface com.xiaobei.spring.demo.generic.domain.GenericList]的泛型类型为：interface java.util.List
     * 当前类[interface java.util.List]的泛型类型为：class java.lang.String
     * 当前泛型类型为：class java.lang.String
     * =============================
     * 当前方法为：wait
     * 当前泛型类型为：void
     * =============================
     * 当前方法为：wait
     * 当前泛型类型为：void
     * =============================
     * 当前方法为：wait
     * 当前泛型类型为：void
     * =============================
     * 当前方法为：equals
     * 当前泛型类型为：boolean
     * =============================
     */
    @Test
    public void displayMethodReturnTypeInfo() {
        Method[] methods = ResolvableTypeDomain.class.getMethods();
        Stream.of(methods).forEach(method -> {
            int parameterCount = method.getParameterCount();
            for (int i = 0; i < parameterCount; i++) {
                ResolvableType methodParameter = ResolvableType.forMethodReturnType(method);
                System.out.printf("当前方法为：%s\n", method.getName());
                displayGenerics(methodParameter);
                System.out.println("=============================");
            }
        });
    }


    private void displayFieldInfo(String fieldName) throws NoSuchFieldException {
        Field genericMapField = ResolvableTypeDomain.class.getDeclaredField(fieldName);
        ResolvableType genericMapFieldResolvableType = ResolvableType.forField(genericMapField);
        Class<?> currentFieldClass = genericMapFieldResolvableType.getRawClass();
        System.out.printf("当前字段类型：%s\n", currentFieldClass);
        ResolvableType[] generics = genericMapFieldResolvableType.getGenerics();
        Arrays.stream(generics).forEach(this::displayGenerics);
    }

    private void displayGenerics(ResolvableType resolvableType) {
        ResolvableType[] generics = resolvableType.getGenerics();
        Class<?> rawClass = resolvableType.getRawClass();
        if(generics.length == 0) {
            System.out.printf("当前泛型类型为：%s\n", rawClass);
        } else {
            Stream.of(generics).forEach(generic -> {
                Class<?> subRawClass = generic.getRawClass();
                System.out.printf("当前类[%s]的泛型类型为：%s\n", rawClass, subRawClass);
                displayGenerics(generic);
            });
        }
    }

}
