package com.xiaobei.spring.demo.annotation.enable.importselector;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-27 15:49:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({HelloWorldImportSelector.class})
public @interface EnableHelloWorld {
}