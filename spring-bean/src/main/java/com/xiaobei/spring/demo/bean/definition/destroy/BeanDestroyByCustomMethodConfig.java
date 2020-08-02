package com.xiaobei.spring.demo.bean.definition.destroy;

import org.springframework.context.annotation.Bean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 9:52
 */
public class BeanDestroyByCustomMethodConfig {

    @Bean(destroyMethod = "destroyCustom")
    public BeanDestroyByCustomMethod customMethod() {
        return new BeanDestroyByCustomMethod("销毁Bean方法：@Bean(destroyMethod=\"destroyCustom\")");
    }
}
