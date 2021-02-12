package com.xiaobei.spring.aop.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * AOP 的拦截器模式 示例
 * TODO 示例很简单但需要将其与真实的AOP实现结合起来理解？？？
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-12 20:24:24
 */
public class AopInterceptorDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopInterceptorDemo.class);

    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Object object = Proxy.newProxyInstance(classLoader, new Class[]{EchoService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(EchoService.class.isAssignableFrom(method.getDeclaringClass())
                        && "echo".equals(method.getName())) {
                    BeforeInterceptor beforeInterceptor = new BeforeInterceptor() {
                        @Override
                        public Object before(Object proxy, Method method, Object[] args) {
                            long result = System.currentTimeMillis();
                            LOGGER.info("前置拦截结果：{}", result);
                            return result;
                        }
                    };
                    // 执行前置拦截
                    Long startTime = (Long) beforeInterceptor.before(proxy, method, args);
                    Long endTime;
                    String result = null;
                    DefaultEchoService echoService = new DefaultEchoService();
                    try {
                        result = echoService.echo((String) args[0]);
                    } catch (Exception e) {
                        // 执行异常拦截
                        ExceptionInterceptor exceptionInterceptor = new ExceptionInterceptor() {
                            @Override
                            public void interceptor(Object proxy, Method method, Object[] args, Throwable throwable) {
                                LOGGER.error("方法执行发生异常, ", throwable);
                            }
                        };
                        exceptionInterceptor.interceptor(proxy, method, args, e);
                    } finally {
                        // 执行后置拦截
                        AfterInterceptor afterInterceptor = new AfterInterceptor() {
                            @Override
                            public Object after(Object proxy, Method method, Object[] args, Object result) {
                                long afterResult = System.currentTimeMillis();
                                LOGGER.info("前置拦截结果：{}", afterResult);
                                return afterResult;
                            }
                        };
                        endTime = (Long) afterInterceptor.after(proxy, method, args, result);
                    }
                    System.out.printf("当前方法执行耗时：%d", (endTime - startTime));
                    return result;
                }
                // TODO 如何避免空指针异常？
                return null;
            }
        });
        EchoService echoService = (EchoService) object;
        echoService.echo("tietie");
    }
}
