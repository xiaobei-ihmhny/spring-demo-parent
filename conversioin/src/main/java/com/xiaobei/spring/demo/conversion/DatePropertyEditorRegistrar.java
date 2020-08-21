package com.xiaobei.spring.demo.conversion;

import com.xiaobei.spring.demo.conversion.domain.PropertyEditorDomain;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import java.util.Date;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/20 22:38
 */
public class DatePropertyEditorRegistrar implements PropertyEditorRegistrar {

    private final DatePropertyEditor datePropertyEditor;

    public DatePropertyEditorRegistrar(DatePropertyEditor datePropertyEditor) {
        this.datePropertyEditor = datePropertyEditor;
    }

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        System.out.println("开始进行日期类型转换...");
        registry.registerCustomEditor(Date.class, datePropertyEditor);
//        registry.registerCustomEditor(PropertyEditorDomain.class, "startDate",  new DatePropertyEditor());
//        registry.registerCustomEditor(PropertyEditorDomain.class, "startDate",  new DatePropertyEditor());
    }
}
