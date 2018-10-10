package com.hrh.kmanual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

/**
 *
 * @author huangrenhao
 * @date 2018/8/9
 */
@SpringBootApplication
public class KnowledgeManualApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowledgeManualApplication.class, args);
	}
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//文件最大
		//KB,MB
		factory.setMaxFileSize("150MB");
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("150MB");
		return factory.createMultipartConfig();
	}
}
