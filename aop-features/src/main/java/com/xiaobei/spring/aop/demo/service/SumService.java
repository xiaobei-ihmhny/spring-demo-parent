package com.xiaobei.spring.aop.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 求和
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 21:30:30
 */
public class SumService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SumService.class);

    private int original;

    public SumService(int original) {
        this.original = original;
    }

    public int getOriginal() {
        return original;
    }

    public int addAndGet(int i) {
        return original = original + i;
    }

    public int incrementAndGet() {
        original++;
        LOGGER.info("当前变量的值为：{}", original);
        return original;
    }
}
