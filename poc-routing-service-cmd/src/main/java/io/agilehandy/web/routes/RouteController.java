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

import io.agilehandy.routes.RouteCreateCommand;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Haytham Mohamed
 **/

@RestController
public class RouteController {

	private final RouteService service;

	public RouteController(RouteService service) {
		this.service = service;
	}

	@PostMapping
	public String createRoute(@RequestBody RouteCreateCommand cmd) {
		return service.createRoute(cmd);
	}

}
