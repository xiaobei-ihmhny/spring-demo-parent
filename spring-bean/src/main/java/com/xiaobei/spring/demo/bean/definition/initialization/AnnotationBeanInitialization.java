package com.xiaobei.spring.demo.bean.definition.initialization;

import org.springframework.context.annotation.Bean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 7:46
 */
public class AnnotationBeanInitialization {

    @Bean(initMethod = "initBeanMethodWithAnnotation")
    public BeanInitialization beanInitialization() {
        return new BeanInitialization("");
    }
}
