package com.xiaobei.spring.demo.configuration.metadata;

import com.xiaobei.spring.demo.configuration.metadata.domain.ResourceDomain;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.lang.annotation.Repeatable;
import java.util.Map;

/**
 * 其中 {@link ImportResource} 替换XML元素<import>
 * 其中 {@link Import} 会将相关类配置成 Configuration Class
 * {@link PropertySource} 利用了java8中的 “可重复注解” {@link Repeatable}
 *
 *
 * @see ImportResource
 * @see Import
 * @see PropertySource
 * @see Repeatable
 * @see PropertySources
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/15 21:39
 */
@ImportResource("classpath:/META-INF/configuration-for-xml.xml")// 导入xml文件
@Import(ResourceDomain.class)
@PropertySource("classpath:/META-INF/configuration-for-properties.properties")
@PropertySource("classpath:/META-INF/configuration-for-properties-other.properties")
public class AnnotatedSpringIoCContainerMetadataConfigurationDemo {

    @Value("${resourceDomain.name}")
    private String name;

    @Bean
    public ResourceDomain configResourceDomain(@Value("${nickname}") String nickname) {
        ResourceDomain domain = new ResourceDomain();
        domain.setName(name);
        domain.setNickname(nickname);
        return domain;
    }

    /**
     * <h2>运行结果：</h2>
     * 当前 com.xiaobei.spring.demo.configuration.metadata.domain.ResourceDomain，信息为 ResourceDomain{byteType=0, basicType=null, name='null', nickname='null', id=null, enumType=null, resourceLocation=null, clazz=null}
     * 当前 configResourceDomain，信息为 ResourceDomain{byteType=0, basicType=null, name='基于Properties资源装载Spring Bean配置元信息-PropertiesBeanDefinition', nickname='117 基于Java注解装载Spring IoC容器配置元信息', id=null, enumType=null, resourceLocation=null, clazz=null}
     * 当前 resourceDomain，信息为 ResourceDomain{byteType=1, basicType=true, name='基于Properties资源装载Spring Bean配置元信息-PropertiesBeanDefinition', nickname='null', id=111, enumType=RUYANG, resourceLocation=class path resource [META-INF/configuration-for-xml.xml], clazz=class com.xiaobei.spring.demo.configuration.metadata.domain.ResourceDomain}
     *
     * <h2>说明：</h2>
     * 结果中第一个来源为 {@code @Import(ResourceDomain.class)}
     * 第二个来源为 {@code @Bean}
     * 第三个来源为 xml配置文件：/META-INF/configuration-for-xml.xml
     */
    @Test
    public void iocContainerMetadata() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotatedSpringIoCContainerMetadataConfigurationDemo.class);
        // 启动spring应用上下文
        applicationContext.refresh();
        Map<String, ResourceDomain> map = applicationContext.getBeansOfType(ResourceDomain.class);
        // 格式化输出map
        map.forEach((beanName, bean) -> {
            System.out.printf("当前 %s，信息为 %s\n", beanName, bean);
        });
        // 关闭spring应用上下文
        applicationContext.close();
    }
}
