package com.xiaobei.springmvc.demo.multipart;

import com.xiaobei.springmvc.demo.SpringWebMvcConfiguration;
import com.xiaobei.springmvc.demo.controller.MultipartResolverController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 测试 {@link MultipartResolverController}
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-03-19 22:33:33
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringWebMvcConfiguration.class)
@WebAppConfiguration
public class MultipartResolverControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * 测试使用
     * @throws Exception
     */
    @Test
    public void upload() throws Exception {
        String filename = "image/demo.jpg";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        MockMultipartFile mFile = new MockMultipartFile("file",
                "demo.jpg",
                "multipart/form-data",
                inputStream);
        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/multipart/upload").file(mFile);
        String result = mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        System.out.println(result);
    }


}
