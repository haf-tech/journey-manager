package com.haddouti.journeymanager.here;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class JourneyManagerHereApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(JourneyManagerHereApplication.class);
		app.addInitializers(new YamlFileApplicationContextInitializer());
		app.run(args);
	}
}
