package com.xiaobei.springmvc.demo.databinder;

import java.lang.annotation.*;

/**
 * 请求参数绑定
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-23 11:04:04
 */
@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParamName {

    String name();
}
