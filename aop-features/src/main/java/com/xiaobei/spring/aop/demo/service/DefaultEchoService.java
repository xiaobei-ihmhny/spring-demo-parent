package com.xiaobei.spring.aop.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link EchoService} 的默认实现
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-14 22:40:40
 */
public class DefaultEchoService implements EchoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEchoService.class);

    @Override
    public String echo(String msg) {
        String result = "Hello " + msg;
        LOGGER.info(result);
        return result;
    }
}
