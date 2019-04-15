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


package io.agilehandy.routes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.agilehandy.common.api.events.RouteEvent;
import io.agilehandy.common.api.events.legs.LegAddedEvent;
import io.agilehandy.common.api.events.routes.RouteCreatedEvent;
import io.agilehandy.common.api.model.Location;
import io.agilehandy.legs.Leg;
import io.agilehandy.legs.LegAddCommand;
import javaslang.API;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.Predicates.instanceOf;

/**
 * @author Haytham Mohamed
 **/

@Data
@NoArgsConstructor
public class Route {

	private UUID id;

	@JsonIgnore
	private List<RouteEvent> cache = new ArrayList<>();

	@JsonIgnore
	private UUID bookingId;

	@JsonIgnore
	private UUID cargoId;

	private Location origin;
	private Location destination;

	private List<Leg> legList;

	public Route(RouteCreateCommand cmd) {
		// TODO: any invariants check
		UUID routeId = UUID.randomUUID();
		RouteCreatedEvent event =
				new RouteCreatedEvent.Builder()
				.setBookingId(cmd.getBookingId())
				.setCargoId(cmd.getCargoId())
				.setRouteId(routeId.toString())
				.setOrigin(cmd.getOrigin())
				.setDestination(cmd.getDestination())
				.build();
		routeCreated(event);
	}

	public Route routeCreated(RouteCreatedEvent event) {
		this.setLegList(new ArrayList<>());
		this.setId(UUID.fromString(event.getRouteId()));
		this.setBookingId(UUID.fromString(event.getBookingId()));
		this.setCargoId(UUID.fromString(event.getCargoId()));
		this.setOrigin(event.getOrigin());
		this.setDestination(event.getDestination());
		cacheEvent(event);
		return this;
	}

	public UUID addLeg(LegAddCommand cmd) {
		// TODO: any invariants check
		UUID legId = UUID.randomUUID();
		LegAddedEvent event = new LegAddedEvent.Builder()
				.setRouteId(this.getId().toString())
				.setLegId(legId.toString())
				.setStartLocation(cmd.getStartLocation())
				.setEndLocation(cmd.getEndLocation())
				.setTransportationType(cmd.getTransType())
				.build();
		legAdded(event);
		return legId;
	}

	public Route legAdded(LegAddedEvent event) {
		Leg leg = legMember(UUID.fromString(event.getLegId()));
		this.getLegList().add(leg);
		leg.legAdded(event);
		cacheEvent(event);
		return this;
	}

	public Leg legMember(UUID legId) {
		Leg leg = getLegList().stream()
				.filter(l -> l.getId() == legId)
				.findFirst()
				.orElse(new Leg(legId));
		return leg;
	}

	public void cacheEvent(RouteEvent event) {
		cache.add(event);
	}

	public void clearEventCache() {
		this.cache.clear();
	}

	// Event Sourcing Handler (When replaying)
	public Route handleEvent(RouteEvent event) {
		return API.Match(event).of(
				Case( $( instanceOf( RouteCreatedEvent.class ) ), this::routeCreated)
				, Case( $( instanceOf( LegAddedEvent.class ) ), this::legAdded)
		);
	}

}
