package com.haddouti.journeymanager.here.route;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haddouti.journeymanager.here.HereConfig;
import com.haddouti.journeymanager.service.route.spi.Route;
import com.haddouti.journeymanager.service.route.spi.Route.RouteCalculationType;
import com.haddouti.journeymanager.service.route.spi.Route.RouteSummary;
import com.haddouti.journeymanager.service.route.spi.Route.RouteTransportMode;
import com.haddouti.journeymanager.service.route.spi.RoutePoint;
import com.haddouti.journeymanager.service.route.spi.RouteService;
import com.navteq.lbsp.routing_calculateroute._4.CalculateRouteResponseType;
import com.navteq.lbsp.routing_calculateroute._4.CalculateRouteType;
import com.navteq.lbsp.routing_common._4.RouteLegType;
import com.navteq.lbsp.routing_common._4.RouteType;
import com.navteq.lbsp.routing_common._4.TransportModeType;
import com.navteq.lbsp.routing_common._4.WaypointType;

/**
 * A HERE implementation of the {@link RouteService}.
 * 
 * Use the following HERE APIs
 * <ul>
 * <li>Geocoder: to retrieve the way points for a given textual position (like
 * Street + City)</li>
 * <li>CalculateRoute: The routing services by itself</li>
 * </ul>
 *
 */
@Service
public class HereRouteService implements RouteService {

	private static final Logger LOG = LoggerFactory.getLogger(HereRouteService.class);

	@Autowired
	private HereConfig config;

	private final ObjectMapper om = new ObjectMapper();
	private final RestTemplate restTpl = new RestTemplate();

	@Override
	public List<Route> find(final String source, final String destination) {

		// retrieve the routing URL with GeoWaypoints
		final String routingUrl = calcUrlForCalculateRouteService(source, destination);

		// retrieve the routes
		final CalculateRouteType responseWrapper = callService(routingUrl, CalculateRouteType.class);

		final List<Route> totalRoutes = mapCalculateRouteResponse(routingUrl, responseWrapper);

		return totalRoutes;
	}

	@Override
	public List<Route> find(final RoutePoint source, final RoutePoint destination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Route> findById(final String id) {
		// TODO Auto-generated method stub
		return null;
	}

	private String calcUrlForCalculateRouteService(final String source, final String destination) {
		return config.getUrlForRoutingService().replace("{0}", getGeoWaypoint(source)).replace("{1}", getGeoWaypoint(destination));
	}

	/**
	 * Determines the geo way point using the given address and HERE GeoCoder
	 * API.
	 * 
	 * HERE expects in most API a geo waypoint in the format
	 * "geo!latitude,longitude". This method determines for the given address
	 * this information.
	 * 
	 * @param adr
	 *            String representing an address, like "Street, City".
	 * @return Returns null in case if error or nothing found, otherwise the geo
	 *         way point in the format "geo!latitude,longitude".
	 */
	protected String getGeoWaypoint(final String adr) {

		if (StringUtils.isBlank(adr)) {
			LOG.warn("Address not given. Parameter={}.", adr);
			return null;
		}

		final String url = config.getUrlForGeocoderService().replace("{0}", adr);
		final String response = callService(url, String.class);

		if (StringUtils.isBlank(response)) {
			LOG.warn("GeoCoder response is empty. Response={}.", response);
			return null;
		}

		LOG.debug("Geocoder.Url={}. Response={}", url, response);

		String latitude = null;
		String longitude = null;
		try {
			final JsonNode jsonNode = om.readTree(response);
			final JsonNode results = jsonNode.at("/Response/View/0/Result");
			if (results.size() > 0) {
				final JsonNode firstResult = results.get(0);
				final JsonNode navigation = firstResult.at("/Location/NavigationPosition/0");
				latitude = navigation.get("Latitude").asText();
				longitude = navigation.get("Longitude").asText();

				LOG.info("lat={}, lon={}", latitude, longitude);
				return String.format("geo!%s,%s", latitude, longitude);
			}
		} catch (final IOException e) {
			LOG.error("Exception during handling GeoCoder response occurred.", e);
		}

		return null;
	}

	private List<Route> mapCalculateRouteResponse(final String url, final CalculateRouteType responseWrapper) {
		final List<Route> totalRoutes = new ArrayList<>();
		if (responseWrapper != null && responseWrapper.getResponse() != null) {
			final CalculateRouteResponseType response = responseWrapper.getResponse();

			for (final RouteType route : response.getRoute()) {

				final Route spiRoute = new Route();

				WaypointType waypointType = route.getWaypoint().get(0);
				spiRoute.setStartPoint(mapWaypointTypeToRoutePoint(waypointType));

				waypointType = route.getWaypoint().get(1);
				spiRoute.setEndPoint(mapWaypointTypeToRoutePoint(waypointType));

				final Route spiLegRoute = new Route();
				for (final RouteLegType leg : route.getLeg()) {

					// use the mapped position instead of the original position
					// (from the request)

					spiLegRoute.setStartPoint(mapWaypointTypeToRoutePoint(leg.getStart()));
					spiLegRoute.setEndPoint(mapWaypointTypeToRoutePoint(leg.getEnd()));

					spiRoute.getLegs().add(spiLegRoute);
				}

				final RouteSummary summary = new RouteSummary();
				spiRoute.setSummary(summary);
				summary.setDistance(route.getSummary().getDistance().longValue());

				RouteCalculationType tmpRouteType = null;
				switch (route.getMode().getType()) {
				case FASTEST:
				case FASTEST_NOW:
					tmpRouteType = RouteCalculationType.FASTEST;
					break;
				case SHORTEST:
				case SHORTEST_WALK:
					tmpRouteType = RouteCalculationType.SHORTEST;
					break;
				default:
					tmpRouteType = RouteCalculationType.OTHER;
					break;
				}
				summary.setRouteCalculationType(tmpRouteType);

				final List<RouteTransportMode> allTransportModes = new ArrayList<>();
				for (final TransportModeType transportMode : route.getMode().getTransportModes()) {
					RouteTransportMode tmpTransportMode = RouteTransportMode.OTHER;
					switch (transportMode) {
					case CAR:
						tmpTransportMode = RouteTransportMode.CAR;
						break;
					case PUBLIC_TRANSPORT:
					case PUBLIC_TRANSPORT_TIME_TABLE:
						tmpTransportMode = RouteTransportMode.PUBLIC;
						break;
					default:
						tmpTransportMode = RouteTransportMode.OTHER;
						break;
					}
					allTransportModes.add(tmpTransportMode);
				}

				summary.setTransportModes(allTransportModes);
				summary.setTravelTime(route.getSummary().getTravelTime().longValue());

				totalRoutes.add(spiRoute);
			}

		}

		LOG.trace("URL={}. Response={}.", url, responseWrapper);
		LOG.debug("Result: totalRoutes={}", totalRoutes);

		return totalRoutes;
	}

	private RoutePoint mapWaypointTypeToRoutePoint(final WaypointType waypointType) {
		return new RoutePoint(waypointType.getLinkId(), waypointType.getMappedRoadName(), waypointType.getMappedPosition().getLatitude(),
				waypointType.getMappedPosition().getLongitude());
	}

	protected <T> T callService(final String url, final Class<T> clazz) {

		final T response = restTpl.getForObject(url, clazz);
		return response;
	}

	protected void setConfig(final HereConfig cfg) {
		config = cfg;
	}

}
