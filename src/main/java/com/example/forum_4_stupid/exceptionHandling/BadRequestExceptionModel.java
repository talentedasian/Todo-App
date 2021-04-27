package com.example.forum_4_stupid.exceptionHandling;

import java.util.List;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BadRequestExceptionModel {
	
	private String err;

	private String reason;
	
	@JsonProperty(value = "body_errors")
	private List<FieldError> fieldErrors;
	
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

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public BadRequestExceptionModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BadRequestExceptionModel(String err, String reason, List<FieldError> fieldErrors) {
		super();
		this.err = err;
		this.reason = reason;
		this.fieldErrors = fieldErrors;
	}

}
