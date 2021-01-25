package com.xiaobei.springmvc.demo.controller;

import com.xiaobei.springmvc.demo.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 springmvc 配置
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-22 18:43:43
 */
@RestController
public class DemoController {

    private ApplicationContext applicationContext;

    @GetMapping("/index")
    public String hello() {
        return "Hello SpringMVC!";
    }

    @GetMapping("/userBody")
    public User saveUserByBody(@RequestBody User user) {
        // TODO 具体的保存操作
        return user;
    }

    @PostMapping("/user")
    public User saveUser(User user) {
        // TODO 具体的保存操作
        return user;
    }

    @GetMapping("/int")
    public String parseInt(int id) {
        return "parseInt" + id;
    }
}
