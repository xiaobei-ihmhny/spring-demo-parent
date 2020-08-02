package com.xiaobei.spring.demo.bean.definition.destroy;

import org.springframework.beans.factory.DisposableBean;

import javax.annotation.PreDestroy;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 10:24
 */
public class BeanDestroyVs implements DisposableBean {

    @PreDestroy
    public void destroyByPreDestroy() {
        System.out.println("@PreDestroy：指定销毁bean的方法");
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy：指定销毁bean的方法");
    }

    public void destroyCustom() {
        System.out.println("自定义方法destroyCustom：指定销毁bean的方法");
    }
}
