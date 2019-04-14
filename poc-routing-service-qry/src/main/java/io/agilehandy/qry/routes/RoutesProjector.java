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

import io.agilehandy.common.api.events.routes.RouteCreatedEvent;
import lombok.extern.log4j.Log4j2;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author Haytham Mohamed
 **/

@Component
@EnableBinding(Sink.class)
@Log4j2
public class RoutesProjector {

	private final RouteRepository routeRepository;

	public RoutesProjector(RouteRepository routeRepository) {
		this.routeRepository = routeRepository;
	}

	@StreamListener(target = Sink.INPUT,
			condition = "headers['event_type']=='Route_CREATED'")
	public void routeCreatedProjection(@Payload RouteCreatedEvent event) {
		log.info("Projecting a new Route entity");
		Route route = new Route();
		route.setId(event.getRouteId());
		route.setCargoId(event.getCargoId());
		route.setOriginOpZone(event.getOrigin().getOpZone());
		route.setOriginFacility(event.getOrigin().getFacility());
		route.setDestOpZone(event.getDestination().getOpZone());
		route.setDestFacility(event.getDestination().getFacility());
		routeRepository.save(route);
	}
}
