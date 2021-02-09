package com.xiaobei.spring.aop.demo;

/**
 * {@link DefaultEchoService} 的静态代理实现
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-02-09 19:40:40
 */
public class StaticProxyEchoService implements EchoService {

    private final EchoService echoService;

    public StaticProxyEchoService(EchoService echoService) {
        this.echoService = echoService;
    }

    @Override
    public String echo(String msg) {
        long startTime = System.currentTimeMillis();
        // 调用目标方法
        String result = echoService.echo(msg);
        long endTime = System.currentTimeMillis();
        System.out.printf("[EchoService] 的执行时间为：%d%n", (endTime - startTime));
        return result;
    }
}
