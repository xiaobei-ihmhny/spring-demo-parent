package com.xiaobei.spring.aop.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 无实现任何接口的 service
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-14 23:14:14
 */
public class CustomEchoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomEchoService.class);

    public String echo(String msg) {
        String result = "[CustomEchoService] Hello " + msg;
        LOGGER.info(result);
        return result;
    }
}
