package com.xiaobei.spring.demo.bean.lifecycle.config;

import com.xiaobei.spring.demo.bean.lifecycle.domain.City;
import com.xiaobei.spring.demo.bean.lifecycle.domain.LifeCycleDomain;
import org.springframework.context.annotation.Bean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/9 15:46
 */
public class LifeCycleDomainMetadataConfig {

    @Bean
    public LifeCycleDomain lifeCycleDomain() {
        return new LifeCycleDomain()
                .setId(3L)
                .setName("面向注解")
                .setCity(City.BIEJING);
    }
}
