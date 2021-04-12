package com.example.forum_4_stupid.dto;

import java.time.LocalDateTime;

public class TodoRequest {

	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 20)
	private String title;
	
	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 255)
	private String content;
	
	private String username;
	
	private LocalDateTime deadline;
	
	private String day; 
	
	private String month;
	
	private String year;
	
	private String hour;
		
	private String minute;
	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
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
	
	public LocalDateTime getDeadline() {
		return deadline;
	}
	
	public void setDeadline() {
		this.deadline = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month),
				Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute));
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public TodoRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
