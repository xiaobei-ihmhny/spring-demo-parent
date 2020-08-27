package com.xiaobei.spring.demo.annotation.enable.importselector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 通过 {@link ImportSelector} 实现模块驱动
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-27 15:50:50
 * @see ImportSelector
 */
public class HelloWorldImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.xiaobei.spring.demo.annotation.enable.configuration.HelloWorldConfiguration"};
    }
}