package com.xiaobei.spring.demo.ioc.bean.scope.controller;

import com.xiaobei.spring.demo.ioc.bean.scope.domain.ScopeDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/8 22:44
 */
@RestController
public class IndexController {

    @Autowired
    private ScopeDomain scopeDomain;

    /**
     * 每次请求都会重新生成一个bean
     *
     * 响应结果如下：
     * ScopeDomain{id=1596900679516, name='xiaobei', beanName='scopedTarget.scopeDomain'}
     * ScopeDomain{id=1596900731364, name='xiaobei', beanName='scopedTarget.scopeDomain'}
     * 日志信息：
     * 当前bean [scopedTarget.scopeDomain] 正在进行初始化...
     * 当前bean [scopedTarget.scopeDomain] 正在进行销毁...
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return scopeDomain.toString();
    }
}
