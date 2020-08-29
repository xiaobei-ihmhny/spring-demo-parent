package com.xiaobei.spring.demo.event;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * {@link TestPropertySource}
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/29 9:34
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestPropertySourceTest.class)
@TestPropertySource(
        properties = {"user.name=那那"}, // PropertySource[Inlined Test Properties]中属性 user.name = 那那
        locations = "classpath:/META-INF/propertysource.properties") // PropertySource[class path resource [META-INF/propertysource.properties]]中属性 user.name = nana
public class TestPropertySourceTest {

    @Value("${user.name}")
    private String username;

    @Autowired
    private ConfigurableEnvironment environment;

    /**
     * <h2>运行结果：</h2>
     * PropertySource[Inlined Test Properties]中属性 user.name = 那那
     * PropertySource[class path resource [META-INF/propertysource.properties]]中属性 user.name = nana
     * PropertySource[systemProperties]中属性 user.name = lenovo
     * PropertySource[systemEnvironment]中属性 user.name = null
     */
    @Test
    public void test() {
        Assert.assertEquals(username, "那那");

        environment.getPropertySources().forEach(propertySource ->
                System.out.printf("PropertySource[%s]中属性 user.name = %s\n", propertySource.getName(), propertySource.getProperty("user.name")));

    }
}
