package com.xiaobei.spring.demo.conversion;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/22 16:11
 */
public class PropertiesToStringConverter implements ConditionalGenericConverter {

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return Properties.class.equals(sourceType.getObjectType())
                && String.class.equals(targetType.getObjectType());
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Properties.class, String.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
//        return convertByStringBuilder(source);
        return convertByOutputStream(source);
    }

    private String convertByOutputStream(Object source) {
        try {
            Properties properties = (Properties) source;
            ByteArrayOutputStream os = new ByteArrayOutputStream(256);
            properties.store(os, null);
            return os.toString("UTF-8");
        } catch (IOException e) {
            // Should never happen.
            throw new IllegalArgumentException("Failed to store [" + source + "] into String", e);
        }
    }

    private String convertByStringBuilder(Object source) {
        Properties properties = (Properties) source;
        StringBuilder textBuilder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            textBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(System.getProperty("line.separator"));
        }
        return textBuilder.toString();
    }
}
