package com.example.mqueue;

import com.example.mqueue.one2one.One2oneSender;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class MqueueApplication {

	@Profile("sender")
	@Bean
	public CommandLineRunner execute(One2oneSender sender) {
		return args -> {
			sender.send();
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(MqueueApplication.class, args);
	}
}
