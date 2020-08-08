package com.xiaobei.spring.demo.ioc.bean.scope.config;

import com.xiaobei.spring.demo.ioc.bean.scope.domain.ScopeDomain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/8 22:42
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.xiaobei.spring.demo.ioc.bean.scope")
public class SpringWebMvcConfiguration {

    @Bean
    @RequestScope
    public ScopeDomain scopeDomain() {
        return new ScopeDomain()
                .setId(System.currentTimeMillis())
                .setName("xiaobei");
    }
}
