package com.chenyi.auth.springbootshiro.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component("springUtils")
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}

	public  Object getBean(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return applicationContext.getBean(name);
	}
}
