package com.myway.questmultichoice.service.jpa;

//import static junit.framework.Assert.*;
import static org.fest.assertions.Assertions.assertThat;
import static com.googlecode.catchexception.CatchException.*;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.Test;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.myway.questmultichoice.domaine.Choice;
import com.myway.questmultichoice.domaine.QuestionMultiChoice;
import com.myway.questmultichoice.domaine.QuestionTag;
import com.myway.questmultichoice.service.QuestionMultiChoiceService;
import com.myway.questmultichoice.service.QuestionTagService;
import com.myway.questmultichoice.service.exception.BusinessConstraintViolationException;
import com.myway.questmultichoice.service.jpa.annotation.DataSets;
import com.myway.questmultichoice.utils.BusinessConstraintCode;

//This will setup the database with the dataset read from
@DataSets
// QuestionMultiChoiceServiceTest.xml by the TestExecutionListener of Spring
public class QuestionMultiChoiceServiceTest extends AbstractServiceImplTest {
	// static private QuestionMultiChoiceService qcmService;
	// @BeforeClass
	// static public void init() {
	// GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	// ctx.load("app-context.xml");
	// ctx.refresh();
	// qcmService = ctx.getBean("questionMultiChoiceServiceImplJpa",
	// QuestionMultiChoiceService.class);
	// }

	@Autowired
	// since we use SpringJUnit4ClassRunner (see AbstractServiceImplTest), this
	// test is run in the spring's application context.
	QuestionMultiChoiceService qmcService;
	
	@Autowired
	QuestionTagService qTagService;

	@Test
	// @DataSets(setUpDataSet =
	// "/com/myway/questmultichoice/service/QuestionMultiChoiceServiceTest.xml")
	public void findAll() {
		// arrange
		int correctSize = 3;

		// act
		// this call the findAll method of repository interface, the
		// implementation is created by Spring, we normally test only what we
		// implement, but it's a base test for the following test.
		List<QuestionMultiChoice> results = qmcService.findAll();

		// assert
		assertThat(results).hasSize(correctSize);
		assertThat(results.get(0).getChoices()).containsOnly(
				new Choice("mother", true), new Choice("daughter", false));
	}

	@Test
	public void findOne_shouldReturnTheCorrectOneAndChoiceShouldBeFetched() {
		// arrange
		Long questionId = 1L;

		// act
		// use the service
		QuestionMultiChoice qcm = qmcService.findOne(questionId);

		// assert by using the entity manager directly
		// we have to detach the object we just persist, otherwise the
		// comparison of expected object and actual object make no sense since they both point
		// to the same object in memory.
		// The test class has herited  AbstractTransactionalJUnit4SpringContextTests which is annotated with @transactional
		// this transaction is passed to the tested target method. So they share the
		// same persistence context.
//		em.getEntityManagerFactory();  //This will throw IllegalStateException, Not allowed to create transaction on shared EntityManager - use Spring transaction or EJB CMT instead.
//		boolean qcmIsManagedem = em.contains(qcm);  //This return true, since the same transaction is used for this test method and the qcmService.findOne(), the same persistence context is used too.
//		em.detach(qcm); //without this detachment done, the database will not be hit since the object is still in memory. A database object can be represented by only one in-memory entity object in a persistence context.
		em.clear(); //detached all the managed entities from the present persistence context, otherwise the actual and expected object reference the same object in memory. Then it's pointless to compare them.
		QuestionMultiChoice expected = em.find(QuestionMultiChoice.class, questionId);
		assertThat(qcm).isEqualTo(expected); // assert equality by using the override equals()
		assertThat(qcm.getChoices()).isNotEmpty();  //The choices of a question should be fetched too when the question is fetched.
	}

