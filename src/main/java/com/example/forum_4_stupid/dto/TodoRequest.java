package com.example.forum_4_stupid.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class TodoRequest {

	@NotNull(message = "Cannot be null")
	@javax.validation.constraints.Size(max = 20)
	private String title;
	
	@NotNull(message = "Cannot be null")
	@javax.validation.constraints.Size(max = 255)
	private String content;
	
	@NotNull(message = "Cannot be null")
	@javax.validation.constraints.Size(min = 8, max = 20)
	private String username;
	
	@Null(message = "Should be null")
	private LocalDateTime deadline;
	
	@Min(value = 1, message = "Value cannot be lower than 1")
	@Max(value = 31, message = "Value cannot be greater than 31")
	private int day; 
	
	@Min(value = 1, message = "Value cannot be lower than 1")
	@Max(value = 12, message =  "Value cannot be greater than 32")
	private int month;
	
	private int year;
	
	@Min(value = 0, message = "Value cannot be lower than 0")
	@Max(value = 23, message = "Value cannot be greater than 23")
	private int hour;
		
	@Min(value = 0, message = "Value cannot be lower than 0")
	@Max(value = 59, message = "Value cannot be greater than 59")
	private int minute;

	@NotNull(message = "Cannot be null")
	private Boolean sendable;	
	
	public boolean isSendable() {
		return sendable;
	}

	public void setSendable(boolean sendable) {
		this.sendable = sendable;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public TodoRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TodoRequest(@NotNull(message = "Cannot be null") @Size(max = 20) String title,
			@NotNull(message = "Cannot be null") @Size(max = 255) String content,
			@NotNull(message = "Cannot be null") @Size(min = 8, max = 20) String username,
			@Null(message = "Should be null") LocalDateTime deadline,
			@Min(value = 1, message = "Value cannot be lower than 1") @Max(value = 31, message = "Value cannot be greater than 31") int day,
			@Min(value = 1, message = "Value cannot be lower than 1") @Max(value = 12, message = "Value cannot be greater than 32") int month,
			int year,
			@Min(value = 0, message = "Value cannot be lower than 0") @Max(value = 23, message = "Value cannot be greater than 23") int hour,
			@Min(value = 0, message = "Value cannot be lower than 0") @Max(value = 59, message = "Value cannot be greater than 59") int minute,
			@NotNull(message = "Cannot be null") Boolean sendable) {
		super();
		this.title = title;
		this.content = content;
		this.username = username;
		this.deadline = deadline;
		this.day = day;
		this.month = month;
		this.year = year;
		this.hour = hour;
		this.minute = minute;
		this.sendable = sendable;
	}

	@Override
	public String toString() {
		return "TodoRequest [title=" + title + ", content=" + content + ", username=" + username + ", deadline="
				+ deadline + ", day=" + day + ", month=" + month + ", year=" + year + ", hour=" + hour + ", minute="
				+ minute + "]";
	}
	
}
