package com.myway.questmultichoice.domaine;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6495052438854470752L;
	
//	@Nonnull  // from java.annotation, it's for validation
	@Column(name="question", nullable=false)  //this is JPA annotation, it indicates database schema detail
	private String question;
	
//	@ManyToMany
//	@JoinTable(name="correct_choice",
//		joinColumns=@JoinColumn(name="question_multi_choice_id"),
//		inverseJoinColumns=@JoinColumn(name="choice_id")
//	)
	@OrderBy("text")  //order the choice collection, otherwise the order is undefined between too fetchs.
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="choice",
	joinColumns=@JoinColumn(name="question_id"))
	private List<Choice> choices;
	
//	private List<Choice> wrongChoices;
	
//	@ManyToMany
//	@JoinTable(name="tag_question_association",
//		joinColumns=@JoinColumn(name="question_id"),
//		inverseJoinColumns=@JoinColumn(name="tag_id")
//	)
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
