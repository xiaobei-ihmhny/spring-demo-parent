package com.xiaobei.spring.demo.configuration.metadata;

import com.xiaobei.spring.demo.configuration.metadata.domain.ResourceDomain;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 8:54
 */
@PropertySource("classpath:/META-INF/configuration-for-properties-other.properties")
public class PropertySourceDemo {

    @Bean
    public ResourceDomain domain(@Value("${user.name}") String username) {
        ResourceDomain domain = new ResourceDomain();
        domain.setName(username);
        return domain;
    }

    /**
     *
     * <h2>运行结果：</h2>
     * domain -> ResourceDomain{byteType=0, basicType=null, name='lenovo', nickname='null', id=null, enumType=null, resourceLocation=null, clazz=null}
     * PropertiesPropertySource : user.name -> lenovo
     * ResourcePropertySource : user.name -> 拿铁
     *
     * <h2>结果说明：</h2>
     * 说明除了我们自定义的 {@link org.springframework.core.env.PropertySource}之外还有其他默认添加的，
     * 比如本例中的 {@link PropertySourceDemo}<br>
     * 而且也说明了不同的{@link org.springframework.core.env.PropertySource}之间是有优先级的，且最终的属性值以优先级高的为准
     */
    @Test
    public void propertySourcePriorityOriginal() {
        // 不添加特殊 {@code PropertySource}
        propertySourcePriority((applicationContext) ->{});

    }

    /**
     *
     * <h2>运行结果：</h2>
     * domain -> ResourceDomain{byteType=0, basicType=null, name='小贝', nickname='null', id=null, enumType=null, resourceLocation=null, clazz=null}
     * MapPropertySource[my-property-source] : user.name -> 小贝
     * PropertiesPropertySource[systemProperties] : user.name -> lenovo
     * ResourcePropertySource[class path resource [META-INF/configuration-for-properties-other.properties]] : user.name -> 拿铁
     *
     * <h2>结果说明：</h2>
     * 在 {@link #propertySourcePriorityOriginal}的基础上，
     * 可以通过在调用 {@code applicationContext.refresh()}之前添加自定义 {@link org.springframework.core.env.PropertySource}
     * 的方式来修改相关属性的优先级
     */
    @Test
    public void propertySourcePriorityAddFirst() {
        propertySourcePriority((applicationContext) -> {
            // 添加自定义数据源，该操作需要在 {@code refresh()} 调用前完成
            applicationContext.getEnvironment()
                    .getPropertySources()
                    .addFirst(new MapPropertySource("my-property-source",
                            Collections.singletonMap("user.name", "小贝")));
        });
    }

    /**
     * 演示 {@link PropertySource}的优先级
     * @param addFirstPropertySource 在 {@link AbstractApplicationContext}中的 {@link PropertySource}优先级
     */
    private void propertySourcePriority(Consumer<AbstractApplicationContext> addFirstPropertySource) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 处理添加 {@code PropertySource} 的自定义逻辑
        addFirstPropertySource.accept(applicationContext);
        // 注册当前类
        applicationContext.register(PropertySourceDemo.class);
        // 启动spring应用上下文
        applicationContext.refresh();
        Map<String, ResourceDomain> beansMap = applicationContext.getBeansOfType(ResourceDomain.class);
        beansMap.forEach((beanName, bean) -> {
            System.out.println(beanName + " -> " + bean);
        });
        MutablePropertySources propertySources = applicationContext.getEnvironment().getPropertySources();
        propertySources.forEach(propertySource -> {
            if (propertySource instanceof MapPropertySource) {
                MapPropertySource mapPropertySource = (MapPropertySource) propertySource;
                Arrays.stream(mapPropertySource.getPropertyNames())
                        // 打印出当前找到的所有名称为 "user.name"的属性信息
                        .filter("user.name"::equals)
                        .forEach(pro -> System.out.println(mapPropertySource.getClass().getSimpleName() + "[" + mapPropertySource.getName() + "] : " + pro + " -> " + mapPropertySource.getProperty(pro)));
            }
        });
        // 关闭spring应用上下文
        applicationContext.close();
    }
}
