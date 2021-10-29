package com.microservice.fastdfs;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class BeanListener implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		LoggerFactory.getLogger(getClass()).info("setApplicationContext ==============================================================");
		LoggerFactory.getLogger(getClass()).info("applicationContext " + applicationContext);
		LoggerFactory.getLogger(getClass()).info("setApplicationContext ==============================================================");
		this.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String beanName) {
		LoggerFactory.getLogger(BeanListener.class).info("getBean ==============================================================");
		LoggerFactory.getLogger(BeanListener.class).info("beanName " + beanName);
		LoggerFactory.getLogger(BeanListener.class).info("getBean ==============================================================");
		return applicationContext.getBean(beanName);
	}

	public static Object getBean(Class<Object> c) {
		return applicationContext.getBean(c);
	}

	public static String byte2hex(byte[] buffer) {
		String h = "0x";

		for (byte aBuffer : buffer) {
			String temp = Integer.toHexString(aBuffer & 0xFF);
			if (temp.length() == 1) {
				temp = "0" + temp;
			}
			h = h + " " + temp;
		}

		return h;
	}

}
