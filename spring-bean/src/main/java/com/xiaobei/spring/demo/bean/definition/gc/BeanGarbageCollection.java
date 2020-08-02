package com.xiaobei.spring.demo.bean.definition.gc;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 10:44
 */
public class BeanGarbageCollection {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("当前bean正在被gc回收...");
    }
}
