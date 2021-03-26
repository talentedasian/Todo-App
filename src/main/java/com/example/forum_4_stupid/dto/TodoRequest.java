package com.example.forum_4_stupid.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class TodoRequest {

	@NotNull
	private String title;
	
	@NotNull
	private String content;
	
	@Null
	private LocalDateTime deadline;
	
	private String username;
	
	@NotNull
	private int day, month, year, hour, minute; 
	
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

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
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
		this.deadline = LocalDateTime.of(year, month, day, hour, minute);
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
