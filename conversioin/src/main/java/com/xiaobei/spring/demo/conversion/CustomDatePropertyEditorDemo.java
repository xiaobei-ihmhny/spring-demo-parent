package com.xiaobei.spring.demo.conversion;

import org.junit.Test;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/9/23 22:44
 */
public class CustomDatePropertyEditorDemo {

    static class DateDomain {
        private Date date;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "DateDomain{" +
                    "date=" + date +
                    '}';
        }
    }

    /**
     * 创建自定义的日期格式转换器
     */
    static class DatePropertyEditor extends PropertyEditorSupport implements PropertyEditor {

        private final String pattern;

        public DatePropertyEditor(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public String getAsText() {
            Date date = (Date) getValue();
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(date);
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            LocalDateTime localDateTime = LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            Date date = Date.from(zonedDateTime.toInstant());
            setValue(date);
        }
    }

    static class DatePropertyEditorRegistrar implements PropertyEditorRegistrar {

        private final DatePropertyEditor datePropertyEditor;

        public DatePropertyEditorRegistrar(DatePropertyEditor datePropertyEditor) {
            this.datePropertyEditor = datePropertyEditor;
        }

        @Override
        public void registerCustomEditors(PropertyEditorRegistry registry) {
            registry.registerCustomEditor(Date.class, datePropertyEditor);
        }
    }

    @Test
    public void stringToDate() {
        String configLocations = "META-INF/property-editors-date-register-context.xml";
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocations);
        DateDomain domain = applicationContext.getBean("domain", DateDomain.class);
        System.out.println(domain);
        applicationContext.close();

    }

}
