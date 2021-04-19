package com.example.forum_4_stupid.todoSmsSend;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import com.twilio.rest.api.v2010.account.MessageCreator;

@EnableAsync
@Configuration
public class SendTodoMessages {
	
	@Async
	public static void sendMessages(MessageCreator messageToBeSent, Date dateToBeSent) {
		ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		ConcurrentTaskScheduler scheduler = new ConcurrentTaskScheduler(scheduledExecutor);
		
		System.out.println("tanginamo");
		scheduler.schedule(() -> messageToBeSent.create() 
				, dateToBeSent);
		}
	
}
