package com.myway.questmultichoice.domaine;

import java.io.Serializable;

import javax.persistence.Transient;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class AbstractEntityBase extends AbstractPersistable<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1905767760562422715L;

	@Override
	@Transient
	@JsonIgnore
	public boolean isNew() {
		return null == getId();
	}
}
