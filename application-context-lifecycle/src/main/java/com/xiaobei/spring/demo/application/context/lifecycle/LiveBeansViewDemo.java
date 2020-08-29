package com.xiaobei.spring.demo.application.context.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.LiveBeansView;

import java.io.IOException;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/29 20:17
 */
public class LiveBeansViewDemo {


    /**
     * <h2>通过 jconsole 等工具查看相关的 MBean 得到的响应如下：</h2>
     * [
     *   {
     *     "context": "org.springframework.context.annotation.AnnotationConfigApplicationContext@43a25848",
     *     "parent": null,
     *     "beans": [
     *       {
     *         "bean": "liveBeansViewDemo",
     *         "aliases": [
     *
     *         ],
     *         "scope": "singleton",
     *         "type": "com.xiaobei.spring.demo.application.context.lifecycle.LiveBeansViewDemo",
     *         "resource": "null",
     *         "dependencies": [
     *
     *         ]
     *       }
     *     ]
     *   }
     * ]
     *
     *
     * @param args
     * @throws IOException
     * @see LiveBeansView#registerApplicationContext(org.springframework.context.ConfigurableApplicationContext)
     * @see LiveBeansView#generateJson(java.util.Set)
     *
     */
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        System.setProperty(LiveBeansView.MBEAN_DOMAIN_PROPERTY_NAME, "com.xiaobei.spring.demo.application.context.lifecycle");
        applicationContext.register(LiveBeansViewDemo.class);

        applicationContext.refresh();

        System.out.println("按任意键继续...");
        System.in.read();
        applicationContext.close();
    }
}
