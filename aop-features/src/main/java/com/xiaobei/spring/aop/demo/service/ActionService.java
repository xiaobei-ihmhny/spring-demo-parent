package com.xiaobei.spring.aop.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动作服务
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-17 20:46:46
 */
public class ActionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionService.class);

    public void lightUp() {
        LOGGER.info("light up...");
    }

    public void lightening() {
        LOGGER.info("lightening...");
    }

    public void shutdown() {
        LOGGER.info("shutdown...");
    }

    public void echo() {
        LOGGER.info("echo...");
    }

}
