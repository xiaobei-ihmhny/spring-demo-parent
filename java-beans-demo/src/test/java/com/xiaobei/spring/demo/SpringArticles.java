package com.xiaobei.spring.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 获取spring课程信息
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/7/28 20:32
 */
public class SpringArticles {

    public static void main(String[] args) throws IOException {
        article();
//        orderPriceAll();
    }

    private static void orderPriceAll() throws IOException {
        byte[] jsonStr = Files.readAllBytes(
                Paths.get("D:\\orders.json"));
        JSONObject jsonObject = JSON.parseObject(new String(jsonStr));
        JSONArray result = jsonObject.getJSONArray("result");
        List<JSONObject> orderList = result.toJavaList(JSONObject.class);
        final AtomicReference<BigDecimal> allPrice = new AtomicReference<>();
        allPrice.set(BigDecimal.ZERO);
        orderList.forEach(jsonObject1 -> {
            BigDecimal goodsPrice = jsonObject1.getBigDecimal("goodsPrice");
            BigDecimal current = allPrice.get();
            allPrice.set(current.add(goodsPrice));
        });
        System.out.println(allPrice.get());

    }

    /**
     * 课程相关
     * @throws IOException
     */
    private static void article() throws IOException {
        List<String> jsonStr = Files.readAllLines(
                Paths.get("E:\\project\\spring-demo-parent\\java-beans-demo\\src\\test\\resources\\spring.json"));
        JSONObject jsonObject = JSON.parseObject(jsonStr.get(0));
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray list = data.getJSONArray("list");
        List<JSONObject> lessonList = list.toJavaList(JSONObject.class);
        AtomicInteger countSeconds = new AtomicInteger();
        LocalDate today = LocalDate.of(2020, 9, 2);
        for (JSONObject object : lessonList) {
            String title = object.getString("article_title").replace(" |", "");
            String time = object.getString("video_time");
            LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
            // 当前课程需要的时间
            int localLessonCostSeconds = localTime.toSecondOfDay();
            countSeconds.addAndGet(localLessonCostSeconds);
            int currentCountSeconds = countSeconds.get();
            if (currentCountSeconds >= 7200) {
//                System.out.println("=========" + currentCountSeconds);
                countSeconds.set(0);
                today = today.plusDays(1L);
            }
            String result = title + " | " + time + " | " + today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            System.out.println(result);
        }
        System.out.println(countSeconds.get() / 60);
        System.out.println(countSeconds.get() / 60 / 60);
//        System.out.println(jsonObject);
    }
}
