package com.microservice.fastdfs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConditionalOnProperty(prefix = "swagger", value = { "enable" }, havingValue = "true")
@EnableSwagger2
public class SwaggerConfig {

	@Value("${fdfs.tracker-list[0]}")
	private String trackerIp;
	private final static String TITLE = "文件服务接口文档";
	private final static String DESCRIPTION = "Api";
	private final static String VERSION = "2019";
	private final static String BASE_PACKAGE = "com.microservice.fastdfs.api";
	private final static String CONTACT = "jnyc";

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).forCodeGeneration(false).select()
				.apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
				.paths(Predicates.not(PathSelectors.regex("/error.*"))).build();
	}

	private ApiInfo apiInfo() {
		StringBuffer sbStr = new StringBuffer();
		this.trackerIp = trackerIp.replace(":22122", "");
		sbStr.append("\n rootPath=http://" + trackerIp);
		sbStr.append("\n filePath=/group1/M00/00/00/wKgB51x176OAFgj4AABlPOgYq40058.jpg ");
		sbStr.append("\n 访问地址：http://" + trackerIp + "/group1/M00/00/00/wKgB51x176OAFgj4AABlPOgYq40058.jpg ");
		return new ApiInfoBuilder().title(TITLE).description(DESCRIPTION + sbStr.toString()).version(VERSION)
				.termsOfServiceUrl("/").contact(new Contact(CONTACT, "", "")).license("2019-2029").build();
	}
}
