package com.xiaobei.spring.demo.configuration.metadata.yaml;

import com.xiaobei.spring.demo.configuration.metadata.domain.EnumType;
import com.xiaobei.spring.demo.configuration.metadata.domain.ResourceDomain;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 10:37
 */
@PropertySource(name = "annotation",
        value = "classpath:/META-INF/application.yaml", factory = YamlPropertySourceFactory.class)
public class AnnotationBasedYamlPropertySourceDemo {

    @Bean
    public ResourceDomain domain(@Value("${user.name}") String name,
                                 @Value("${domain.id}") Long id,
                                 @Value("${domain.enumType}") EnumType enumType) {
        ResourceDomain domain = new ResourceDomain();
        domain.setName(name);
        domain.setId(id);
        domain.setEnumType(enumType);
        return domain;
    }

    /**
     * <h2>运行结果：</h2>
     * 当前 domain，信息为 ResourceDomain{byteType=0, basicType=null, name='lenovo', nickname='null', id=121, enumType=LUOYANG, resourceLocation=null, clazz=null}
     *
     * <h2>结果说明：</h2>
     * 从运行结果上可以看出yaml文件已经正确进行了读取
     *
     * <h2>相关资料</h2>
     * @see PropertySource
     * @see PropertySourceFactory
     * @see YamlPropertiesFactoryBean
     * @see PropertiesPropertySource
     */
    @Test
    public void annotationBaseYamlProperty() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationBasedYamlPropertySourceDemo.class);
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
