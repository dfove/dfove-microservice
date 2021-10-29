package com.microservice.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConditionalOnProperty(prefix = "swagger", value = { "enable" }, havingValue = "true")
@EnableSwagger2
public class SwaggerConfig {

	private final static String TITLE = "管理员信息服务接口文档";
	private final static String DESCRIPTION = "Api";
	private final static String VERSION = "2019";
	private final static String BASE_PACKAGE = "com.microservice.admin.api";
	private final static String CONTACT = "jnyc";

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).forCodeGeneration(false)
				// .ignoredParameterTypes(OperateResult.class)
				.select().apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
				.paths(Predicates.not(PathSelectors.regex("/error.*")))
				// .paths(PathSelectors.any())
				.build().globalOperationParameters(userScope());
		// .securitySchemes(securitySchemes()).securityContexts(securityContexts());
	}

	private ApiInfo apiInfo() {
		/*
		 * return new ApiInfo(title, description, version, "/", new Contact("dtph",
		 * "http://www.dtpuhui.com", "dtph@dtpuhui.com"), "2019-2029", "/", null);
		 */

		return new ApiInfoBuilder().title(TITLE).description(DESCRIPTION).version(VERSION).termsOfServiceUrl("/")
				.contact(new Contact(CONTACT, "", "")).license("2019-2029").build();

	}

	private List<Parameter> userScope() {

		List<Parameter> paras = new ArrayList<Parameter>();
		paras.add(
				new ParameterBuilder().name("UserScope").description("需要登录才能执行的操作需要注入用户身份，通过模拟登录用户的scope获取并设置，真实环境不需要)")
						.modelRef(new ModelRef("string")).parameterType("header").required(false).build());
		return paras;
	}
}
