package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.annonation.CustomSource;
import com.xiaobei.spring.demo.dependency.annonation.Source;
import com.xiaobei.spring.demo.dependency.config.CustomSourceAnnotationConfig;
import com.xiaobei.spring.demo.dependency.config.SourceAnnotationConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-06 14:09:09
 */
public class CustomAnnotationDependencyInjectionDemo {

    /**
     * 自定义注解{@link Source}上直接使用{@link Autowired}作为元注解来
     */
    @Test
    public void dependencyInjectionWithAutowiredMetaAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SourceAnnotationConfig.class);
        applicationContext.refresh();
        // 开始依赖查找
        SourceAnnotationConfig bean = applicationContext.getBean(SourceAnnotationConfig.class);
        System.out.println("@Autowired 注入：" + bean.getDomain());
        System.out.println("@Source 注入" + bean.getMyDomain());
        applicationContext.close();
    }

    /**
     * 使用完全自定义注解{@link CustomSource}并配置自定义bean {@link AutowiredAnnotationBeanPostProcessor}
     * 来实现自定义依赖注入注解
     */
    @Test
    public void dependencyInjectionWithCustomSourceAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(CustomSourceAnnotationConfig.class);
        applicationContext.refresh();
        // 开始依赖查找
        CustomSourceAnnotationConfig bean = applicationContext.getBean(CustomSourceAnnotationConfig.class);
        System.out.println("@Autowired 注入：" + bean.getDomain());
        System.out.println("@Inject 注入：" + bean.getDomain2());
        System.out.println("@CustomSource 注入" + bean.getMyDomain());
        applicationContext.close();
    }

    /**
     * TODO 完全通过自定义，需要补上！！
     */
    public void dependencyInjectionWithCustomImplementation() {

    }

}