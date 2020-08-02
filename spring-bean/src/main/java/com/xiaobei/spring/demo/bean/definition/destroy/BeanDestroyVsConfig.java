package com.xiaobei.spring.demo.bean.definition.destroy;

import org.springframework.context.annotation.Bean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 10:27
 */
public class BeanDestroyVsConfig {

    @Bean(destroyMethod = "destroyCustom")
    public BeanDestroyVs beanDestroyVs() {
        return new BeanDestroyVs();
    }
}
