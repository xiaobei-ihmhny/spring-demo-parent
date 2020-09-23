package com.xiaobei.spring.demo.conversion;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;

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

    static class StringToPropertiesPropertyEditor extends PropertyEditorSupport implements PropertyEditor {

        // 1. 实现 setAsText(String) 方法
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            // 2. 将 String 类型转换成 Properties 类型
            Properties properties = new Properties();
            try {
                properties.load(new StringReader(text));
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
            // 3. 临时存储 Properties 对象
            setValue(properties);
        }

        @Override
        public String getAsText() {
            Properties properties = (Properties) getValue();
            StringBuilder sb = new StringBuilder();
            for(Map.Entry entry : properties.entrySet()) {
                sb.append(entry.getKey()).append("=")
                        .append(entry.getValue())
                        .append(System.getProperty("line.separator"));
            }
            return sb.toString();
        }
    }

}
