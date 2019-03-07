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


package io.agilehandy.common.api.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.agilehandy.common.api.events.legs.LegAddedEvent;
import io.agilehandy.common.api.events.routes.RouteCreatedEvent;
import io.agilehandy.common.api.events.routes.RouteStatusChangedEvent;

/**
 * @author Haytham Mohamed
 **/

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(name = "ROUTE_CREATED", value = RouteCreatedEvent.class),
		@JsonSubTypes.Type(name = "ROUTE_STATUS_CHANGED", value = RouteStatusChangedEvent.class),
		@JsonSubTypes.Type(name = "LEG_ADDED", value = LegAddedEvent.class)
})

public interface RouteEvent {

	public String getType();
	public String getRouteId();

}
