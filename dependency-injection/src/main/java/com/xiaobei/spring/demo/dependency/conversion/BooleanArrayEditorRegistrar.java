package com.xiaobei.spring.demo.dependency.conversion;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/9/8 21:23
 */
public class BooleanArrayEditorRegistrar implements PropertyEditorRegistrar {

    private final BooleanArrayEditor booleanArrayEditor;

    public BooleanArrayEditorRegistrar(BooleanArrayEditor booleanArrayEditor) {
        this.booleanArrayEditor = booleanArrayEditor;
    }

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(List.class, booleanArrayEditor);
    }
}
