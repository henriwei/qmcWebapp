package com.myway.questmultichoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.myway.questmultichoice.domaine.QuestionMultiChoice;

public interface QuestionMultiChoiceRepository extends CrudRepository<QuestionMultiChoice, Long>{
//	@Query("select q from QuestionMultiChoice q join q.tags t where t.id = ?1")  //This query can be build by Spring Data based on the method name
	public List<QuestionMultiChoice> findByTags_Id(Long id);
	
	
//	@Query("select q from QuestionMultiChoice q left join q.choices c where c.correctness=TRUE")  //questionmultichoice without correct choice will not be included in the result since choice is an embaddable, it's part of questionmultichoice.
//	public List<QuestionMultiChoice> findAllWithCorrectChoice();
}
