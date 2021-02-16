package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.proxyfactory.CustomMethodInterceptor;
import com.xiaobei.spring.aop.demo.service.CustomEchoService;
import com.xiaobei.spring.aop.demo.service.DefaultEchoService;
import com.xiaobei.spring.aop.demo.service.EchoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

/**
 * 相比 {@link org.springframework.aop.framework.ProxyFactoryBean}，
 * {@link ProxyFactory} 更加底层
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-14 23:04:04
 */
public class ProxyFactoryDemo {

    @Test
    @DisplayName("没有继承接口时的代理，此时使用 CGLIB 动态代理")
    public void noInterfaceProxy() {
        CustomEchoService echoService = new CustomEchoService();
        // 注入目标对象（被代理对象）
        ProxyFactory proxyFactory = new ProxyFactory(echoService);
        // 添加 Advice 实现 MethodInterceptor
        proxyFactory.addAdvice(new CustomMethodInterceptor());
        // 获取代理对象
        CustomEchoService proxy = (CustomEchoService) proxyFactory.getProxy();
        proxy.echo("tietie");
    }

    @Test
    @DisplayName("有继承接口时的代理，此时使用 JDK 动态代理")
    public void implInterfaceProxy() {
        EchoService echoService = new DefaultEchoService();
        // 注入目标对象（被代理对象）
        ProxyFactory proxyFactory = new ProxyFactory(echoService);
        // 添加 Advice 实现 MethodInterceptor
        proxyFactory.addAdvice(new CustomMethodInterceptor());
        // 获取代理对象
        EchoService proxy = (EchoService) proxyFactory.getProxy();
        proxy.echo("tietie");
    }

}
