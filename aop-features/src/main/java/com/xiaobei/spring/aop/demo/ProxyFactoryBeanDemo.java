package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.service.EchoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * {@link org.springframework.aop.framework.ProxyFactoryBean} 示例
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-14 22:52:52
 */
public class ProxyFactoryBeanDemo {

    /**
     * TODO {@code 代理对象 echoServiceProxy} 与 {@link org.springframework.aop.framework.JdkDynamicAopProxy} 的关系是什么呢？
     * @param args
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext("classpath:/META-INF/spring-aop-proxyfactorybean-config.xml");
        EchoService echoServiceProxy = applicationContext.getBean("echoServiceProxy", EchoService.class);
        echoServiceProxy.echo("tietie");
        applicationContext.close();

    }
}
