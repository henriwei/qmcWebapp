package com.myway.questmultichoice.service.exception;

import com.myway.questmultichoice.utils.BusinessConstraintCode;

public class BusinessConstraintViolationException extends Exception{
	private BusinessConstraintCode code;
	
	public BusinessConstraintViolationException() {
		super();
	}

	public BusinessConstraintViolationException(BusinessConstraintCode code) {
		super();
		this.code = code;
	}
	
	public BusinessConstraintViolationException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public BusinessConstraintViolationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BusinessConstraintViolationException(String arg0) {
		super(arg0);
	}

	public BusinessConstraintViolationException(Throwable arg0) {
		super(arg0);
	}

	public BusinessConstraintCode getCode() {
		return code;
	}
}
