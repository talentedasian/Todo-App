package com.example.forum_4_stupid;

import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) throws GeneralSecurityException, IOException {
		SpringApplication.run(DemoApplication.class, args);
	}

}
