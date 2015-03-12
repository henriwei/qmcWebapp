package com.myway.questmultichoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.myway.questmultichoice.domaine.QuestionTag;

public interface QuestionTagRepository extends CrudRepository<QuestionTag, Long>{
//	@Query("select t from QuestionTag t join t.questions q where q.id=?1")
	public List<QuestionTag> findByQuestions_Id(Long questionId);

	/**
	 * Find the tag by name, without upper lower case distinction
	 * @param name name of the tag to be found.
	 * @return the corresponding tag.
	 */
	@Query("select t from QuestionTag t where LOWER(t.name)=?1")
	public QuestionTag findByName(String name);
}
