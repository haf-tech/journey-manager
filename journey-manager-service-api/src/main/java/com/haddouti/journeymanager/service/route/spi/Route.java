package com.haddouti.journeymanager.service.route.spi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Route {

	private String routeId;
	private RoutePoint startPoint;
	private RoutePoint endPoint;

	private Date startAt;
	private Date endAt;

	private List<Route> legs = new ArrayList<>();

	private RouteSummary summary;

	public static class RouteSummary {

		private Long distance;
		private Long travelTime;
		private List<RouteTransportMode> transportModes = new ArrayList<>();
		private RouteCalculationType routeCalculationType;

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("RouteSummary [distance=").append(distance).append(", travelTime=").append(travelTime).append(", transportModes=")
					.append(transportModes).append(", routeCalculationType=").append(routeCalculationType).append("]");
			return builder.toString();
		}

		public Long getDistance() {
			return distance;
		}

		public void setDistance(Long distance) {
			this.distance = distance;
		}

		public Long getTravelTime() {
			return travelTime;
		}

		public void setTravelTime(Long travelTime) {
			this.travelTime = travelTime;
		}

		public List<RouteTransportMode> getTransportModes() {
			return transportModes;
		}

		public void setTransportModes(List<RouteTransportMode> transportModes) {
			this.transportModes = transportModes;
		}

		public RouteCalculationType getRouteCalculationType() {
			return routeCalculationType;
		}

		public void setRouteCalculationType(RouteCalculationType routeCalculationType) {
			this.routeCalculationType = routeCalculationType;
		}

	}

	public static enum RouteTransportMode {

		CAR, PUBLIC, MIX, OTHER;
	}

	public static enum RouteCalculationType {

		FASTEST, SHORTEST, OTHER;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Route [routeId=").append(routeId).append(", startPoint=").append(startPoint).append(", endPoint=").append(endPoint).append(", startAt=")
				.append(startAt).append(", endAt=").append(endAt).append(", legs=").append(legs).append(", summary=").append(summary).append("]");
		return builder.toString();
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public RoutePoint getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(RoutePoint startPoint) {
		this.startPoint = startPoint;
	}

	public RoutePoint getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(RoutePoint endPoint) {
		this.endPoint = endPoint;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public List<Route> getLegs() {
		return legs;
	}

	public void setLegs(List<Route> legs) {
		this.legs = legs;
	}

	public RouteSummary getSummary() {
		return summary;
	}

	public void setSummary(RouteSummary summary) {
		this.summary = summary;
	}

}
