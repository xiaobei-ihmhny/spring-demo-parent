package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.service.CustomEchoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 10:48:48
 */
public class AspectJXmlPointcutDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext("classpath:/META-INF/spring-aop-pointcut-config.xml");
        applicationContext.refresh();
        CustomEchoService echoService = applicationContext.getBean("echoService", CustomEchoService.class);
        echoService.echo("tietie");
        applicationContext.close();
    }
}
