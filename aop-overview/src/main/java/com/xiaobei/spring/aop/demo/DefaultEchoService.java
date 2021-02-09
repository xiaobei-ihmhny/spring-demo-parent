package com.xiaobei.spring.aop.demo;

/**
 * {@link EchoService} 的默认实现
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-02-09 19:35:35
 */
public class DefaultEchoService implements EchoService {

    @Override
    public String echo(String msg) {
        System.out.printf("Hello %s%n", msg);
        return msg;
    }
}
