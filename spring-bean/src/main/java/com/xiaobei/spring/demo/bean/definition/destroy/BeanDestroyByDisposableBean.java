package com.xiaobei.spring.demo.bean.definition.destroy;

import org.springframework.beans.factory.DisposableBean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 9:49
 */
public class BeanDestroyByDisposableBean implements DisposableBean {

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy：执行销毁bean的方法");
    }
}
