package kr.inhatc.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

//	@Override
//	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/images-member/**/**").addResourceLocations("file:images-member/**/");
//	}
	// 메모리에 올려야 오류안나지 Bean
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		//처리 할 수 있는 파일 크기 지정 등 가능
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024);	// 5MB
		return commonsMultipartResolver;
	}


	
	
		
}
