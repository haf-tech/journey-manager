package com.haddouti.journeymanager.service.route.spi;

/**
 * Route point representation.
 */
public class RoutePoint {

	/**
	 * ID to identify this point
	 */
	private String id;

	/**
	 * Point name (like search text/street+city etc)
	 */
	private String pointName;

	private Double latitude;
	private Double longitude;

	public RoutePoint(String id, String pointName, Double lat, Double lon) {
		this.id = id;
		this.pointName = pointName;
		this.latitude = lat;
		this.longitude = lon;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoutePoint [id=").append(id).append(", pointName=").append(pointName).append(", latitude=")
				.append(latitude).append(", longitude=").append(longitude).append("]");
		return builder.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
