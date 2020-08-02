package com.xiaobei.spring.demo.bean.definition.initialization;

import org.springframework.context.annotation.Bean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 7:37
 */
public class BeanInitializationVsConfig {

    @Bean(initMethod = "initBeanWithCustomMethod")
    public BeanInitializationVs beanInitializationVs() {
        return new BeanInitializationVs();
    }
}
