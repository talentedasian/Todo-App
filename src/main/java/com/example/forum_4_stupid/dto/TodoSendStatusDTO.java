package com.example.forum_4_stupid.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TodoSendStatusDTO {
	
	private boolean isGoingToBeSentOnDeadline;
	
	private String reason;

	public boolean isGoingToBeSentOnDeadline() {
		return isGoingToBeSentOnDeadline;
	}

	public void setGoingToBeSentOnDeadline(boolean isGoingToBeSentOnDeadline) {
		this.isGoingToBeSentOnDeadline = isGoingToBeSentOnDeadline;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public TodoSendStatusDTO() {
		super();
	}

	public TodoSendStatusDTO(boolean isGoingToBeSentOnDeadline, String reason) {
		super();
		this.isGoingToBeSentOnDeadline = isGoingToBeSentOnDeadline;
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "TodoNoPhoneNumberDTO [isGoingToBeSentOnDeadline=" + isGoingToBeSentOnDeadline + ", reason=" + reason
				+ "]";
	}

}
