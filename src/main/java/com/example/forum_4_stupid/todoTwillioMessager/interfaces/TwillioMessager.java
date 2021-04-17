package com.example.forum_4_stupid.todoTwillioMessager.interfaces;

import com.example.forum_4_stupid.dto.TodoRequest;

public interface TwillioMessager {

	public void sendMessage(TodoRequest todoRequest);
}
