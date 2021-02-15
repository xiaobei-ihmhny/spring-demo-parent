package com.xiaobei.spring.aop.demo.config;

import com.xiaobei.spring.aop.demo.service.CustomEchoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义普通的bean
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 07:59:59
 */
@Configuration
public class OtherBeanConfig {

    @Bean
    public CustomEchoService echoService() {
        return new CustomEchoService();
    }
}
