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


package io.agilehandy.common.api.events.routes;

import io.agilehandy.common.api.events.EventTypes;
import io.agilehandy.common.api.events.RouteBaseEvent;
import io.agilehandy.common.api.events.RouteEvent;
import io.agilehandy.common.api.model.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Haytham Mohamed
 **/

@Data
@NoArgsConstructor
public class RouteCreatedEvent extends RouteBaseEvent implements RouteEvent {

	private Location origin;
	private Location destination;

	public static class Builder {
		private RouteCreatedEvent eventToBuild;

		public Builder() {
			eventToBuild = new RouteCreatedEvent();
		}

		public RouteCreatedEvent build() {
			eventToBuild.setOccurredOn(LocalDateTime.now());
			eventToBuild.setType(EventTypes.ROUTE_CREATED);
			RouteCreatedEvent eventBuilt = eventToBuild;
			eventToBuild = new RouteCreatedEvent();
			return eventBuilt;
		}

		public Builder setBookingId(String bookingId) {
			eventToBuild.setBookingId(bookingId);
			return this;
		}

		public Builder setCargoId(String cargoId) {
			eventToBuild.setCargoId(cargoId);
			return this;
		}

		public Builder setRouteId(String routeId) {
			eventToBuild.setRouteId(routeId);
			return this;
		}

		public Builder setOrigin(String opZone, String facility) {
			eventToBuild.setOrigin(new Location(opZone, facility));
			return this;
		}

		public Builder setOrigin(Location location) {
			eventToBuild.setOrigin(location);
			return this;
		}

		public Builder setDestination(String opZone, String facility) {
			eventToBuild.setDestination(new Location(opZone, facility));
			return this;
		}

		public Builder setDestination(Location location) {
			eventToBuild.setDestination(location);
			return this;
		}
	}

}
