package com.myway.questmultichoice.domaine;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="QUESTION_MULTI_CHOICE")
//@NamedQueries({
//	@NamedQuery(name="QuestionMultiChoice.findAll", query="from QuestionMultiChoice q"),
//	@NamedQuery(name="QuestionMultiChoice.findAllWithCorrectChoices", query="select q from QuestionMultiChoice q left join fetch q.correctChoices"),
//	@NamedQuery(name="QuuestionMultiChoice.findAllWithTheTagId", query="select q from QuestionMultiChoice q join q.tags t where t.id = :id")
//}
//)
//@SqlResultSetMapping(
//	name="questionMultiChoice",
//	entities=@EntityResult(entityClass=QuestionMultiChoice.class)
//)
public class QuestionMultiChoice extends AbstractEntityBase{
	
	private static final long serialVersionUID = 6495052438854470752L;
	
//	@Nonnull  // from java.annotation, it's for validation
	@Column(name="question", nullable=false)  //this is JPA annotation, it indicates database schema detail
	private String question;
	
//	@ElementCollection(fetch=FetchType.EAGER)
//	@CollectionTable(name="choice",
//	joinColumns=@JoinColumn(name="question_id"))
	@OneToMany(fetch=FetchType.EAGER, mappedBy="question", cascade=CascadeType.ALL)
	//if the choice need to be persisted in cascade mode with new QuestionMultiChoice, Choice will require the id of question, which is not be available.
	private List<Choice> choices;
	
	@ManyToMany(mappedBy="questions")
	private List<QuestionTag> tags;
	
	public QuestionMultiChoice(){
		
	}
	
	public QuestionMultiChoice(String question){
		this.question = question;
	}
	
	
	public String getQuestion(){
		return question;
	}
	
	public void setQuestion(String question){
		this.question = question;
	}
	
	public List<Choice> getChoices(){
		return choices;
	}
	
	public void setChoices(List<Choice> choices){
		this.choices = choices;
	}
	
	public List<QuestionTag> getTags(){
		return tags;
	}
	
	public void setTags(List<QuestionTag> tags){
		this.tags = tags;
	}
	
	/**
	 * return question only
	 * @return
	 */
	public String questionOnly(){
		return question + "?";
	}
	
//	/**
//	 * return question and choices
//	 * @return
//	 */
//	public String questionAndChoices(){
//		StringBuilder qcm = new StringBuilder();
//		qcm.append(question);
//		qcm.append("?\n");
//		for(Choice choice : choices){
//			qcm.append(choice.getText());
//			qcm.append("\n");
//		}
//		return qcm.toString();
//	}

	
}
