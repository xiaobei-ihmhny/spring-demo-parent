package com.xiaobei.springmvc.demo;

import com.xiaobei.springmvc.demo.config.WebMvcConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * springmvc核心配置
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-22 18:40:40
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.xiaobei.springmvc.demo")
@Import(value = WebMvcConfig.class)
public class SpringWebMvcConfiguration {
}
