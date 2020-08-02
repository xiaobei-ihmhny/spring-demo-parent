package com.xiaobei.spring.demo.bean.definition.initialization;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 7:37
 */
public class BeanInitializationByImplementsInitializationBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("通过实现InitializationBean的afterPropertiesSet方法实现bean的初始化..");
    }
}
