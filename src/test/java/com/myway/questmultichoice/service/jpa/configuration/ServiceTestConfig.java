package com.myway.questmultichoice.service.jpa.configuration;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * This class define application context for service layer testing. Unlike the
 * configuration for development, only the database schema is created, no data
 * is insert into the database.
 * 
 * Earch test will have a fresh empty database since no data is insert by
 * default and no test will commit modification into database.
 * 
 * @author Zhe
 *
 */
@Configuration
@ImportResource("classpath:datasource-tx-jpa.xml") //this xml configuration contains datasource and transaction configuration which are reusable
@ComponentScan(basePackages={"com.myway.questmultichoice.service.jpa"}) //instruct Spring where to scan service layer components to be tested
@Profile("test") //instruct Spring that the beans defined in this configuration belong to the test profile.
public class ServiceTestConfig {

	/**
	 * execute only the schema.sql script to the H2 database without any data.
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:schema.sql")
				.build();
	}
	
	/**
	 * DBUnit's DataSourceDatabaseTester is build on the datasource bean defined in the xml file for testing.
	 * @return
	 */
	@Bean(name="databaseTester")
	public DataSourceDatabaseTester dataSourceDatabaseTester() {
		DataSourceDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource());
		return databaseTester;
	}
	
	@Bean(name="messageSource")
	public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		//to unify test environment and other environments, we placed i18n folder in src/main/resources
		//Spring's standard is to place i18n inside src/main/webapp/WEB-INF/
		messageSource.setBasenames(new String[]{"classpath:i18n/messages","classpath:i18n/application"});
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}
	/**
	 * This bean is used in test execution listener to load xls file, which contains the test data to insert into database before test begin.
	 * @return
	 */
//	@Bean(name="xlsDataFileLoader")
//	public XlsDataFileLoader xlsDataFileLoader() {
//		return new XlsDataFileLoader();
//	}

}
