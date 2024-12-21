package com.example.happyDream;

import com.example.happyDream.Service.ChargerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HappyDreamApplication implements CommandLineRunner {

	@Autowired
	private ChargerService chargerService;
	public static void main(String[] args) {
		SpringApplication.run(HappyDreamApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
