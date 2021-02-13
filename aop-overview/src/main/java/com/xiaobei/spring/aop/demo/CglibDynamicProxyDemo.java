package com.xiaobei.spring.aop.demo;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 示例 cglib 动态生成字节码
 *
 * TODO {@link org.springframework.cglib.proxy.Enhancer} 与 {@link net.sf.cglib.proxy.Enhancer} 的区别与联系是什么？
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-13 21:31:31
 */
public class CglibDynamicProxyDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(CglibDynamicProxyDemo.class);

    /**
     * 使用 {@link Enhancer} 类来实现 cglib 动态代理
     * @param args
     */
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        // 设定代理类为：{@link DefaultEchoService}
        enhancer.setSuperclass(DefaultEchoService.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object source, Method method,
                                    Object[] args, MethodProxy methodProxy) throws Throwable {
                long startTime = System.currentTimeMillis();
                // 使用 MethodProxy 调用被代理对象的方法
                Object result = methodProxy.invokeSuper(source, args);
                long endTime = System.currentTimeMillis();
                LOGGER.info("[CGLIB] DefaultEchoService 执行时间为 {} ms", (endTime - startTime));
                return result;
            }
        });
        DefaultEchoService echoService = (DefaultEchoService) enhancer.create();
        echoService.echo("xiaobei");
    }
}
