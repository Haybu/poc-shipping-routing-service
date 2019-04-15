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


package io.agilehandy.web.routes;

import io.agilehandy.common.api.events.cargos.CargoAddedEvent;
import io.agilehandy.pubsub.RouteEventChannels;
import io.agilehandy.routes.RouteCreateCommand;
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
public class RouteEventsListener {

	private final RouteService service;

	public RouteEventsListener(RouteService service) {
		this.service = service;
	}

	// Create a new route every time a new cargo is requested in a new booking
	@StreamListener(target = RouteEventChannels.CARGOS_EVENTS_IN,
			condition = "headers['event_type']=='CARGO_ADDED'")
	public void cargoAddedListener(@Payload CargoAddedEvent event) {
		log.info("Get a cargo added event! will create a new route");
		RouteCreateCommand cmd =
				new RouteCreateCommand.Builder()
				.setBookingId(event.getBookingId())
				.setCargoId(event.getCargoId())
				.setOrigin(event.getOrigin())
				.setDestination(event.getDestination())
				.build();
		service.createRoute(cmd);
	}
}