	@Test(expected = DataIntegrityViolationException.class) // expected = ... is not clear enough, consider using googlecode's catch-exception library.
	public void save_shouldThrowExceptionWhenQuestionTextDuplicate() {
		// arrange
		Long questionId = 1L;
		QuestionMultiChoice existingQcm = em.find(QuestionMultiChoice.class,
				questionId);
		QuestionMultiChoice duplicateTextQcm = new QuestionMultiChoice(// "ttt");
				existingQcm.getQuestion());

		// act
		// catchException(qcmService).save(duplicateTextQcm);
		qmcService.save(duplicateTextQcm);
		// assert This gives bad message, caughtException() returns null if no exception is caught.
		// assertThat(caughtException()).isInstanceOf(DataIntegrityViolationException.class);

	}

	@Test
	// This test is for learning purpose only. In general, only the
	// representative test are necessary. The Save method simply calls the CRUD
	// repository interface implementation of Spring.
	public void save_shouldSaveTheCorrectQuestion() {
		// arrange
		String qtext = "Do you need this";
		QuestionMultiChoice newQcm = new QuestionMultiChoice(qtext);

		// act
		qmcService.save(newQcm);

		// assert
		assertThat(newQcm.getId()).isNotNull(); //the database should give this saved object an id.
		em.clear();
		QuestionMultiChoice expected = em.find(QuestionMultiChoice.class, newQcm.getId());
		assertThat(newQcm).isEqualTo(expected);  //This is using the equals() method of the object.
		// assertThat(em.find(QuestionMultiChoice.class, newQcm.getId()).getQuestion()).isEqualTo(qtext);
//		 List<QuestionMultiChoice> results = qcmService.findAll();
		// assertThat(results).contains(newQcm); //This is not calling the equals method in entity, why?
		
	}

	@Test
	public void findByTags_Id_shouldReturnEmptyWhenNoMatch() {
		// arrage
		Long tagId = 1L;

		// act
		List<QuestionMultiChoice> results = qmcService.findByTags_Id(tagId);

		// assert
		assertThat(results).hasSize(0);
	}

	@Test
	public void findByTags_Id_shouldReturnOneWhenOneToOne() {
		// arrage
		Long tagId = 2L;
		int correctSize = 1;

		// act
		List<QuestionMultiChoice> results = qmcService.findByTags_Id(tagId);

		// assert
		assertThat(results).hasSize(correctSize);
	}

