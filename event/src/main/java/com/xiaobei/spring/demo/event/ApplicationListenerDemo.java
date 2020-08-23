package com.xiaobei.spring.demo.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.GenericApplicationContext;

/**
 * {@link ApplicationListener}
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/23 22:00
 */
public class ApplicationListenerDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        // 向 Spring 应用上下文注册事件
//        applicationContext.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
//            @Override
//            public void onApplicationEvent(ApplicationEvent event) {
//                System.out.printf("接收到 Spring 事件：%s\n", event);
//            }
//        });
        applicationContext.addApplicationListener(event -> System.out.printf("接收到 Spring 事件：%s\n", event));
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 启动 Spring 上下文
        applicationContext.start();
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }
}
