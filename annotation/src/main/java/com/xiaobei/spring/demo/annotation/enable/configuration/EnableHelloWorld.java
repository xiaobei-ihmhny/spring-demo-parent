package com.xiaobei.spring.demo.annotation.enable.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 基于 {@code Configuration Class} 实现 {@code EnableXXX} 模块驱动
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-27 15:44:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({HelloWorldConfiguration.class})
public @interface EnableHelloWorld {
}
