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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.agilehandy.common.api.model.Location;
import io.agilehandy.common.api.model.RouteLeg;
import lombok.Data;

/**
 * @author Haytham Mohamed
 **/

@Data
public class RouteCreateCommand implements Serializable {

	private String bookingId;
	private String cargoId;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime occurredOn;

	private Location origin;
	private Location destination;

	List<RouteLeg> legs;

	public RouteCreateCommand() {
		this.occurredOn = LocalDateTime.now();
	}

	public static class Builder {
		private RouteCreateCommand commandToBuild;

		public Builder() {
			commandToBuild = new RouteCreateCommand();
		}

		public RouteCreateCommand build() {
			RouteCreateCommand commandBuilt = commandToBuild;
			commandToBuild = new RouteCreateCommand();
			return commandBuilt;
		}

		public Builder setBookingId(String bookingId) {
			commandToBuild.setBookingId(bookingId);
			return this;
		}

		public Builder setCargoId(String cargoId) {
			commandToBuild.setCargoId(cargoId);
			return this;
		}

		public Builder setOrigin(Location origin) {
			commandToBuild.setOrigin(origin);
			return this;
		}

		public Builder setLegs(List<RouteLeg> legs) {
			commandToBuild.setLegs(legs);
			return this;
		}

		public Builder setDestination(Location destination) {
			commandToBuild.setDestination(destination);
			return this;
		}
	}

}
