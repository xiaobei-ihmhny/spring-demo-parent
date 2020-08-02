package com.xiaobei.spring.demo.bean.definition.initialization;

import javax.annotation.PostConstruct;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 7:37
 */
public class BeanInitializationByPostConstruct {

    @PostConstruct
    public void initByPostConstruct() {
        System.out.println("通过注解@PostConstruct来实现bean的初始化");
    }
}
