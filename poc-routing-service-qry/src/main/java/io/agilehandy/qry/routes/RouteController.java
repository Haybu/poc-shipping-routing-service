/*
 * Copyright 2013-2019 the original author or authors.
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
package io.agilehandy.qry.routes;

import java.util.List;

import io.agilehandy.common.api.exceptions.RouteNotFoundException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Haytham Mohamed
 **/

@RestController
public class RouteController {

	private final RouteRepository repository;

	public RouteController(RouteRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public Iterable<Route> getRoutes() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public Route getRoute(@PathVariable String id) {
		return repository.findById(id)
				.orElseThrow(() -> new RouteNotFoundException("Route with id {} is not found!".format(id)));
	}

	@GetMapping("/cargos/{cargoId}/routes/{routeId}")
	public List<Route> getCargoOneRoute(@PathVariable String cargoId, @PathVariable String routeId) {
		return repository.findByIdAndCargoId(routeId, cargoId);
	}

	@GetMapping("/cargos/{cargoId}/routes")
	public List<Route> getCargoRoutes(@PathVariable String cargoId, @PathVariable String routeId) {
		return repository.findByCargoId(cargoId);
	}
}
