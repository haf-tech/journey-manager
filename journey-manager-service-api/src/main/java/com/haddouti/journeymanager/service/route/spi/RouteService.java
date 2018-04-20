package com.haddouti.journeymanager.service.route.spi;

import java.util.List;
import java.util.Optional;

/**
 * Service providing functionality to retrieve {@link Route} objects.
 *
 */
public interface RouteService {

	/**
	 * Find for the given source and destination possible routes.
	 * 
	 * @param source
	 * @param destination
	 * @return List of possible routes
	 */
	public List<Route> find(String source, String destination);

	/**
	 * Find for a given source an destination possible routes.
	 * 
	 * @param source
	 * @param destination
	 * @return List of possible routes
	 */
	public List<Route> find(RoutePoint source, RoutePoint destination);

	/**
	 * Delivers a route for the given id.
	 * 
	 * @param id
	 *            ID which represents a special route
	 * @return A route or nothing
	 */
	public Optional<Route> findById(String id);
}
