package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.adviceorder.AspectA;
import com.xiaobei.spring.aop.demo.adviceorder.AspectB;
import com.xiaobei.spring.aop.demo.config.AspectJConfig;
import com.xiaobei.spring.aop.demo.config.OtherBeanConfig;
import com.xiaobei.spring.aop.demo.service.CustomEchoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 示例多个 {@link org.aspectj.lang.annotation.Before @Before} 时指定顺序问题
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 16:48:48
 */
public class AspectJAnnotationOrderAdviceDemo {

    /**
     * 运行结果如下：
     * 2021-02-15 16:49:55.861 [main] INFO  com.xiaobei.spring.aop.demo.adviceorder.AspectA - @Order(1) 的 Aspect 切面 AspectA 中的 @Before 方法
     * 2021-02-15 16:49:55.863 [main] INFO  com.xiaobei.spring.aop.demo.adviceorder.AspectB - @Order(2) 的 Aspect 切面 AspectB 中的 @Before 方法
     * 2021-02-15 16:49:55.888 [main] INFO  c.x.spring.aop.demo.service.CustomEchoService - [CustomEchoService] Hello tietie
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext();
        applicationContext.register(AspectJConfig.class, AspectA.class, AspectB.class, OtherBeanConfig.class);
        applicationContext.refresh();
        CustomEchoService customEchoService = applicationContext.getBean(CustomEchoService.class);
        customEchoService.echo("tietie");
        applicationContext.close();
    }
}