	@Test
	public void findByTags_Id_shouldReturnManyWhenManyToOne() {
		// arrange
		Long tagId = 3L;
		int correctSize = 2;
		Long questionOneId = 2L;
		Long questionTwoId = 3L;
		QuestionMultiChoice qcmOne = em.find(QuestionMultiChoice.class, questionOneId);
		QuestionMultiChoice qcmTwo = em.find(QuestionMultiChoice.class, questionTwoId);
		em.clear();
		// act
		List<QuestionMultiChoice> results = qmcService.findByTags_Id(tagId);

		// assert
		assertThat(results).hasSize(correctSize).containsOnly(qcmOne, qcmTwo);
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertChoice_shouldThrowExceptionWhenNoQuestionFound() throws IllegalArgumentException, BusinessConstraintViolationException{
		//arrange
		Long notExistQuestionId = 100L;
		Choice anyChoice = mock(Choice.class);
		//act
		qmcService.insertChoice(notExistQuestionId, anyChoice);
	}
	
	@Test
	public void insertChoice_shouldThrowExceptionWhenDuplicateChoiceText() throws IllegalArgumentException, BusinessConstraintViolationException {
		// arrage
		Long questionId = 1L;

		// act
		catchException(qmcService).insertChoice(questionId, new Choice("mother", true));
		
		// assert
		Exception e = caughtException();
		assertThat(e).isInstanceOf(BusinessConstraintViolationException.class);
		assertThat(((BusinessConstraintViolationException)e).getCode()).isEqualTo(BusinessConstraintCode.CHOICE_TEXT_SHOULD_BE_UNIQUE_PER_QUESTION);
	}

	@Test
	public void insertChoice_shouldInsertChoice_WhenNotDuplicate_And_QuestionHasChoices() throws IllegalArgumentException, BusinessConstraintViolationException {
		// arrage
		Long questionId = 1L;
		Choice newChoice = new Choice("sister", false);
		QuestionMultiChoice qcm = em.find(QuestionMultiChoice.class, questionId);
		int sizeBeforeInsert = qcm.getChoices().size();
		em.clear();

		// act
		qmcService.insertChoice(questionId, newChoice);
		em.flush();   //flush now before clear, otherwise the database will not be updated.
		// assert
		em.clear();  //clear the cache so we can read the database without cache.
		qcm = em.find(QuestionMultiChoice.class, qcm.getId());
//		List<Choice> choices = qcm.getChoices();
		assertThat(qcm.getChoices()).hasSize(sizeBeforeInsert + 1).contains(newChoice);  //
	}

	@Test
	public void insertChoice_shouldInsertChoice_WhenNotDuplicate_And_QuestionHasNoChoices() throws IllegalArgumentException, BusinessConstraintViolationException {
		// arrage
		Long questionId = 3L;
		Choice newChoice = new Choice("newChoice", false);
		QuestionMultiChoice qcm = em.find(QuestionMultiChoice.class, questionId);
		int sizeBeforeInsert = qcm.getChoices().size();
		em.clear();
		// verify the context again before act

		// act
		qmcService.insertChoice(questionId, newChoice);
		em.flush();
		
		// assert
		em.clear();
		qcm = em.find(QuestionMultiChoice.class, qcm.getId());
		assertThat(qcm.getChoices()).hasSize(sizeBeforeInsert + 1).contains(newChoice);
	}
	
	@Test
	public void updateChoiceSelectedTimes_shouldIncreaseSelectedTimesWhenSelected(){
		//arrange
		Long questId = 1L;
		int choiceIndex = 1;
		QuestionMultiChoice qmc = qmcService.findOne(questId);
		List<Choice> choices = qmc.getChoices();
		Choice choice = choices.get(choiceIndex);
		long selectedTimesBefore = choice.getSelectedTimes();
		choice.setSelected(true);
		em.clear();
		
		//act
		qmcService.updateChoiceSelectedTimes(qmc);
		em.flush();
		
		//assert
		em.clear();
		assertThat(qmcService.findOne(questId).getChoices().get(choiceIndex).getSelectedTimes()).isEqualTo(selectedTimesBefore+1);
	}
	
	@Test
	public void updateChoiceSelectedTimes_shouldNotModifyOtherFields(){
		//arrange
		Long questId = 1L;
		int choiceIndex = 1;
		QuestionMultiChoice qmc = qmcService.findOne(questId);
		List<Choice> choices = qmc.getChoices();
		Choice existingChoice = choices.get(choiceIndex);
		long selectedTimesBefore = existingChoice.getSelectedTimes();
		String oldText = existingChoice.getText();
		boolean oldCorrectness = existingChoice.isCorrectness();
		existingChoice.setSelected(true);
		//insert corrupted choice in the qmc should not change the existing choice
		Choice corruptChoice = new Choice("corruption", true);
		choices.remove(choiceIndex);
		choices.add(choiceIndex, corruptChoice);
		em.clear();
		
		//act
		qmcService.updateChoiceSelectedTimes(qmc);
		em.flush();
		
		//assert
		em.clear();
		Choice newChoice = qmcService.findOne(questId).getChoices().get(choiceIndex);
		assertThat(newChoice.getText()).isEqualTo(oldText);
		assertThat(newChoice.isCorrectness()).isEqualTo(oldCorrectness);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addTag_shouldThrowExceptionWhenNoQuestionFound(){
		//arrange
		Long notExistQuestionId = 100L;
		QuestionTag tag = mock(QuestionTag.class);
		
		//act
		qmcService.addTagToQuestion(notExistQuestionId, tag);
	}
	
	@Test
	public void addTag_shouldCreateNewTagIfNotExist(){
		//arrange
		Long qcmId = 1L;
		QuestionTag noExistingTagToAdd = new QuestionTag("newTag");
		int oldTagSize = qTagService.findAll().size();
		em.clear();
		
		//act
		qmcService.addTagToQuestion(qcmId, noExistingTagToAdd);
		em.flush();
		
		//assert
		em.clear();
		assertThat(qTagService.findAll()).hasSize(oldTagSize + 1).contains(noExistingTagToAdd);
	}
	
	@Test
	public void addTag_shouldNotCreateNewTag_WhenExistTagWithSameName(){
		//arrange
		Long qcmId = 2L;
		String existingTagName = "moral";
		QuestionTag existingTag = new QuestionTag(existingTagName);
		int oldTagListSize = qTagService.findAll().size();
		em.clear();
		
		//act
		qmcService.addTagToQuestion(qcmId, existingTag);
		em.flush();
		
		//assert
		em.clear();
		assertThat(qTagService.findAll()).hasSize(oldTagListSize);
	}
	
	@Test
	public void addTag_shouldNotCreateNewTag_WhenExistTagWithSameNameWithoutCaseDistinction(){
		//arrange
		Long qcmId = 2L;
		String existingTagName = "Moral";
		QuestionTag existingTag = new QuestionTag(existingTagName);
		int oldTagListSize = qTagService.findAll().size();
		em.clear();
		
		//act
		qmcService.addTagToQuestion(qcmId, existingTag);
		em.flush();
		
		//assert
		em.clear();
		assertThat(qTagService.findAll()).hasSize(oldTagListSize);
	}
	
	@Test
	public void addTag_shouldQuestionHasTag_WithExistingTag(){
		//arrange
		Long existingQmcId = 1L;
		Long existingTagId = 1L;
		QuestionTag existingTag = qTagService.findOne(existingTagId);
		QuestionMultiChoice existingQmc = qmcService.findOne(existingQmcId);
		int oldTagListSize = existingQmc.getTags().size();
		em.clear();
		
		//acte
		qmcService.addTagToQuestion(existingQmcId, existingTag);
		em.flush();
		
		//assert
		em.clear();
		existingQmc = qmcService.findOne(existingQmcId);
		assertThat(existingQmc.getTags()).hasSize(oldTagListSize + 1).contains(existingTag);
	}
	
	@Test
	public void addTag_shouldQuestionHasTag_WithNewTag(){
		//arrange
		Long existingQmcId = 1L;
		String newTagName = "newTag";
		QuestionTag newTag = new QuestionTag(newTagName);
		QuestionMultiChoice existingQmc = qmcService.findOne(existingQmcId);
		int oldTagListSize = existingQmc.getTags().size();
		em.clear();
		
		//acte
		qmcService.addTagToQuestion(existingQmcId, newTag);
		em.flush();
		
		//assert
		em.clear();
		existingQmc = qmcService.findOne(existingQmcId);
		assertThat(existingQmc.getTags()).hasSize(oldTagListSize + 1).contains(newTag);
	}
	
	@Test
	public void addTag_shouldTagHasQuestion_WithExistingTag(){
		//arrange
		Long existingQmcId = 1L;
		Long existingTagId = 1L;
		QuestionTag existingTag = qTagService.findOne(existingTagId);
		QuestionMultiChoice existingQmc = qmcService.findOne(existingQmcId);
		int oldQuestionListSize = existingTag.getQuestions().size();
		em.clear();
		
		//acte
		qmcService.addTagToQuestion(existingQmcId, existingTag);
		em.flush();
		
		//assert
		em.clear();
		existingTag = qTagService.findOne(existingTagId);
		assertThat(existingTag.getQuestions()).hasSize(oldQuestionListSize + 1).contains(existingQmc);
	}
	
	@Test
	public void addTag_shouldTagHasQuestion_WithNewTag(){
		//arrange
		Long existingQmcId = 1L;
		String newTagName = "newTag";
		QuestionTag newTag = new QuestionTag(newTagName);
		QuestionMultiChoice existingQmc = qmcService.findOne(existingQmcId);
		int oldQuestionListSize = newTag.getQuestions().size();
		em.clear();
		
		//acte
		qmcService.addTagToQuestion(existingQmcId, newTag);
		em.flush();
		
		//assert
		em.clear();
		newTag = qTagService.findByName(newTagName);
		assertThat(newTag.getQuestions()).hasSize(oldQuestionListSize + 1).contains(existingQmc);
	}
	
	
	
}
