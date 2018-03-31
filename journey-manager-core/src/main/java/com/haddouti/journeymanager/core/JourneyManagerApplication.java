package com.haddouti.journeymanager.core;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.haddouti.journeymanager.core.journey.web.RestJourney;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = { "com.haddouti.journeymanager.core" })
@EnableSwagger2
@Configuration
public class JourneyManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JourneyManagerApplication.class, args);
	}

	@Bean
	public Docket swaggerApiConfig() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(RestJourney.class.getPackage().getName()))
				.paths(PathSelectors.any()).build().apiInfo(swaggerMetaData());
	}

	private ApiInfo swaggerMetaData() {

		Collection<VendorExtension> vendors = new ArrayList<>();
		ApiInfo apiInfo = new ApiInfo("Journey Manager", "Journey Manager Core", "1.0", "Terms of service",
				new Contact("Hafid Haddouti", "https://github.com/haf-tech/", "code@haddouti.com"),
				"Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0", vendors);
		return apiInfo;
	}
}
