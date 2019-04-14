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
package io.agilehandy.qry.legs;

import java.util.Optional;

import io.agilehandy.common.api.events.legs.LegAddedEvent;
import io.agilehandy.qry.routes.Route;
import io.agilehandy.qry.routes.RouteRepository;
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
public class LegsProjector {

	private final LegRepository legRepository;
	private final RouteRepository routeRepository;

	public LegsProjector(LegRepository legRepository, RouteRepository routeRepository) {
		this.legRepository = legRepository;
		this.routeRepository = routeRepository;
	}

	@StreamListener(target = Sink.INPUT,
			condition = "headers['event_type']=='LEG_ADDED'")
	public void legAddedProjection(@Payload LegAddedEvent event) {
		log.info("Projecting a new Leg entity");
		Optional<Route> optional = routeRepository.findById(event.getBookingId());
		if(optional.isPresent()) {
			Route route = optional.get();
			Leg leg = new Leg();
			leg.setRoute(route);
			leg.setLegId(event.getLegId());
			leg.setStartOpZone(event.getStart().getOpZone());
			leg.setStartFacility(event.getStart().getFacility());
			leg.setEndOpZone(event.getEnd().getOpZone());
			leg.setEndFacility(event.getEnd().getFacility());
			leg.setPickupTime(event.getOccurredOn());
			leg.setDropOffTime(event.getOccurredOn());
			leg.setTransType(event.getTransType());
			legRepository.save(leg);
		}
	}

}
