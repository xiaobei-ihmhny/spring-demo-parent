package com.xiaobei.springmvc.demo.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 序列化与反序列化示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-26 05:41:41
 */
@DisplayName("序列化与反序列化示例")
public class JsonTest {

    @Test
    void jacksonBindSample() throws JsonProcessingException {
        // 1. 创建一个单例的 objectMapper 供所有转换使用
        ObjectMapper objectMapper = new ObjectMapper();
        // json -> pojo
        Domain domain = objectMapper.readValue("{\"id\":\"1\", \"name\":\"xiaobei\"}", Domain.class);
        System.out.println("json -> pojo：" + domain);
        // pojo -> json
        String json = objectMapper.writeValueAsString(new Domain().setId(11).setName("natie"));
        System.out.println("pojo -> json：" + json);
    }

    @Test
    void jacksonBindWithAnnotation() throws JsonProcessingException {
        // 1. 创建一个单例的 objectMapper 供所有转换使用
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // json -> pojo
        Domain domain = objectMapper.readValue("{\"id\":\"1\", \"Name\":\"xiaobei\", \"start_date\":\"2021-1-26 16:17:55\"}", Domain.class);
        System.out.println("json -> pojo：" + domain);
        // pojo -> json
        String json = objectMapper.writeValueAsString(new Domain().setId(11).setName("natie"));
        System.out.println("pojo -> json：" + json);

    }

    static class Domain {

        private Integer id;

        private String name;

//        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonProperty("start_date")
        private Date startDate;

        public Integer getId() {
            return id;
        }

        public Domain setId(Integer id) {
            this.id = id;
            return this;
        }

        @JsonProperty(value = "name")
        public String getName() {
            return name;
        }

        @JsonProperty(value = "Name")
        public Domain setName(String name) {
            this.name = name;
            return this;
        }

        public Date getStartDate() {
            return startDate;
        }

        public Domain setStartDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        @Override
        public String toString() {
            return "Domain{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", startDate=" + startDate +
                    '}';
        }
    }

}
