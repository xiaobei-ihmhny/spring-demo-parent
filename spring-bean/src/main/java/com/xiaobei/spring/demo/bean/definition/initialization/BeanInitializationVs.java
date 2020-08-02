package com.xiaobei.spring.demo.bean.definition.initialization;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 7:37
 */
public class BeanInitializationVs implements InitializingBean {

    /**
     * 自定义初始化方法——注解方式
     */
    public void initBeanWithCustomMethod() {
        System.out.println("BeanInitializationVs：自定义初始化方法");
    }

    @PostConstruct
    public void initByPostConstruct() {
        System.out.println("BeanInitializationVs：通过注解@PostConstruct来实现bean的初始化");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("BeanInitializationVs：通过实现InitializationBean的afterPropertiesSet方法实现bean的初始化..");
    }
}
