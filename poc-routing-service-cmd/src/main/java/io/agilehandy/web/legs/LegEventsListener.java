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
package io.agilehandy.web.legs;

import io.agilehandy.common.api.events.routes.RouteCreatedEvent;
import io.agilehandy.pubsub.RouteEventChannels;
import io.agilehandy.routes.Route;
import io.agilehandy.web.routes.RouteService;
import lombok.extern.log4j.Log4j2;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author Haytham Mohamed
 **/

@Component
@EnableBinding(RouteEventChannels.class)
@Log4j2
public class LegEventsListener {

	private final LegService legService;
	private final RouteService routeService;

	public LegEventsListener(LegService legService, RouteService routeService) {
		this.legService = legService;
		this.routeService = routeService;
	}

	// Create legs for the new created route
	// This listens to route created events
	@StreamListener(target = RouteEventChannels.ROUTES_EVENTS_IN,
			condition = "headers['event_type']=='ROUTE_CREATED'")
	public void routeAddedListener(@Payload RouteCreatedEvent event) {
		log.info("Get a route created event! will create route legs");
		Route route = routeService.getRouteById(event.getRouteId());
		legService.attachLegsToRoute(route);
		routeService.save(route);
	}
}
