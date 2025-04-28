package com.ssafy.mvc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 서버에서 이미지를 저장할 실제 경로 (back의 root 폴더 기준)
//        String uploadDir = System.getProperty("user.dir") + "/uploads/profile_images/";
//
//        // 클라이언트가 접근할 URL 경로
//        String urlPath = "/profile_images/**";
//
//        // URL과 실제 파일 경로를 매핑
//        registry.addResourceHandler(urlPath)
//                .addResourceLocations("file:" + uploadDir);
//    
//    }
	
    @Value("${file.upload-dir}")
    private String uploadDir;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    // 로컬 경로에 저장된 파일을 /uploads/profile_images/ URL로 매핑
	    registry.addResourceHandler("/uploads/**")
	            .addResourceLocations("file:///" + uploadDir);
	    
	}
	
	@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("*");
			
		}
}