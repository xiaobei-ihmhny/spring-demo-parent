package com.xiaobei.spring.demo.event;

import org.springframework.context.ApplicationListener;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/24 21:19
 */
public class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {

    @Override
    public void onApplicationEvent(MyApplicationEvent event) {
        System.out.printf("[线程 ：%s] 监听到事件：%s\n" ,Thread.currentThread().getName(), event);
    }
}
