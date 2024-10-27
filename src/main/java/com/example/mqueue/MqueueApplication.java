package com.example.mqueue;

import com.example.mqueue.one2one.One2oneSender;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class MqueueApplication {
	@Profile("usage_message")
	@Bean
	public CommandLineRunner usage() {
		return args -> {
			System.out.println("This app uses Spring Profiles to control its behavior.\n");
			System.out.println("Options are: ");
			System.out.println("java -jar target/mqueue.jar --spring.profiles.active=one2one,sender");
			System.out.println("java -jar target/mqueue.jar --spring.profiles.active=one2one,receiver");
		};
	}

	@Profile("!usage_message")
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
