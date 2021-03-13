package com.xiaobei.spring.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaobei.spring.demo.util.GeekBangArticleUtils;
import org.junit.jupiter.api.Test;

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
public class GeekBangArticlesPrintTest {

    /**
     * <a href="https://time.geekbang.org/course/intro/100042601">小马哥讲Spring核心编程思想</a>
     */
    @Test
    public void printSpringArticles() {
        String springFilePath = "E:\\git-project-workspace\\spring-demo-parent\\java-beans-demo\\src\\test\\resources\\spring.json";
        GeekBangArticleUtils.printVideoLessonPlanForMarkDown(springFilePath,
                "YYYY-MM-dd",
                LocalDate.now(),
                7200);
    }

    /**
     * <a href="https://time.geekbang.org/course/intro/100066301">小马哥讲Spring AOP编程思想</a>
     * https://time.geekbang.org/serv/v1/column/articles
     */
    @Test
    public void printSpringAopArticles() {
        String springAopFilePath = "E:\\git-project-workspace\\spring-demo-parent\\java-beans-demo\\src\\test\\resources\\spring-aop.json";
        GeekBangArticleUtils.printVideoLessonPlanForMarkDown(springAopFilePath,
                "YYYY-MM-dd",
                LocalDate.now(),
                7200);
    }

    /**
     * <a href="https://time.geekbang.org/column/intro/100020801">MySQL实战45讲</a>
     */
    @Test
    public void printMysql() {
        String mysql45FilePath = "E:\\git-project-workspace\\spring-demo-parent\\java-beans-demo\\src\\test\\resources\\mysql-45.json";
        GeekBangArticleUtils.printAudioLessonPlanForMarkDown(mysql45FilePath,
                "YYYY-MM-dd",
                LocalDate.now(),
                7200);
    }

    public static void main(String[] args) throws IOException {
        article();
    }

    /**
     * 课程相关
     * @throws IOException
     */
    private static void article() throws IOException {
        List<String> jsonStr = Files.readAllLines(
                Paths.get("E:\\git-project-workspace\\spring-demo-parent\\java-beans-demo\\src\\test\\resources\\spring.json"));
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
