package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.config.AspectDemo;
import com.xiaobei.spring.aop.demo.config.AspectJConfig;
import com.xiaobei.spring.aop.demo.config.OtherBeanConfig;
import com.xiaobei.spring.aop.demo.service.CustomEchoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * AspectJ Pointcut 示例
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 07:53:53
 */
public class AspectJAnnotationPointcutDemo {

    /**
     * 运行结果为：
     * 2021-02-15 08:19:14.906 [main] INFO  com.xiaobei.spring.aop.demo.config.AspectDemo - @Before in any public method.
     * 2021-02-15 08:19:15.016 [main] INFO  com.xiaobei.spring.aop.demo.config.AspectDemo - @Before in any public method.
     * 2021-02-15 08:19:15.017 [main] INFO  com.xiaobei.spring.aop.demo.config.AspectDemo - @Before in any echo method.
     * 2021-02-15 08:19:15.027 [main] INFO  c.x.spring.aop.demo.service.CustomEchoService - [CustomEchoService] Hello tietie
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext();
        applicationContext.register(OtherBeanConfig.class, AspectJConfig.class, AspectDemo.class);
        applicationContext.refresh();
        CustomEchoService echoService = applicationContext.getBean(CustomEchoService.class);
        echoService.echo("tietie");
        applicationContext.close();
    }
}
