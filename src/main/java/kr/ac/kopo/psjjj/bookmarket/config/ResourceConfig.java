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
        String location = fileDir.endsWith("/") ? fileDir : fileDir + "/";

        registry.addResourceHandler("/imgs/**")
                // 1순위: 프로젝트 내 static/imgs/ 폴더 (book.png 등 기본 이미지)
                // 2순위: 외부 파일 업로드 경로 (사용자가 업로드한 파일)
                .addResourceLocations("classpath:/static/imgs/", "file:///" + location)
                .setCachePeriod(3600);
    }
}