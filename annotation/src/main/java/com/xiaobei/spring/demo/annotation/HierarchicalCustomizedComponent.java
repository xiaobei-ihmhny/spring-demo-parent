package com.xiaobei.spring.demo.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 自定义 {@link org.springframework.stereotype.Component} 的多层次派生
 * 即：{@link CustomizedComponent} 的派生性
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/26 22:16
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CustomizedComponent
public @interface HierarchicalCustomizedComponent {

    @AliasFor(annotation = CustomizedComponent.class)
    String value() default "";
}
