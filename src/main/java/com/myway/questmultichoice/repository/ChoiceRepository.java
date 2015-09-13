package com.myway.questmultichoice.repository;

import org.springframework.data.repository.CrudRepository;
import com.myway.questmultichoice.domaine.Choice;

public interface ChoiceRepository extends CrudRepository<Choice, Long> {
	
}
