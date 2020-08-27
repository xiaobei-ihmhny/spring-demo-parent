package com.xiaobei.spring.demo.annotation.attribute.overrides;

import java.lang.annotation.*;

/**
 * 使用 java8 中的 {@link Repeatable} 实现可重复注解
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-27 15:24:57
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface CustomizedComponentScans {

    CustomizedComponentScan[] value();
}
