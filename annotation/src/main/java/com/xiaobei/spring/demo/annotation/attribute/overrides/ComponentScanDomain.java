package com.xiaobei.spring.demo.annotation.attribute.overrides;

import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-27 14:57:57
 */
@Component
public class ComponentScanDomain {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ComponentScanDomain{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}