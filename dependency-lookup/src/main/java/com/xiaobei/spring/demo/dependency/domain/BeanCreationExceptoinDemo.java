package com.xiaobei.spring.demo.dependency.domain;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-04 10:18:18
 */
public class BeanCreationExceptoinDemo implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        throw new RuntimeException("bean 初始化异常");
    }
}