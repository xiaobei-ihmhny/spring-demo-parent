package com.xiaobei.spring.demo.application.context.lifecycle;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

/**
 * Spring Shutdown Hook 线程示例
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/29 21:07
 */
public class SpringShutdownHookThreadDemo {

    /**
     * <h2>运行结果：</h2>
     * 按任意键继续并关闭 Spring 应用上下文...
     *
     * [线程 main 接收到 ContextClosedEvent 事件]
     *
     * 在应用上下文刷新前 手动调用 {@link AbstractApplicationContext#registerShutdownHook()} 方法后，
     * 当非法终止线程时，有可能会进行优雅关闭。
     * TODO kill -1 ... 可以，kill -3 不行？？？？
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        GenericApplicationContext applicationContext = new GenericApplicationContext();

        applicationContext.addApplicationListener(new ApplicationListener<ContextClosedEvent>() {
            @Override
            public void onApplicationEvent(ContextClosedEvent event) {
                System.out.printf("[线程 %s 接收到 ContextClosedEvent 事件]", Thread.currentThread().getName());
            }
        });

        // 手动调用注册 shutdownHook 线程
        applicationContext.registerShutdownHook();
        applicationContext.refresh();
        System.out.println("按任意键继续并关闭 Spring 应用上下文...");
        System.in.read();

        applicationContext.close();
    }
}
