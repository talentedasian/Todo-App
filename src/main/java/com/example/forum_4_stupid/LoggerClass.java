package com.example.forum_4_stupid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LoggerClass {
	
	private static final Logger logger = LogManager.getLogger(LoggerClass.class);
	
	public static Logger getLogger () {
		return logger;
	}
	
	private LoggerClass() {}
	
	private static class LoggerClassHolder {
		static final LoggerClass loggerSingletonInstance = new LoggerClass();
	}
	
	public static LoggerClass getInstance () {
		return LoggerClassHolder.loggerSingletonInstance;
	}

}
