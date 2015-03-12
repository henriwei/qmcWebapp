package com.myway.questmultichoice.domaine;

/**
 * This class defines (as its fields) interfaces that are used to defined views, e.g. @JsonView(MyViewName.class).
 * These interfaces define the hierarchy of the corresponding views too.
 * @author Zhe
 *
 */
public class View {
	public static interface QmcNoCorrectAnswer {}

	//all field included in the child view is also presented in the parent view.
	//since the view is inclusive, field without jsonview annotation is also included in all view.
	public static interface QmcCorrectAnswer extends QmcNoCorrectAnswer{}; 
}
