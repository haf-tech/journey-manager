package com.haddouti.journeymanager.core.journey.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Component
@RestController
@RequestMapping("journey/v1")
@Api(value = "RestJourney", description = "Journey Manager: Core")
public class RestJourney {

	@GetMapping(path = "all", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Retrieves all available journeys.", notes = "Delivers all the available journeys for a given user without any restrictions.", nickname = "JourneyAll")
	public JourneyResponse getAll() {

		return new JourneyResponse();
	}
}
