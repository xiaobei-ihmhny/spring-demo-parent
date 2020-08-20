package com.xiaobei.spring.demo.conversion;

import com.xiaobei.spring.demo.conversion.domain.PropertyEditorDomain;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/20 22:31
 */
public class CustomizedPropertyEditorDemo {

    /**
     * <h2>运行结果</h2>
     * PropertyEditorDomain{id=163, name='自定义PropertyEditor扩展：不尝试怎么知道它好不好用？', startDate=Thu Aug 20 22:37:40 CST 2020}
     *
     * @param args
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:META-INF/property-editors-context.xml");
        PropertyEditorDomain propertyDomain = applicationContext.getBean("propertyDomain", PropertyEditorDomain.class);
        System.out.println(propertyDomain);
        applicationContext.close();
    }
}
