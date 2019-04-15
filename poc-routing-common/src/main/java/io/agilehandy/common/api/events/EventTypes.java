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


package io.agilehandy.common.api.events;

/**
 * @author Haytham Mohamed
 **/

public interface EventTypes {

	String UNKNOWN_EVENT = "UNKNOWN";

	String ROUTE_CREATED = "ROUTE_CREATED";
	String ROUTE_CHANGED = "ROUTE_CHANGED";
	String ROUTE_CANCELED = "ROUTE_CANCELED";
	String ROUTE_STATUS_CHANGED = "ROUTE_STATUS_CHANGED";

	String LEG_ADDED = "LEG_ADDED";
	String LEG_UPDATED = "LEG_UPDATED";

}
