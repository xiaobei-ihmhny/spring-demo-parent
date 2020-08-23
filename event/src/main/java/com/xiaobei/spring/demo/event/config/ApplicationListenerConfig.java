package com.xiaobei.spring.demo.event.config;

import org.springframework.context.event.*;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * {@link EnableAsync} 开启异步支持
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/23 22:32
 */
@EnableAsync
public class ApplicationListenerConfig {

    @EventListener
    public void onApplication(ContextStartedEvent startedEvent) {
        println("接收到事件：ContextStartedEvent");
    }

    @EventListener
    public void onApplication(ContextRefreshedEvent refreshedEvent) {
        println("接收到事件：ContextRefreshedEvent");
    }

    @EventListener
    public void onApplication(ContextClosedEvent closedEvent) {
        println("接收到事件：ContextClosedEvent");
    }

    @EventListener
    public void onApplication(ContextStoppedEvent stoppedEvent) {
        println("接收到事件：ContextStoppedEvent");
    }


    @EventListener
    @Order(2)
    public void onApplication1(ContextRefreshedEvent startedEvent) {
        println("接收到事件 Order(2)：ContextStartedEvent");
    }

    /**
     * {@link Order} 指定监听处理顺序，其中值越小优先级越高
     *
     * @param refreshedEvent
     */
    @EventListener
    @Order(1)
    public void onApplication2(ContextRefreshedEvent refreshedEvent) {
        println("接收到事件 Order(1)：ContextRefreshedEvent");
    }

    /**
     * {@link Order} 指定监听处理顺序，其中值越小优先级越高
     *
     * @param refreshedEvent
     */
    @EventListener
    @Async
    public void onApplicationAsync(ContextRefreshedEvent refreshedEvent) {
        println("接收到事件 Async：ContextRefreshedEvent");
    }

    private void println(String msg) {
        System.out.printf("当前线程[%s]，事件为：%s\n", Thread.currentThread().getName(), msg);
    }
}
