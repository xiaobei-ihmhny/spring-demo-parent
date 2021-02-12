package com.xiaobei.spring.aop.demo;

/**
 * 目标过滤类
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-02-12 06:51:51
 */
public interface TargetFilter {

    void method1(String args1);

    void method1(String args1, Integer args2);

    void method2(String args1) throws NullPointerException;
}
