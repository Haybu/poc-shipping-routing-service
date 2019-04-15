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


package io.agilehandy.pubsub;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Haytham Mohamed
 **/

public interface RouteEventChannels {

	String ROUTE_STREAM_IN = "input";
	String ROUTES_EVENTS_IN = "routes";
	String ROUTE_EVENTS_OUT = "output";
	String CARGOS_EVENTS_IN = "cargos";

	@Output(ROUTE_EVENTS_OUT)
	MessageChannel output();

	@Input(CARGOS_EVENTS_IN)
	SubscribableChannel cargos();

	@Input(ROUTES_EVENTS_IN)
	SubscribableChannel routes();

	@Input(ROUTE_STREAM_IN)
	KStream<?, ?> input();
}
