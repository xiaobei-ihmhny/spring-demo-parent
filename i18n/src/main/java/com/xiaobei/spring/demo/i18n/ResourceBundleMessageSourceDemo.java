package com.xiaobei.spring.demo.i18n;

import org.junit.Test;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.MessageSourceSupport;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.PropertiesPersister;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-17 17:44:44
 */
public class ResourceBundleMessageSourceDemo {

    /**
     *
     * <h2>ResourceBundleMessageSource相关</h2>
     *
     * {@link AbstractMessageSource#getMessage(java.lang.String, java.lang.Object[], java.util.Locale)}
     * {@link ResourceBundleMessageSource#resolveCode(java.lang.String, java.util.Locale)}
     * {@link ResourceBundleMessageSource#getMessageFormat(java.util.ResourceBundle, java.lang.String, java.util.Locale)}
     * {@link MessageSourceSupport#createMessageFormat(java.lang.String, java.util.Locale)}
     *
     *
     * <h2>ReloadableResourceBundleMessageSource相关</h2>
     * {@link ReloadableResourceBundleMessageSource}
     * {@link PropertiesPersister}
     * {@link AbstractMessageSource#getMessage(java.lang.String, java.lang.Object[], java.lang.String, java.util.Locale)}
     * {@link ReloadableResourceBundleMessageSource#resolveCode(java.lang.String, java.util.Locale)}
     */
    @Test
    public void instruction() {

    }
}