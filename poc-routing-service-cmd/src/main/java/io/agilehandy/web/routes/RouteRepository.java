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


package io.agilehandy.web.routes;

import io.agilehandy.pubsub.RouteEventPubSub;
import io.agilehandy.routes.Route;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haytham Mohamed
 **/

@Component
@Slf4j
public class RouteRepository {

	private final RouteEventPubSub pubsub;
	private final QueryableStoreRegistry queryableStoreRegistry;

	public RouteRepository(RouteEventPubSub pubsub
			, QueryableStoreRegistry queryableStoreRegistry) {
		this.pubsub = pubsub;
		this.queryableStoreRegistry = queryableStoreRegistry;
	}

	public void save(Route route) {
		route.getCache().stream().forEach(e -> {
			log.debug(String.format("publishing [routeId: %s, eventType: %s]"
					, e.getRouteId(), e.getType()));
			pubsub.publish(e);
		});
		route.clearEventCache();
	}

	public Route findById(String id) {
		ReadOnlyKeyValueStore<String, Route> queryStore =
				queryableStoreRegistry.getQueryableStoreType(RouteEventPubSub.EVENTS_SNAPSHOT
						, QueryableStoreTypes.<String, Route>keyValueStore());
		return queryStore.get(id);
	}

	public List<Route> findAll() {
		ReadOnlyKeyValueStore<String, Route> queryStore =
				queryableStoreRegistry.getQueryableStoreType(RouteEventPubSub.EVENTS_SNAPSHOT
						, QueryableStoreTypes.<String, Route>keyValueStore());
		KeyValueIterator<String, Route> all = queryStore.all();
		List<Route> routings = new ArrayList<>();
		while(all.hasNext()) {
			routings.add(all.next().value);
		}
		return routings;
	}
}
