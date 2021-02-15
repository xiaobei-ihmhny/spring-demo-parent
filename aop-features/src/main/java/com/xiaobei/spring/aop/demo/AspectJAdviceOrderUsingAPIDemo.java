package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.config.EmptyAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 编程方式创建 AspectJ 代理，并确定各切面顺序
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-14 21:25:25
 */
public class AspectJAdviceOrderUsingAPIDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectJAdviceOrderUsingAPIDemo.class);

    public static void main(String[] args) {
        // 创建一个 HashMap 作为被代理对象
        Map<String, Object> map = new HashMap<>(2);
        // 创建 Proxy 工厂
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(map);
        // 增加 Aspect 配置类
        proxyFactory.addAspect(EmptyAspect.class);
        // 根据调用方法 addAdvice 的先后顺序来确定其执行顺序，先添加的优先级高于后添加的
        proxyFactory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                if("put".equals(method.getName()) && args.length == 2) {
                    LOGGER.info("Advice(1) 当前存放的 key 为 {}， value 为 {}", args[0], args[1]);
                }
            }
        });
        proxyFactory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                if("put".equals(method.getName()) && args.length == 2) {
                    LOGGER.info("Advice(2) 当前存放的 key 为 {}， value 为 {}", args[0], args[1]);
                }
            }
        });
        // 使用代理对象向 map 中添加元素
        Map<String, Object> proxyMap = proxyFactory.getProxy();
        proxyMap.put("1", "tietie");
        Object result = proxyMap.get("1");
        LOGGER.info("查询结果为：{}", result);
    }
}
