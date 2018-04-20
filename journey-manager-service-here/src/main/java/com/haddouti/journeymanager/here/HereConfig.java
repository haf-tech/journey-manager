package com.haddouti.journeymanager.here;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
// @ConfigurationProperties(prefix = "here")
public class HereConfig {

	@Value("${here.version}")
	private String version;

	@Value("${here.api.id}")
	private String apiId;

	@Value("${here.api.code}")
	private String apiCode;

	@Value("${here.service.routing}")
	private String serviceRouting;

	@Value("${here.service.geocoder}")
	private String serviceGeocoder;

	public String getUrlForRoutingService() {
		return String.format("%s?app_id=%s&app_code=%s&waypoint0={0}&waypoint1={1}&mode=fastest;car;traffic:disabled",
				serviceRouting, apiId, apiCode);
	}

	public String getUrlForGeocoderService() {
		return String.format("%s?app_id=%s&app_code=%s&searchtext={0}", serviceGeocoder, apiId, apiCode);
	}

	public String getVersion() {
		return version;
	}

	public String getApiId() {
		return apiId;
	}

	public String getApiCode() {
		return apiCode;
	}

}
