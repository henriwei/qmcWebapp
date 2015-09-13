package com.myway.questmultichoice.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.myway.questmultichoice.domaine.Choice;
import com.myway.questmultichoice.domaine.QuestionMultiChoice;
import com.myway.questmultichoice.domaine.QuestionTag;
import com.myway.questmultichoice.repository.QuestionMultiChoiceRepository;
import com.myway.questmultichoice.service.QuestionMultiChoiceService;
import com.myway.questmultichoice.service.QuestionTagService;
import com.myway.questmultichoice.service.exception.BusinessConstraintViolationException;
import com.myway.questmultichoice.utils.BusinessConstraintCode;


@Service("questionMultiChoiceServiceImplJpa")
@Repository
@Transactional
public class QuestionMultiChoiceServiceImpl implements
		QuestionMultiChoiceService {
	
	private Logger log = LoggerFactory.getLogger(QuestionMultiChoiceServiceImpl.class);
	
	@Autowired
	private QuestionMultiChoiceRepository qcmRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private QuestionTagService qTagService;
	
	@Override
	@Transactional(readOnly=true)
	public List<QuestionMultiChoice> findAll() {
//		log.debug("findAll()");
//		List<QuestionMultiChoice> list = em.createNamedQuery("QuestionMultiChoice.findAll", QuestionMultiChoice.class).getResultList();
//		List<QuestionMultiChoice> list = em.createQuery("SELECT q FROM QuestionMultiChoice q", QuestionMultiChoice.class).getResultList();
//		return list;
		return Lists.newArrayList(qcmRepository.findAll());
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<QuestionMultiChoice> findByTags_Id(Long id) {
//		log.debug("findByTags_Id( " + id + " )");
//		TypedQuery<QuestionMultiChoice> query = em.createNamedQuery("QuuestionMultiChoice.findAllWithTheTagId", QuestionMultiChoice.class).setParameter("id", id);
//		List<QuestionMultiChoice> list = query.getResultList();
		List<QuestionMultiChoice> list = qcmRepository.findByTags_Id(id);
		return list;
	}

	@Override
	@Transactional(readOnly=true)
	public QuestionMultiChoice findOne(Long id){
		return qcmRepository.findOne(id);
	}

	@Override
	public QuestionMultiChoice save(QuestionMultiChoice qmc) throws DataIntegrityViolationException{
		return qcmRepository.save(qmc);
	}

	@Override
	public void insertChoice(Long questionId, Choice choice) throws IllegalArgumentException, BusinessConstraintViolationException{
		QuestionMultiChoice existingQcm = findOne(questionId);
		if(existingQcm == null) throw new IllegalArgumentException();
		for(Choice c : existingQcm.getChoices()){
			if(c.getText().equals(choice.getText())) {
				throw new BusinessConstraintViolationException(BusinessConstraintCode.CHOICE_TEXT_SHOULD_BE_UNIQUE_PER_QUESTION);
			}
		}
		choice.setQuestion(existingQcm);
		existingQcm.getChoices().add(choice);
	}

	@Override
	public void addTagToQuestion(Long qcmId, QuestionTag tag) throws IllegalArgumentException{
		QuestionMultiChoice existingQcm = findOne(qcmId);
		if(existingQcm == null) throw new IllegalArgumentException();
		tag = qTagService.getTagWithCreation(tag);
		existingQcm.getTags().add(tag);
		tag.getQuestions().add(existingQcm);  //if the question reference this tag but not inverse, both references will fail.
	}

	@Override
	public void remove(Long id) {
		qcmRepository.delete(id);
	}
}
