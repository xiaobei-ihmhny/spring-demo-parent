package com.xiaobei.spring.demo.resource;

import com.xiaobei.spring.demo.resource.utils.ResourceUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 21:35
 */
public class InjectingResourceDemo {

    /**
     * 注入单个资源对象
     */
    @Value("classpath:/META-INF/dev.properties")
    private Resource devResource;

    @Value("classpath*:/META-INF/*.properties")
    private Resource[] propertiesResources;

    /**
     * TODO 多个资源信息无法正常集合类型？？？？
     */
//    @Value("classpath*:/META-INF/*.properties")
    private List<Resource> propertiesResourceList;

    @PostConstruct
    public void init() {
        System.out.println(ResourceUtils.getContext(devResource));
        System.out.println("====================================");
        Stream.of(propertiesResources).map(ResourceUtils::getContext).forEach(System.out::println);
        System.out.println("====================================");
        if(propertiesResourceList != null) propertiesResourceList.stream().map(ResourceUtils::getContext).forEach(System.out::println);

    }

    /**
     * <h2>运行结果：</h2>
     * name=131
     * ====================================
     * name=131
     * name=\u4F9D\u8D56\u6CE8\u5165Spring Resource
     */
    @Test
    public void injectResource() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册当前类
        applicationContext.register(InjectingResourceDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 关闭应用上下文
        applicationContext.close();
    }
}
