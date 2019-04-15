/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.agilehandy.web.legs;

import java.util.List;

import io.agilehandy.common.api.exceptions.LegNotFoundException;
import io.agilehandy.common.api.model.Location;
import io.agilehandy.common.api.model.RoutePath;
import io.agilehandy.common.api.model.TransportationType;
import io.agilehandy.legs.Leg;
import io.agilehandy.legs.LegAddCommand;
import io.agilehandy.routes.Route;
import io.agilehandy.web.routes.RouteRepository;

import org.springframework.stereotype.Service;

/**
 * @author Haytham Mohamed
 **/

@Service
public class LegService {

	private final RouteRepository repository;

	public LegService(RouteRepository repository) {
		this.repository = repository;
	}

	public String addLeg(String routeId, LegAddCommand cmd) {
		Route route = repository.findById(routeId);
		String legId = route.addLeg(cmd).toString();
		repository.save(route);
		return legId;
	}

	public List<Leg> getLegs(String routeId) {
		Route route = repository.findById(routeId);
		return route.getLegList();
	}

	public Leg getLeg(String routeId, String legId) {
		return this.getLegs(routeId).stream()
				.filter(leg -> leg.getId().toString().equals(legId))
				.findFirst()
				.orElseThrow(() -> new LegNotFoundException(
						String.format("No Leg found for route id %s and leg id", routeId, legId)))
				;
	}

	public void attachLegsToRoute(Route route) {
		RoutePath path = this.getPath(route.getOrigin(), route.getDestination());
		path.gtPathLegs().stream()
				.map(leg -> {
					return new LegAddCommand.Builder()
							.setStartLocation(leg.getStartLocation())
							.setEndLocation(leg.getEndLocation())
							.setTransType(leg.getTransType())
							.setRouteId(route.getId().toString())
							.build();
				})
				.forEach(cmd -> route.addLeg(cmd));
	}

	// TODO: some external service to get legs of a route
	public RoutePath getPath(Location origin, Location destination) {
		return new RoutePath.Builder()
			.addLocation(origin, TransportationType.TRUCK)
			.addLocation(new Location("op1", "facility1"), TransportationType.VESSEL)
			.addLocation(new Location("op2", "facility2"), TransportationType.TRUCK)
			.addLocation(destination, null)
			.build();
	}

}
