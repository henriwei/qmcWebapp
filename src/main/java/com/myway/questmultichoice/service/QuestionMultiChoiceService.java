package com.myway.questmultichoice.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.myway.questmultichoice.domaine.Choice;
import com.myway.questmultichoice.domaine.QuestionMultiChoice;
import com.myway.questmultichoice.domaine.QuestionTag;
import com.myway.questmultichoice.service.exception.BusinessConstraintViolationException;


public interface QuestionMultiChoiceService {

	/**
	 * Find all QuestionMultiChoices
	 * @return all the question multi choice.
	 */
	public List<QuestionMultiChoice> findAll();

	/**
	 * Find one question multi choice by its id.
	 * @param id
	 * @return the question multi choice
	 */
	public QuestionMultiChoice findOne(Long id);
	
	/**
	 *  Find all QuestionMultiChoice with the tag specified
	 * @param tag id
	 * @return all the question tagged by the given tag.
	 */
	public List<QuestionMultiChoice> findByTags_Id(Long id);

	/**
	 * Create new question multi choice
	 * @param question multi choice to be created
	 * @return the created question multi choice
	 */
	public QuestionMultiChoice save(QuestionMultiChoice qcm) throws DataIntegrityViolationException;

	/**
	 * create and insert a new choice to the list of choice for the given question multi choice
	 * @param questionId
	 * @param choice
	 * @throws IllegalArgumentException
	 * @throws BusinessConstraintViolationException
	 */
	public void insertChoice(Long questionId, Choice choice)  throws IllegalArgumentException, BusinessConstraintViolationException;
	
	/**
	 * Add a tag to a question. The tag is created if it's necessary.
	 */
	public void addTagToQuestion(Long qcmId, QuestionTag tag) throws IllegalArgumentException;

	public void updateChoiceSelectedTimes(QuestionMultiChoice qmc) throws IllegalArgumentException;
}
