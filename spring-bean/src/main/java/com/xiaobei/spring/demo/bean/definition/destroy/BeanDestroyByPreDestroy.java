package com.xiaobei.spring.demo.bean.definition.destroy;

import javax.annotation.PreDestroy;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 9:45
 */
public class BeanDestroyByPreDestroy {

    @PreDestroy
    public void destroy() {
        System.out.println("@PreDestroy：执行销毁bean的方法");
    }
}
