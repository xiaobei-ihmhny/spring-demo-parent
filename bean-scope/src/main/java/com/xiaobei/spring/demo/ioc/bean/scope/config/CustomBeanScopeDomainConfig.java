package com.xiaobei.spring.demo.ioc.bean.scope.config;

import com.xiaobei.spring.demo.ioc.bean.scope.domain.ScopeDomain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/9 9:36
 */
public class CustomBeanScopeDomainConfig {

    /**
     * 自定义的线程级别的作用域
     * @return
     */
    @Bean
    @Scope(ThreadLocalScope.SCOPE_NAME)
    public ScopeDomain customScopeDomain () {
        return createBeanScopeDemo();
    }

    public ScopeDomain createBeanScopeDemo() {
        return new ScopeDomain()
                .setId(System.currentTimeMillis() + Math.abs(ThreadLocalRandom.current().nextInt() % 100))
                .setName("");
    }
}
