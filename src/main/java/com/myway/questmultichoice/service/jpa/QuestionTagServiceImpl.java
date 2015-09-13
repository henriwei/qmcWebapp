package com.myway.questmultichoice.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.myway.questmultichoice.domaine.QuestionTag;
import com.myway.questmultichoice.repository.QuestionTagRepository;
import com.myway.questmultichoice.service.QuestionTagService;
import com.myway.questmultichoice.service.exception.BusinessConstraintViolationException;

@Service("questionTagServiceImplJpa")
@Repository
@Transactional
public class QuestionTagServiceImpl implements QuestionTagService{
	private Logger log = LoggerFactory.getLogger(QuestionTagServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private QuestionTagRepository qTagRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public QuestionTag findOne(Long id) {
//		log.debug("getById("+ id + ")");
//		TypedQuery<QuestionTag> query = em.createNamedQuery("QuestionTag.findById", QuestionTag.class);
//		query.setParameter("id", id);
//		return query.getSingleResult();
		return qTagRepository.findOne(id);
	}
	
	@Override
	public List<QuestionTag> findByQuestions_Id(Long questionId) {
//		log.debug("findAllByQuestionId(" + questionId + ")");
//		TypedQuery<QuestionTag> query = em.createNamedQuery("QuestionTag.findAllByQuestionId", QuestionTag.class).setParameter("id", questionId);
//		List<QuestionTag> list = query.getResultList();
		List<QuestionTag> list = qTagRepository.findByQuestions_Id(questionId);
		return list;
	}

	@Override
	public List<QuestionTag> findAll() {
		return Lists.newArrayList(qTagRepository.findAll());
	}

	@Override
	public QuestionTag findByName(String name) {
		return qTagRepository.findByName(name.toLowerCase());
	}

	@Override
	public boolean existWithName(String name) {
		if(findByName(name)!=null)
			return true;
		else 
			return false;
	}

	@Override
	public QuestionTag createTag(QuestionTag newTag) throws BusinessConstraintViolationException {
		if(existWithName(newTag.getName()))
			throw(new BusinessConstraintViolationException());
		return qTagRepository.save(newTag);
	}
	
	@Override
	public QuestionTag getTagWithCreation(QuestionTag newTag){
		QuestionTag existingTag = findByName(newTag.getName());
		if(existingTag == null) {
			newTag = qTagRepository.save(newTag);
			return newTag;
		} else {
			return existingTag;
		}
	}

}
