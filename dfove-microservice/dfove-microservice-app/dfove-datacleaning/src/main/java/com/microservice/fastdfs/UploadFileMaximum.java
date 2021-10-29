package com.microservice.fastdfs;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: UploadFileMaximum
 * @Description: 配置文件上传大小size
 * @author sunwuzhao
 * @date 2019-02-28
 * @version V1.0
 */
@Configuration
public class UploadFileMaximum {

	/**
	 * @Desc 文件上传配置size
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 文件最大
		factory.setMaxFileSize("1024MB"); // KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("10240MB");
		return factory.createMultipartConfig();
	}
}
