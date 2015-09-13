
package com.myway.questmultichoice.service.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myway.questmultichoice.service.jpa.configuration.ServiceTestConfig;
import com.myway.questmultichoice.service.jpa.listener.ServiceTestExecutionListener;


@RunWith(SpringJUnit4ClassRunner.class)  //Specify runner for this test. SpringJUnit4ClassRunner is Spring’s JUnit support class for running test cases within Spring’s ApplicationContext environment
@ContextConfiguration(classes = {ServiceTestConfig.class}) //indicates to the Spring JUnit runner on the configuration to be loaded
@TestExecutionListeners({ServiceTestExecutionListener.class})
@ActiveProfiles("test") //This indicates to Spring that beans belonging to the test profile should be loaded. 
public abstract class AbstractServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

	@PersistenceContext
	protected EntityManager em;
	
}
