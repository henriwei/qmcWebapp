package com.myway.questmultichoice.service.jpa;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class DefaultLocaleMessageSource {
	@Autowired
	MessageSource messageSource;
	
	public String getMessage(String key, Object[] args) {
		return messageSource.getMessage(key, args, Locale.ENGLISH);
	}
}
