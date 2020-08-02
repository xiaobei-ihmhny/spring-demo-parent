package com.xiaobei.spring.demo.bean.definition.lazy;

import com.xiaobei.spring.demo.bean.definition.initialization.BeanInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 9:09
 */
public class LazyBeanInitializationConf {

    @Bean(name = "beanInitialization")
    @Lazy(false)
    public BeanInitialization beanInitialization() {
        return new BeanInitialization("非延迟加载");
    }

    @Bean(name = "beanInitializationLazy")
    @Lazy(true)
    public BeanInitialization beanInitializationLazy() {
        return new BeanInitialization("延迟加载");
    }
}
