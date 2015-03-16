package com.myway.questmultichoice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;
import com.myway.questmultichoice.domaine.QuestionMultiChoice;
import com.myway.questmultichoice.domaine.View;
import com.myway.questmultichoice.service.QuestionMultiChoiceService;

@RequestMapping("/questmultichoice")
@Controller
public class QuestionMultiChoiceController {
	private static final Logger logger = LoggerFactory.getLogger(QuestionMultiChoiceController.class);
	
	@Autowired
	QuestionMultiChoiceService qmcService;
	
	
	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	@ResponseBody //indicate that the value return by the method should be written in the http response stream directly.
	//If no view annotation on a field of object to serialize, 
	//assumed to mean View identified by Object.class: that is, included in all views.
	@JsonView(View.QmcNoCorrectAnswer.class) 
	public List<QuestionMultiChoice> findAll(){
		return qmcService.findAll();
	}
	
	@RequestMapping(value = "/questions/correctanswers", method = RequestMethod.GET)
	@ResponseBody //indicate that the value return by the method should be written in the http response stream directly.
	@JsonView(View.QmcCorrectAnswer.class)
	public List<QuestionMultiChoice> findAllWithAnswer(){
		return qmcService.findAll();
	}
}
