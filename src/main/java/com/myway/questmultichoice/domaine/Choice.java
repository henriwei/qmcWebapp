package com.myway.questmultichoice.domaine;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.CollectionTable;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Embeddable
public class Choice{
	
	private static final long serialVersionUID = -1442801147447896689L;
	
	@Column(name="text", nullable=false)
	private String text;
	
	@Column(name="correctness")
	private boolean correctness;
	
	/**
	 * the number of time this choice is selected.
	 */
	@Column(name="selectedTimes")
	private long selectedTimes;
	
	/**
	 * true if the user has selected this choice.
	 */
	@Transient
	private boolean selected;
	
	public Choice(){}
	
	public Choice(String text, boolean correctness){
		this(text, correctness, 0);
	}
	
	public Choice(String text, boolean correctness, long selectedTimes){
		this.text = text;
		this.correctness = correctness;
		this.selectedTimes = selectedTimes;
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	//If no view annotation on a field of object to serialize, 
	//assumed to mean View identified by Object.class: that is, included in all views.
	//By default all properties without explicit view definition are included in serialization. 
	@JsonView(View.QmcCorrectAnswer.class) 
	public boolean isCorrectness() {
		return correctness;
	}

	public void setCorrectness(boolean correctness) {
		this.correctness = correctness;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@JsonView(View.QmcCorrectAnswer.class) 
	public long getSelectedTimes() {
		return selectedTimes;
	}

	public void setSelectedTimes(long selectedTimes) {
		this.selectedTimes = selectedTimes;
	}

	public void incrementSelectedTime() {
		this.selectedTimes += 1;
	}
	
	public String toString(){
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (correctness ? 1231 : 1237);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	/**
	 * Should compare only the text field, not the correctness field.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Choice other = (Choice) obj;

		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	
}
