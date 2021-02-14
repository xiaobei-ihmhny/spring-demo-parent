package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.config.AspectDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 编程方式创建 @AspectJ 代理
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-14 21:25:25
 */
public class AspectJUsingAPIDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectJUsingAPIDemo.class);

    public static void main(String[] args) {
        // 创建一个 HashMap 作为被代理对象
        Map<String, Object> map = new HashMap<>(2);
        // 创建 Proxy 工厂
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(map);
        // 增加 Aspect 配置类
        proxyFactory.addAspect(AspectDemo.class);
        proxyFactory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                if("put".equals(method.getName()) && args.length == 2) {
                    LOGGER.info("当前存放的 key 为 {}， value 为 {}", args[0], args[1]);
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
