package com.myway.questmultichoice.service;

import java.math.BigDecimal;
import java.util.List;

import com.myway.questmultichoice.domaine.QuestionTag;
import com.myway.questmultichoice.service.exception.BusinessConstraintViolationException;

public interface QuestionTagService {
	/**
	 * find a question tag by its id.
	 * 
	 * @param id
	 * @return the tag
	 */
	public QuestionTag findOne(Long id);

	/**
	 * find all the tags of the question with the id.
	 */
	public List<QuestionTag> findByQuestions_Id(Long questionId);

	/**
	 * @param newTag
	 * @throws BusinessConstraintViolationException
	 */
	public QuestionTag createTag(QuestionTag newTag)
			throws BusinessConstraintViolationException;

	/**
	 * Find all the tags.
	 * 
	 * @return
	 */
	public List<QuestionTag> findAll();

	/**
	 * Find the tag by name, without upper lower case distinction
	 * 
	 * @param name
	 *            name of the tag to be found.
	 * @return the corresponding tag.
	 */
	public QuestionTag findByName(String name);

	/**
	 * return true if one tag exists with the same name without case distinction
	 * 
	 * @param name
	 *            name of the tag to compare to.
	 * 
	 */
	public boolean existWithName(String name);

	/**
	 * search tag by argument tag's name, if found, return the existing tag, if
	 * not, create a new tag based on the argument tag, then return the new tag.
	 * 
	 * @param newTag
	 * @return
	 */
	QuestionTag getTagWithCreation(QuestionTag newTag);
}
