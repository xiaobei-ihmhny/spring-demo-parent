package com.xiaobei.spring.demo.conversion;

import java.beans.PropertyEditor;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/20 21:09
 */
public class PropertyEditorDemo {

    /**
     * 模拟 Spring Framework 操作 一段文件 name=拿铁
     *
     * <h2>运行结果：</h2>
     * {name=拿铁}
     * name=拿铁
     *
     * @param args
     */
    public static void main(String[] args) {
        String text = "name = 拿铁";
        PropertyEditor propertyEditor = new StringToPropertiesPropertyEditor();
        propertyEditor.setAsText(text);
        // 通过 PropertyEditor#getValue()方法获取类型转换后的对象
        Object value = propertyEditor.getValue();
        String asText = propertyEditor.getAsText();
        System.out.println(value);
        System.out.println(asText);
    }
}
