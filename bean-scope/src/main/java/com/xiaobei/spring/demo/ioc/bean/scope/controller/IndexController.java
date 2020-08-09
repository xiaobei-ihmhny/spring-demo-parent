package com.xiaobei.spring.demo.ioc.bean.scope.controller;

import com.xiaobei.spring.demo.ioc.bean.scope.domain.ScopeDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/8 22:44
 */
@RestController
public class IndexController {

    @Autowired
    private ScopeDomain requestScopeDomain;

    @Autowired
    private ScopeDomain sessionScopeDomain;

    @Autowired
    private ScopeDomain applicationScopeDomain;

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
    @GetMapping("/request")
    public String requestScopeDomain() {
        return requestScopeDomain.toString();
    }


    /**
     * 运行结果表明：
     * {@link WebApplicationContext#SCOPE_SESSION}作用域的bean，每一个cookie时会对应一个bean
     *
     * 运行结果：
     *
     * ScopeDomain{id=1596927941140, name='xiaobei', beanName='scopedTarget.sessionScopeDomain'}
     *
     * 日志信息：
     *
     * 当前bean [scopedTarget.sessionScopeDomain] 正在进行初始化...
     * @return
     */
    @GetMapping("/session")
    public String sessionScopeDomain() {
        return sessionScopeDomain.toString();
    }

    /**
     *
     * 无论多少个终端访问，结果都是一样的
     *
     * 运行结果：
     * 当前bean [scopedTarget.applicationScopeDomain] 正在进行初始化...
     *
     * 日志信息：
     * ScopeDomain{id=1596933712223, name='application', beanName='scopedTarget.applicationScopeDomain'}
     * @return
     */
    @GetMapping("/application")
    public String applicationScopeDomain() {
        return applicationScopeDomain.toString();
    }
}
