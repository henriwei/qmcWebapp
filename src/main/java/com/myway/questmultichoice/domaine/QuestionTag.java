package com.myway.questmultichoice.domaine;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Tag")
//@NamedQueries({
//	@NamedQuery(name="QuestionTag.findById", query="select t from QuestionTag t where t.id=:id"),
//	@NamedQuery(name="QuestionTag.findAllByQuestionId", query="select t from QuestionTag t join t.questions q where q.id=(:id)")
//})
public class QuestionTag extends AbstractEntityBase{
	
	private static final long serialVersionUID = -8568787582017875274L;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@ManyToMany
	@JoinTable(name="TAG_QUESTION_ASSOCIATION",
		joinColumns=@JoinColumn(name="tag_id"),
		inverseJoinColumns=@JoinColumn(name="question_id")
	)
	private List<QuestionMultiChoice> questions;
	
	public QuestionTag(){
		
	}
	
	public QuestionTag(String name){
		this.questions = new ArrayList<QuestionMultiChoice>();
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	public List<QuestionMultiChoice> getQuestions(){
		return questions;
	}
	
	public void setQuestions(List<QuestionMultiChoice> questions){
		this.questions = questions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		QuestionTag other = (QuestionTag) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
