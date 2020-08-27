package com.xiaobei.spring.demo.annotation.attribute.overrides;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/27 14:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@ComponentScan
@Repeatable(CustomizedComponentScans.class)
public @interface CustomizedComponentScan {

    /**
     * 隐性覆盖
     *
     * 其中名称 {@link #basePackages} 与 {@link ComponentScan#basePackages} 名称相同，
     * 前者自动覆盖后者，这种情况称为：Implicit Overrides
     * @return
     */
    String[] basePackages() default {};

    /**
     * 显式覆盖
     *
     * 其中名称 {@link #scanBasePackages} 与 {@link ComponentScan#basePackages()} 名称不相同，
     * 但通过 {@link AliasFor} 指定其为 {@link ComponentScan#basePackages} 的别名，
     * 这种情况称为：Explicit Overrides
     * @return
     */
    @AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
    String[] scanBasePackages() default {};
}
