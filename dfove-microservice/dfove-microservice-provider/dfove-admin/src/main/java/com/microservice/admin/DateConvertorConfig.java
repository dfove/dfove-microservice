package com.microservice.admin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.microservice.core.DateConvertor;

@Configuration
public class DateConvertorConfig {

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	@PostConstruct
	public void initEditableAvlidation() {

		ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
				.getWebBindingInitializer();
		if (initializer.getConversionService() != null) {
			GenericConversionService genericConversionService = (GenericConversionService) initializer
					.getConversionService();

			genericConversionService.addConverter(new DateConvertor());

		}
	}
}