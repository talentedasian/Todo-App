package com.example.forum_4_stupid.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ExceptionMessageModel {
	
	private String err, reason, optional;

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOptional() {
		return optional;
	}

	public void setOptional(String optional) {
		this.optional = optional;
	}

	public ExceptionMessageModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExceptionMessageModel(String err, String reason, String optional) {
		super();
		this.err = err;
		this.reason = reason;
		this.optional = optional;
	}
	
}
