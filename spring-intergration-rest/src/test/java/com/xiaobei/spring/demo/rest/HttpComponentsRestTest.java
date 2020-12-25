package com.xiaobei.spring.demo.rest;

import com.xiaobei.spring.demo.rest.domain.User;
import com.xiaobei.spring.demo.rest.domain.Views;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * 示例 {@link RestTemplate} 使用 {@code spring-intergration}
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/25 11:23
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringRestTemplateConfig.class)
@ActiveProfiles(value = "httpComponents")
//@ActiveProfiles(value = "okHttp3") // 使用 OkHttp 客户端实现
//@ActiveProfiles(value = "netty4") // 使用 netty4 客户端实现
public class HttpComponentsRestTest {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * <pre> {@code
     *     @RequestMapping
     *     @ResponseBody
     *     public String helloWorld() {
     *         return "hello world";
     *     }
     * </pre>
     */
    @Test
    public void getForObjectUseString() {
        String url = "http://localhost:8080";
        RestTemplate restTemplate = new RestTemplate(
                new HttpComponentsClientHttpRequestFactory());
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
    }

    /**
     * <pre> {@code
     *     @RequestMapping(value = "/path/{param1}/{param2}",method = RequestMethod.GET)
     *     @ResponseBody
     *     public String mapParams(@PathVariable("param1") String param1,
     *                             @PathVariable("param2") String param2,
     *                             HttpServletRequest request) {
     *         return request.getRequestURL().toString();
     *     }
     * </pre>
     */
    @Test
    public void getForObjectUseMap() {
        String url = "http://localhost:8080/path/{param1}/{param2}";
        Map<String, String> vars = new HashMap<>(2);
        vars.put("param1", "xiaohui");
        vars.put("param2", "tietie");
        String result = restTemplate.getForObject(url, String.class, vars);
        System.out.println(result);
    }

    /**
     * TODO 自定义配置 {@link UriTemplateHandler}
     */
    @Test
    public void uriTemplateHandler() {
        UriTemplateHandler handler = new DefaultUriBuilderFactory();
        restTemplate.setUriTemplateHandler(handler);
    }

    /**
     * 示例请求和响应中带有请求头、响应头信息
     * <pre> {@code
     *     @ResponseBody
     *     @RequestMapping(value = "/header",method = RequestMethod.GET, headers = {"password=xiaohui"})
     *     public String customHeaders(String name, HttpServletResponse response) {
     *         response.addHeader("answer", "beibei");
     *         return "this is header request, your header consider the current header named password and value xiaohui: " + name;
     *     }
     * </pre>
     */
    @Test
    public void customHeaders() {
        String uriTemplate = "http://localhost:8080/header";
        URI uri = UriComponentsBuilder.fromUriString(uriTemplate)
                .queryParam("name", "tietie").build().toUri();
        // 构建请求参数
        RequestEntity<Void> requestEntity = RequestEntity.get(uri)
                .header("password", "xiaohui")
                .build();
        // 使用 {@code exchange} 发起请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        String responseHeader = responseEntity.getHeaders().getFirst("answer");
        System.out.println(responseHeader);
        String body = responseEntity.getBody();
        System.out.println(body);
    }

    /**
     * TODO {@link RestTemplate#postForLocation(URI, Object) postForLocation} 的作用是什么？
     */
    @Test
    public void postForLocation() {
        String url = "http://localhost:8080/user/save";
        User user = new User().setId(1L).setUsername("xiaohui").setAge(17).setSex(true);
        URI saveURI = restTemplate.postForLocation(url, user);
        // TODO 这个返回值有什么用呢？
    }

    /**
     * TODO 没有 json jar包时报异常的原因？？？2020年12月25日16:52:52
     * 客户端和服务器都需要有 json 相关的包比如：jackson，
     * 如果没有，则会报异常：{@code org.springframework.web.client.RestClientException: No HttpMessageConverter for com.xiaobei.spring.demo.rest.domain.User}
     * <pre> {@code
     *         <dependency>
     *             <groupId>com.fasterxml.jackson.core</groupId>
     *             <artifactId>jackson-annotations</artifactId>
     *             <version>${jackson.version}</version>
     *         </dependency>
     *         <dependency>
     *             <groupId>com.fasterxml.jackson.core</groupId>
     *             <artifactId>jackson-core</artifactId>
     *             <version>${jackson.version}</version>
     *         </dependency>
     *         <dependency>
     *             <groupId>com.fasterxml.jackson.core</groupId>
     *             <artifactId>jackson-databind</artifactId>
     *             <version>${jackson.version}</version>
     *         </dependency>
     *
     *  //===================================================
     *
     *     private final Map<Long, User> userMap = new HashMap<>(16);
     *
     *     @ResponseBody
     *     @RequestMapping(value = "/user/save",method = RequestMethod.POST)
     *     public String postForObject(@RequestBody User user) {
     *         LOGGER.log(Level.INFO, "用户信息为：" + user);
     *         userMap.put(user.getId(), user);
     *         return "user信息保存成功";
     *     }
     * </pre>
     */
    @Test
    public void postForObject() {
        String url = "http://localhost:8080/user/save";
        User user = new User().setId(1L).setUsername("xiaohui").setAge(17).setSex(true);
        String result = restTemplate.postForObject(url, user, String.class);
        System.out.println(result);
    }

    /**
     * <pre>{@code
     *     @ResponseBody
     *     @RequestMapping(value = "/user/{userId}",method = RequestMethod.GET)
     *     public User getForObject(@PathVariable("userId") Long userId) {
     *         LOGGER.log(Level.INFO, "用户信息为：" + userId);
     *         return userMap.get(userId);
     *     }
     * }</pre>
     */
    @Test
    public void getForObject() {
        String url = "http://localhost:8080/user/{id}";
        User user = restTemplate.getForObject(url, User.class, 1);
        System.out.println(user);
    }

    /**
     * 部分字段序列化
     * 其中视图相关的说明参见：<a href="https://www.baeldung.com/jackson-json-view-annotation">JacksonJsonView</a>
     * <pre>{@code
     * @ResponseBody
     * @RequestMapping(value = "/user/save",method = RequestMethod.POST)
     * public String postForObject(@RequestBody User user) {
     *     LOGGER.log(Level.INFO, "用户信息为：" + user);
     *     userMap.put(user.getId(), user);
     *     return "user信息保存成功";
     * }
     *
     * }</pre>
     *
     * @throws URISyntaxException
     */
    @Test
    public void jacksonJSONView() throws URISyntaxException {
        MappingJacksonValue value = new MappingJacksonValue(
                new User().setId(2L).setUsername("tietie").setAge(1).setSex(false));
        // 设置视图
        value.setSerializationView(Views.Public.class);
        RequestEntity<MappingJacksonValue> requestEntity =
                RequestEntity.post(new URI("http://localhost:8080/user/save")).body(value);
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        String body = response.getBody();
        System.out.println(body);
    }

    /**
     * TODO 完善 2020年12月25日17:26:54
     * TODO https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#rest-template-multipart
     */
    @Test
    public void multipart() {

    }

    /**
     * TODO {@link org.springframework.web.client.AsyncRestTemplate}  已被标记为“不推荐使用”，请使用 {@code WebClient}
     */
    @Test
    public void asyncRestTemplate() {

    }

}
