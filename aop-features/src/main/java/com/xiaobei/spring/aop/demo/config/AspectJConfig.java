package com.xiaobei.spring.aop.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 使用 {@link EnableAspectJAutoProxy} 激活 AspectJ
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-14 20:53:53
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectJConfig {

    @Bean
    public AspectDemo aspectDemo() {
        return new AspectDemo();
    }

}
