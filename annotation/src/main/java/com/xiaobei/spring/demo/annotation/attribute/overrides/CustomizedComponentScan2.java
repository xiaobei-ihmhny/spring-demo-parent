package com.xiaobei.spring.demo.annotation.attribute.overrides;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/27 14:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@CustomizedComponentScan
public @interface CustomizedComponentScan2 {

    @AliasFor(annotation = CustomizedComponentScan.class, attribute = "scanBasePackages")
    String[] scanBasePackages2() default {};
}
