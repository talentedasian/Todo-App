package com.example.forum_4_stupid.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class TodoRequest {

	@NotNull
	@Size(max = 20)
	private String title;
	
	@NotNull
	@Size(max = 255)
	private String content;
	
	@Null
	private LocalDateTime deadline;
	
	@NotNull
	private String username;
	
	@Null	
	@Size(min = 1, max = 31)
	private int day; 
	
	@Null		
	@Size(min = 1, max = 12)
	private int month;
	
	@Null		
	@Size(min = 2021, max = 2035)
	private int year;
	
	@Null		
	@Size(min = 0, max = 23)
	private int hour;
	
	@Null		
	@Size(min = 0, max = 59)
	private int minute;
	
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
