package com.example.forum_4_stupid.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class TodoRequest {

	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 20)
	private String title;
	
	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 255)
	private String content;
	
	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(min = 8, max = 20)
	private String username;
	
	@Null
	private LocalDateTime deadline;
	
	@Min(1)
	@Max(31)
	private int day; 
	
	@Min(1)
	@Max(12)
	private int month;
	
	@Min(2021)
	private int year;
	
	@Min(0)
	@Max(23)
	private int hour;
		
	@Min(0)
	@Max(59)
	private int minute;

	@NotNull
	private boolean sendable;
	
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

	public TodoRequest(@NotNull @Size(max = 20) String title, @NotNull @Size(max = 255) String content, String username,
			LocalDateTime deadline, int day, int month, int year, int hour, int minute) {
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
	}

	@Override
	public String toString() {
		return "TodoRequest [title=" + title + ", content=" + content + ", username=" + username + ", deadline="
				+ deadline + ", day=" + day + ", month=" + month + ", year=" + year + ", hour=" + hour + ", minute="
				+ minute + "]";
	}
	
}
