package com.xiaobei.spring.aop.demo.pointcut;

import com.xiaobei.spring.aop.demo.service.EchoService;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 创建一个仅针对 {@link com.xiaobei.spring.aop.demo.service.EchoService#echo(String)} 方法的 pointcut
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-16 16:26:26
 */
public class EchoServiceEchoMethodPointcut implements Pointcut {

    public static final EchoServiceEchoMethodPointcut INSTANCE = new EchoServiceEchoMethodPointcut();

    private EchoServiceEchoMethodPointcut(){}

    @Override
    public ClassFilter getClassFilter() {
        return new ClassFilter() {

            @Override
            public boolean matches(Class<?> clazz) {
                return EchoService.class.isAssignableFrom(clazz);// EchoService接口、子接口或子类均可
            }
        };
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return new MethodMatcher() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return "echo".equals(method.getName())
                        && method.getParameterTypes().length == 1
                        && Objects.equals(String.class, method.getParameterTypes()[0]);
            }

            /**
             * 当该方法返回false时，不会调用到 {@link #matches(Method, Class, Object...)}
             * @return
             */
            @Override
            public boolean isRuntime() {
                return false;
            }

            @Override
            public boolean matches(Method method, Class<?> targetClass, Object... args) {
                // should never be invoked because isRuntime() returns false
                throw new UnsupportedOperationException("Illegal MethodMatcher usage");
            }
        };
    }
}
