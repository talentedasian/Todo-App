package com.example.forum_4_stupid;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LoggerClass {
	
	public static Logger getLogger (Class<?> clazz) {
		return LogManager.getLogger(clazz);
	}
	
	private LoggerClass() {}
	
	private static class LoggerClassHolder {
		static final LoggerClass loggerSingletonInstance = new LoggerClass();
	}
	
	public static LoggerClass getInstance () {
		return LoggerClassHolder.loggerSingletonInstance;
	}

}
