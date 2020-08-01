package com.xiaobei.spring.demo.bean.definition.register;

import org.springframework.context.annotation.Import;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/1 16:15
 */
@Import({ComponentAnnotation.class, BeanAnnotation.class})
public class ImportAnnotation {

    private ComponentAnnotation componentAnnotation;

    public ComponentAnnotation getComponentAnnotation() {
        return componentAnnotation;
    }

    public void setComponentAnnotation(ComponentAnnotation componentAnnotation) {
        this.componentAnnotation = componentAnnotation;
    }

    @Override
    public String toString() {
        return "ImportAnnotation{" +
                "componentAnnotation=" + componentAnnotation +
                '}';
    }
}
