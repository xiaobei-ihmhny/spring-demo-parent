package com.xiaobei.spring.demo.conversion;

import org.junit.Test;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/20 22:31
 */
public class CustomizedPropertiesPropertyEditorDemo {

    /**
     * 自定义 pojo 实现 String类型转换为 Properties类型
     */
    static class StringToPropertiesDomain {

        private Properties properties;

        public Properties getProperties() {
            return properties;
        }

        public void setProperties(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String toString() {
            return "StringToPropertiesDomain{" +
                    "properties=" + properties +
                    '}';
        }
    }

    /**
     * 自定义类型转换器注册器实现
     */
    static class CustomizedPropertyEditorRegister implements PropertyEditorRegistrar {

        @Override
        public void registerCustomEditors(PropertyEditorRegistry registry) {
            registry.registerCustomEditor(Properties.class, new StringToPropertiesPropertyEditor());
        }
    }

    /**
     * 自定义类型转换器实现字符类型到 Properties 类型的转换
     */
    @SuppressWarnings("DuplicatedCode")
    static class StringToPropertiesPropertyEditor extends PropertyEditorSupport implements PropertyEditor {

        /**
         * 1. 实现 setAsText(String) 方法
         * 限定属性之间使用半角 ; 隔开，key和value之间使用 = 隔开
         * @param text
         * @throws IllegalArgumentException
         */
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            // 2. 将 String 类型转换成 Properties 类型
            Properties properties = new Properties();
            String[] split = text.split("!");
            Arrays.stream(split).forEach(property -> {
                String[] keyValue = property.split("=");
                System.out.printf("当前的属性信息为：%s = %s\n", keyValue[0], keyValue[1]);
                properties.setProperty(keyValue[0].trim(), keyValue[1].trim());
            });
            // 3. 临时存储 Properties 对象
            setValue(properties);
        }

        @Override
        public String getAsText() {
            Properties properties = (Properties) getValue();
            StringBuilder sb = new StringBuilder();
            for (Map.Entry entry : properties.entrySet()) {
                sb.append(entry.getKey()).append("=")
                        .append(entry.getValue())
                        .append(System.getProperty("line.separator"));
            }
            return sb.toString();
        }
    }

    /**
     * <h2>自定义 PropertyEditor 的步骤</h2>
     * 1. 扩展类 {@link PropertyEditorSupport} 并重写其 {@link PropertyEditorSupport#setAsText(String) setAsText}，
     * 一般也需要重写其{@link PropertyEditorSupport#getAsText()} getAsText}，这个不强求。
     * 2. 向类 {@link PropertyEditorRegistrar} 注册自定义的 {@link PropertyEditor} 实现。
     * 3. 将注册好的 {@link PropertyEditorRegistrar} 添加到 Spring 内建的Ioc后置处理器
     * {@link CustomEditorConfigurer#setPropertyEditorRegistrars(PropertyEditorRegistrar[])} 中，并注册为 Spring Bean
     *
     * <h2>运行结果</h2>
     * 当前的属性信息为：name = naTie
     * 当前的属性信息为：id = 1
     * StringToPropertiesDomain{properties={name=naTie, id=1}}
     */
    @Test
    public void stringToProperties() {
        ConfigurableApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:META-INF/property-editors-register-context.xml");
        StringToPropertiesDomain propertyDomain = applicationContext.getBean("domain", StringToPropertiesDomain.class);
        System.out.println(propertyDomain);
        applicationContext.close();
    }
}
