package com.xiaobei.spring.demo.dependency.annonation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author xiaobei
 */
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface CustomSource {
}
