package kr.ac.kopo.psjjj.bookmarket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Value("${file.uploadDir}")
    private String fileDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 경로 끝에 슬래시(/) 보장 처리
        String location = fileDir.endsWith("/") ? fileDir : fileDir + "/";

        registry.addResourceHandler("/imgs/**")
                .addResourceLocations("file:///" + location) // 슬래시 3개(file:///) 필수
                .setCachePeriod(3600);
    }
}