package com.myway.questmultichoice.service.jpa;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.myway.questmultichoice.domaine.Choice;
import com.myway.questmultichoice.service.ChoiceService;
import com.myway.questmultichoice.service.jpa.annotation.DataSets;

@DataSets
public class ChoiceServiceImplTest extends AbstractServiceImplTest{
	@Autowired
	ChoiceService choiceService;
	
	@Test
	public void updateChoiceSelectedTimes_shouldIncreaseSelectedTimes(){
		//arrange
		long choiceOneId = 1L;
		long choiceTwoId = 2L;
		long[] selectedIds = new long[]{choiceOneId, choiceTwoId};
		Choice choiceOne = em.find(Choice.class, choiceOneId);
		Choice choiceTwo = em.find(Choice.class, choiceTwoId);
		em.clear();
		
		//act
		choiceService.updateChoiceSelectedTimes(selectedIds);
		em.flush();
		
		//assert
		em.clear();
		Choice choiceOneAfter = em.find(Choice.class, choiceOneId);
		Choice choiceTwoAfter = em.find(Choice.class, choiceTwoId);
		assertThat(choiceOneAfter.getSelectedTimes()).isEqualTo(choiceOne.getSelectedTimes() + 1);
		assertThat(choiceTwoAfter.getSelectedTimes()).isEqualTo(choiceTwo.getSelectedTimes() + 1);
	}
}
