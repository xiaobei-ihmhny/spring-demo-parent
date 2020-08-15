package com.xiaobei.spring.demo.configuration.metadata.extensible;

import com.xiaobei.spring.demo.configuration.metadata.domain.ResourceDomain;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/15 23:22
 */
public class UserBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return ResourceDomain.class;
    }

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String beanClassName = element.getAttribute("x-id");
        if(StringUtils.hasText(beanClassName)) {
            builder.getBeanDefinition().setBeanClassName(beanClassName);
        }
        setPropertyValue("id", element, builder);
        setPropertyValue("name", element, builder);
        setPropertyValue("enumType", element, builder);
    }

    private void setPropertyValue(String elementName, Element element, BeanDefinitionBuilder builder) {
        String attributeValue = element.getAttribute(elementName);
        if(StringUtils.hasText(attributeValue)) {
            builder.addPropertyValue(elementName, attributeValue);
        }
    }



}
