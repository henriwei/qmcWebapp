package com.myway.questmultichoice.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myway.questmultichoice.domaine.Choice;
import com.myway.questmultichoice.repository.ChoiceRepository;
import com.myway.questmultichoice.service.ChoiceService;

@Service
@Repository
@Transactional
public class ChoiceServiceImpl implements ChoiceService{
	@Autowired
	private ChoiceRepository choiceRepository;

	@Override
	public void updateChoiceSelectedTimes(long[] choices) {
		for(long id : choices){
			Choice choice = choiceRepository.findOne(id);
			choice.incrementSelectedTime();
		}
	}

}
