package com.myway.questmultichoice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.log.LogFactory;
import com.myway.questmultichoice.service.ChoiceService;


@Controller
@RequestMapping("/choice")
public class ChoiceController {
	private static final Logger logger = LoggerFactory.getLogger(ChoiceController.class);
	
	@Autowired
	private ChoiceService choiceService;
	
	@RequestMapping("/selected")
	public void increaseSelectedTimes(@RequestBody long[] choices){
		logger.info("receiving answers");
		choiceService.updateChoiceSelectedTimes(choices);
	}
}
