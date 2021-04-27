package com.example.forum_4_stupid.exceptionHandling;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class BadRequestExceptionModel {
	
	private String err;

	private String reason;
	
	@JsonProperty(value = "body_errors")
	private List<String> reasonsOfErrors;
	
	public List<String> getReasonsOfErrors() {
		return reasonsOfErrors;
	}

	public void setReasonsOfErrors(List<String> reasonsOfErrors) {
		this.reasonsOfErrors = reasonsOfErrors;
	}

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


	public BadRequestExceptionModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BadRequestExceptionModel(String err, String reason, List<String> reasonsOfErrors) {
		super();
		this.err = err;
		this.reason = reason;
		this.reasonsOfErrors = reasonsOfErrors;
	}

	@Override
	public String toString() {
		return "BadRequestExceptionModel [err=" + err + ", reason=" + reason + ", reasonsOfErrors=" + reasonsOfErrors + "]";
	}

}
