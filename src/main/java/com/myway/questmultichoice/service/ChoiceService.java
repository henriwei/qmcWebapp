package com.myway.questmultichoice.service;

import org.springframework.stereotype.Service;

public interface ChoiceService {
	/**
	 * increase the selected times of the choices corresponding to ids in the parameter table.
	 * @param id
	 */
	public void updateChoiceSelectedTimes(long[] choices);

}
