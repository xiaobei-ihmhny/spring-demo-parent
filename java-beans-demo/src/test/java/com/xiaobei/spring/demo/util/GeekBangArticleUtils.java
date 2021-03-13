package com.xiaobei.spring.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 极客时间相关课程计划工具类
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-03-13 12:08:08
 */
public class GeekBangArticleUtils {

    /**
     * 输出极客文件中相关的音频课程安排信息
     *
     * @param filePath                 包含课程信息（json格式）的文件路径
     * @param dataTimeFormatterPattern 输出信息中日期的格式
     * @param startLearningDate        计划课程开始学习时间
     * @param oneDayLearningSeconds    一天学习的时长
     */
    public static void printAudioLessonPlanForMarkDown(String filePath,
                                                        String dataTimeFormatterPattern,
                                                        LocalDate startLearningDate,
                                                        int oneDayLearningSeconds) {
        printLessonPlanForMarkDown(filePath,
                dataTimeFormatterPattern,
                startLearningDate,
                oneDayLearningSeconds,
                "audio_time");
    }

    /**
     * 输出极客文件中相关的视频课程安排信息
     *
     * @param filePath                 包含课程信息（json格式）的文件路径
     * @param dataTimeFormatterPattern 输出信息中日期的格式
     * @param startLearningDate        计划课程开始学习时间
     * @param oneDayLearningSeconds    一天学习的时长
     */
    public static void printVideoLessonPlanForMarkDown(String filePath,
                                                        String dataTimeFormatterPattern,
                                                        LocalDate startLearningDate,
                                                        int oneDayLearningSeconds) {
        printLessonPlanForMarkDown(filePath,
                dataTimeFormatterPattern,
                startLearningDate,
                oneDayLearningSeconds,
                "video_time");
    }

    /**
     * 输出极客文件中相关的课程安排信息
     *
     * @param filePath                 包含课程信息（json格式）的文件路径
     * @param dataTimeFormatterPattern 输出信息中日期的格式
     * @param startLearningDate        计划课程开始学习时间
     * @param oneDayLearningSeconds    一天学习的时长
     * @param timeLengthFieldName      时间长度所属字段名称
     */
    public static void printLessonPlanForMarkDown(String filePath,
                                                  String dataTimeFormatterPattern,
                                                  LocalDate startLearningDate,
                                                  int oneDayLearningSeconds,
                                                  String timeLengthFieldName) {
        List<String> jsonStr = null;
        try {
            jsonStr = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("文件读取失败，路径为：%s", filePath);
            return;
        }
        JSONObject jsonObject = JSON.parseObject(jsonStr.get(0));
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray list = data.getJSONArray("list");
        List<JSONObject> lessonList = list.toJavaList(JSONObject.class);
        AtomicInteger countSeconds = new AtomicInteger();
        AtomicInteger sumSeconds = new AtomicInteger();
        LocalDate today = startLearningDate;
        for (JSONObject object : lessonList) {
            String title = object.getString("article_title").replace(" |", "");
            Boolean hasAudio = object.getBoolean("include_audio");
            if("audio_time".equals(timeLengthFieldName) && !hasAudio) {
                continue;
            }
            String time = object.getString(timeLengthFieldName);
            LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
            // 当前课程需要的时间
            int localLessonCostSeconds = localTime.toSecondOfDay();
            countSeconds.addAndGet(localLessonCostSeconds);
            sumSeconds.addAndGet(localLessonCostSeconds);
            int currentCountSeconds = countSeconds.get();
            if (currentCountSeconds >= oneDayLearningSeconds) {
                countSeconds.set(0);
                today = today.plusDays(1L);
            }
            String result = title + " | " + time + " | " + today.format(DateTimeFormatter.ofPattern(dataTimeFormatterPattern));
            System.out.println(result);
        }
        System.out.printf("总时长：%d 分钟", sumSeconds.get() / 60);
        System.out.printf("总时长：%d 小时", sumSeconds.get() / 60 / 60);
    }

}
