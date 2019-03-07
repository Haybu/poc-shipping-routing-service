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


package io.agilehandy.common.api.events.legs;

import io.agilehandy.common.api.events.EventTypes;
import io.agilehandy.common.api.events.LegBaseEvent;
import io.agilehandy.common.api.events.RouteEvent;
import io.agilehandy.common.api.model.Location;
import io.agilehandy.common.api.model.TransportationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Haytham Mohamed
 **/

@Data
@NoArgsConstructor
public class LegAddedEvent extends LegBaseEvent implements RouteEvent {

	private Location start;
	private Location end;
	private TransportationType transType;

	public static class Builder {
		private LegAddedEvent eventToBuild;

		public Builder() {
			eventToBuild = new LegAddedEvent();
		}

		public LegAddedEvent build() {
			eventToBuild.setOccurredOn(LocalDateTime.now());
			eventToBuild.setType(EventTypes.LEG_ADDED);
			LegAddedEvent eventBuilt = eventToBuild;
			eventToBuild = new LegAddedEvent();
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

		public Builder setLegId(String legId) {
			eventToBuild.setLegId(legId);
			return this;
		}

		public Builder setStartLocation(String opZone, String facility) {
			eventToBuild.setStart(new Location(opZone, facility));
			return this;
		}

		public Builder setStartLocation(Location location) {
			eventToBuild.setStart(location);
			return this;
		}

		public Builder setEndLocation(String opZone, String facility) {
			eventToBuild.setEnd(new Location(opZone, facility));
			return this;
		}

		public Builder setEndLocation(Location location) {
			eventToBuild.setEnd(location);
			return this;
		}

		public Builder setTransportationType(TransportationType type) {
			eventToBuild.setTransType(type);
			return this;
		}
	}

}
