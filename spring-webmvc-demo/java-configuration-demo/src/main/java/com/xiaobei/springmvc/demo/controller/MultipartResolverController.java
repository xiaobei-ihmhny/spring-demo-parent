package com.xiaobei.springmvc.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * {@link org.springframework.web.multipart.MultipartResolver} demo
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-03-19 06:38:38
 */
@Controller
@RequestMapping("multipart")
public class MultipartResolverController {

    @PostMapping("upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if(!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String filePath = "D:\\" + file.getOriginalFilename();
            File localFile = new File(filePath);
            file.transferTo(localFile);
            System.out.println("文件保存本地成功...");
        }
        return "请求成功";
    }

}
