package com.nice.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nice.football")
public class NiceFootballTeamApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NiceFootballTeamApiApplication.class, args);
	}

}
