/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.agilehandy.web.routes;

import java.util.List;

import io.agilehandy.legs.LegAddCommand;
import io.agilehandy.routes.Route;
import io.agilehandy.routes.RouteCreateCommand;

import org.springframework.stereotype.Service;

/**
 * @author Haytham Mohamed
 **/

@Service
public class RouteService {

	private final RouteRepository repository;

	public RouteService(RouteRepository repository) {
		this.repository = repository;
	}

	public String createRoute(RouteCreateCommand cmd) {
		Route route = new Route(cmd);
		repository.save(route);
		return route.getId().toString();
	}

	public void addLeg(List<LegAddCommand> commands) {
		String routeId = commands.get(0).getRouteId();
		Route route = getRouteById(routeId);
		commands.stream().forEach(route::addLeg);
		repository.save(route);
	}

	public Route getRouteById(String routeId) {
		return repository.findById(routeId);
	}

	public void save(Route route) {
		repository.save(route);
	}

}
