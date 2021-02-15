package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.service.CustomEchoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 通过 xml 配置方式来指定 advice 切面中各通知的执行优先级
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 16:51:51
 */
public class AspectJXmlOrderAdviceDemo {

    /**
     * 运行结果：
     * 2021-02-15 17:01:09.950 [main] INFO  c.x.spring.aop.demo.adviceorder.AspectAWithXml - @Order(1) 的 Aspect 切面 AspectA 中的 @Before 方法
     * 2021-02-15 17:01:09.954 [main] INFO  c.x.spring.aop.demo.adviceorder.AspectBWithXml - @Order(2) 的 Aspect 切面 AspectB 中的 @Before 方法
     * 2021-02-15 17:01:09.973 [main] INFO  c.x.spring.aop.demo.service.CustomEchoService - [CustomEchoService] Hello tietie
     * @param args
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext("classpath:/META-INF/spring-aop-adviceorder-config.xml");
        CustomEchoService customEchoService = applicationContext.getBean(CustomEchoService.class);
        customEchoService.echo("tietie");
        applicationContext.close();
    }
}
