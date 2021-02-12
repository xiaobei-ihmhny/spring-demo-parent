package com.xiaobei.spring.aop.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 目标方法过滤示例
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-02-12 06:49:49
 */
@DisplayName("Java AOP 目标方法过滤")
public class TargetFilterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetFilterTest.class);

    private static Class<?> targetClass;

    @BeforeAll
    public static void init() throws ClassNotFoundException {
        String targetClassName = "com.xiaobei.spring.aop.demo.TargetFilter";
        // 获取当前线程的 {@link ClassLoader}
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 获取 Class 对象
        targetClass = classLoader.loadClass(targetClassName);
    }

    /**
     * 执行结果为：
     * 找到的方法签名为：public abstract void com.xiaobei.spring.aop.demo.TargetFilter.method1(java.lang.String)
     *
     * @throws ClassNotFoundException
     */
    @Test
    @DisplayName("通过方法名称和参数列表过滤方法")
    public void filterMethodByMethodNameAndMethodArgs() {
        // 通过 ClassLoader 获取指定的 Class
        Class<?> target = targetClass;
        // 利用 Spring 的反射api操作
        // 查找指定方法名称和方法列表的方法
        Method method1 = ReflectionUtils.findMethod(target, "method1", String.class);
        LOGGER.info("找到的方法签名为：{}", method1);
    }

    @Test
    @DisplayName("通过指定条件进行过滤")
    public void filterMethodByMethodArgsAndThrows() {
        ReflectionUtils.doWithMethods(targetClass,
                // 过滤后的方法
                new ReflectionUtils.MethodCallback() {
                    /**
                     * 当匹配到多个满足条件的方法时，该方法 {@link #doWith(Method)} 会被调用多次
                     * @param method
                     * @throws IllegalArgumentException
                     * @throws IllegalAccessException
                     */
                    @Override
                    public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                        LOGGER.info("匹配到的方法为：{}", method);
                    }
                },
                // 过滤条件
                new ReflectionUtils.MethodFilter() {
                    @Override
                    public boolean matches(Method method) {
                        // 方法抛出的异常列表
                        Class<?>[] exceptionTypes = method.getExceptionTypes();
                        // 方法参数列表
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        // 匹配方法参数有一个且为 String 类型，
                        // 且仅抛出 {@link NullPointerException} 异常的方法
                        return parameterTypes.length == 1
                                && String.class.equals(parameterTypes[0])
                                && exceptionTypes.length == 1
                                && NullPointerException.class.equals(exceptionTypes[0]);
                    }
                });
    }




}
