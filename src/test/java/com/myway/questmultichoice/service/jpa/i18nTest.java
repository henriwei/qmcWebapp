package com.myway.questmultichoice.service.jpa;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Locale;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * This test uses ServiceTestConfig like other tests, the MessageSource bean configuration 
 * may require update to reflect the web application configuration. 
 * @author Zhe
 *
 */
public class i18nTest extends AbstractServiceImplTest{
	@Autowired
	MessageSource messageSource;
	
	@Test
	public void getMessageSource_shouldReturnCorrectMessage(){
		//arrage
		String expected = "testcode_en";
		
		//act
		String message = messageSource.getMessage("testcode", null, Locale.ENGLISH);
		
		//assert
		assertThat(message).isEqualTo(expected);
	}
	
	@Test
	public void getMessageSource_shouldUseDefaultLocaleMessages_whenNoLocaleCorrespondToRequest(){
		//arrage
		String expected = "default_locale_test_code";
		
		//act
		String message = messageSource.getMessage("testcode", null, Locale.JAPAN);
		
		//assert
		assertThat(message).isEqualTo(expected);
	}
}
