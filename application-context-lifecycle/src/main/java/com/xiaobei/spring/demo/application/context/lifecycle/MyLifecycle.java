package com.xiaobei.spring.demo.application.context.lifecycle;

import org.springframework.context.Lifecycle;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@link Lifecycle} 示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/29 20:39
 * @see Lifecycle
 */
public class MyLifecycle implements Lifecycle {

    private final AtomicBoolean running = new AtomicBoolean();

    @Override
    public void start() {
        running.set(Boolean.TRUE);
        System.out.println("MyLifecycle 已启动...");
    }

    @Override
    public void stop() {
        running.set(Boolean.FALSE);
        System.out.println("MyLifecycle 已停止");
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }
}
