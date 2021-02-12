package com.xiaobei.spring.aop.demo;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

/**
 * Java AOP 示例 demo
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-02-09 19:37:37
 */
public class EchoServiceTest {

    /**
     * 原方法直接调用
     */
    @Test
    public void echo() {
        EchoService echoService = new DefaultEchoService();
        echoService.echo("xiaobei");
    }

    /**
     * 静态代理示例
     */
    @Test
    public void staticProxyEcho() {
        EchoService echoService = new StaticProxyEchoService(new DefaultEchoService());
        echoService.echo("xiaobei");
    }

    @Test
    public void dynamicProxy() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Object proxy = Proxy.newProxyInstance(classLoader,
                new Class[]{EchoService.class},
                new TimeStatisticsInvocationHandler<EchoService>(new DefaultEchoService()));
        EchoService echoService = (EchoService) proxy;
        echoService.echo("xiaobei");
    }

    /**
     * TODO 待完善
     */
    @Test
    public void cglibProxy() {
        CglibProxyEchoService echoService = new CglibProxyEchoService();
    }

}
