package com.haddouti.journeymanager.here.route.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haddouti.journeymanager.here.HereConfig;
import com.haddouti.journeymanager.here.route.HereRouteService;
import com.haddouti.journeymanager.service.route.spi.Route;

@RestController
@RequestMapping(path = "/here/route/v1")
public class RestRoute {

	@Autowired
	private HereConfig hereConfig;

	@Autowired
	private HereRouteService service;

	@GetMapping(path = "/version")
	public String getAppId() {
		return hereConfig.getVersion();
	}

	@GetMapping(path = "/route/{src}/{dst}")
	public List<Route> getRoute(@PathVariable("src") String source, @PathVariable("dst") String destination) {

		final List<Route> routes = service.find(source, destination);
		return routes;
	}
}
