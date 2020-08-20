package com.xiaobei.spring.demo.conversion;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/20 22:40
 */
public class DatePropertyEditor extends PropertyEditorSupport implements PropertyEditor {

    private final String pattern = "yyyy-MM-dd HH:mm:ss";

//    public DatePropertyEditor(String pattern) {
//        this.pattern = pattern;
//    }

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(value);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        Date date;
        try {
            date = sdf.parse(text);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
        setValue(date);
    }
}
