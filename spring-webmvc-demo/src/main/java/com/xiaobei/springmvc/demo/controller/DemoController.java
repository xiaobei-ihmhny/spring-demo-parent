package com.xiaobei.springmvc.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 springmvc 配置
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-22 18:43:43
 */
@RestController
public class DemoController {

    @GetMapping("/index")
    public String hello() {
        return "Hello SpringMVC!";
    }

    @GetMapping("/user")
    public String user(String username) {
        return username;
    }
}
