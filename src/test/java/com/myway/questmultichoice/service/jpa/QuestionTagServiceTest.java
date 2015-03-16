package com.myway.questmultichoice.service.jpa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import static com.googlecode.catchexception.CatchException.*;
import com.myway.questmultichoice.domaine.QuestionMultiChoice;
import com.myway.questmultichoice.domaine.QuestionTag;
import com.myway.questmultichoice.service.QuestionMultiChoiceService;
import com.myway.questmultichoice.service.QuestionTagService;
import com.myway.questmultichoice.service.exception.BusinessConstraintViolationException;
import com.myway.questmultichoice.service.jpa.annotation.DataSets;

import static org.assertj.core.api.Assertions.*;

@DataSets
public class QuestionTagServiceTest extends AbstractServiceImplTest {
	// static private QuestionTagService qcmTagService;
	// @BeforeClass
	// static public void init() {
	// GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	// ctx.load("app-context.xml");
	// ctx.refresh();
	// qcmTagService = ctx.getBean(
	// "questionTagServiceImplJpa",
	// QuestionTagService.class);
	// }

	@Autowired
	QuestionTagService tagService;

	@Autowired
	QuestionMultiChoiceService qcmService;

	@Test
	public void findOne_shouldReturnCorrectTag() {
		// arrage
		Long tagId = 1L;
		QuestionTag expected = em.find(QuestionTag.class, tagId);
		em.detach(expected);
		// act
		QuestionTag tag = tagService.findOne(tagId);

		// assert
		assertThat(tag).isEqualTo(expected);
	}

	@Test
	public void findByName_shouldReturnCorrectTag_whenExist(){
		//arrage
		String existingTagNameOne = "database";
		Long correctTagId = 1L;
		//act
		QuestionTag tag = tagService.findByName(existingTagNameOne);
		
		//assert
		assertThat(tag.getId()).isEqualTo(correctTagId);
	}
	
	@Test
	public void findByName_shouldReturnCorrectTag_whenExistWithJustCaseDifference(){
		//arrage
		String existingTagNameOne = "Database";
		Long correctTagId = 1L;
		//act
		QuestionTag tag = tagService.findByName(existingTagNameOne);
		
		//assert
		assertThat(tag.getId()).isEqualTo(correctTagId);
	}
	
	@Test
	public void findByName_shouldReturnNull_whenNotExist(){
		//arrage
		String existingTagNameOne = "notExist";
		
		//act
		QuestionTag tag = tagService.findByName(existingTagNameOne);
		
		//assert
		assertThat(tag).isNull();
	}
	
	@Test
	public void findAllByQuestionId_shouldReturnCorrectSetOfTag() {
		// arrage
		Long questionId = 1L;
		Long tagIdOne = 1L;
		Long tagIdTwo = 2L;
		int expectedSize = 2;
		
		QuestionTag tagOne = em.find(QuestionTag.class, tagIdOne);
		QuestionTag tagTwo = em.find(QuestionTag.class, tagIdTwo);
		em.clear();
		
		// act
		List<QuestionTag> results = tagService.findByQuestions_Id(questionId);

		// assert
		assertThat(results).hasSize(expectedSize).containsOnly(tagOne, tagTwo);
	}

	@Test
	public void createTag_shouldThrowExceptionWhenDuplicateTagText() throws BusinessConstraintViolationException {
		//arrange
		Long tagOneId = 1L;
		QuestionTag existingTagOne = em.find(QuestionTag.class, tagOneId);
		String duplicateName = existingTagOne.getName();
		em.clear();
		
		//act
		catchException(tagService).createTag(new QuestionTag(duplicateName));
		
		//assert
		assertThat(caughtException()).isInstanceOf(BusinessConstraintViolationException.class);
	}
	
	@Test
	public void createTag_shouldCreateCorrectOne() throws BusinessConstraintViolationException {
		// arrange
		QuestionTag newTag = new QuestionTag("newTag");
		
		// act
		tagService.createTag(newTag);

		// assert
		assertThat(newTag.getId()).isNotNull();
		em.clear();
		QuestionTag copy = em.find(QuestionTag.class, newTag.getId());
		assertThat(newTag).isEqualTo(copy);
	}

	@Test
	public void getTagWithCreation_shouldReturnExistingTag_WhenExist(){
		//arrange
		Long existingTagId = 2L;
		QuestionTag existingTag = tagService.findOne(existingTagId);
		int oldTagListSize = tagService.findAll().size();
		em.clear();
		
		//acte
		QuestionTag tagFound = tagService.getTagWithCreation(existingTag);
		em.flush();
		
		//assert
		em.clear();
		assertThat(tagService.findAll()).hasSize(oldTagListSize);
		assertThat(tagFound).isEqualTo(existingTag);
	}
	
	@Test
	public void getTagWithCreation_shouldCreateNewTag_WhenNotExist(){
		//arrange
		QuestionTag newTag = new QuestionTag("newTag");
		int oldTagListSize = tagService.findAll().size();
		em.clear();
		
		//acte
		QuestionTag tagFound = tagService.getTagWithCreation(newTag);
		em.flush();
		
		//assert
		em.clear();
		assertThat(tagService.findAll()).hasSize(oldTagListSize + 1);
		assertThat(tagFound).isEqualTo(newTag);
	}
	
}
