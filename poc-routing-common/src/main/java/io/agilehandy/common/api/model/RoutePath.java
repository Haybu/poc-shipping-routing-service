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
package io.agilehandy.common.api.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

/**
 * @author Haytham Mohamed
 **/
public class RoutePath {

	Location location = null;
	TransportationType type;
	RoutePath next = null;
	RoutePath before = null;

	public RoutePath() {}

	public Location getLocation() {
		return location;
	}

	private void setLocation(Location location) {
		this.location = location;
	}

	private RoutePath getNext() {
		return next;
	}

	private void setNext(RoutePath next) {
		this.next = next;
	}

	private RoutePath getBefore() {
		return before;
	}

	private void setBefore(RoutePath before) {
		this.before = before;
	}

	private boolean isFirst() {
		return before == null;
	}

	private boolean isLast() {
		return next == null;
	}

	private boolean isAlone() {
		return isFirst() && isLast() && location == null;
	}

	TransportationType getType() {
		return type;
	}

	void setType(TransportationType type) {
		this.type = type;
	}

	public void add(Location location, TransportationType type) {
		RoutePath lastPath = this.last();
		if (isAlone()) {
			lastPath.setLocation(location);
			last().setType(type);
		} else {
			RoutePath routePath = new RoutePath();
			routePath.setLocation(location);
			routePath.setType(type);
			routePath.setBefore(lastPath);
			lastPath.setNext(routePath);
		}
	}

	public List<Location> locations() {
		RoutePath path = this;
		List<Location> locations = new ArrayList<>();
		while(!path.isLast()) {
			locations.add(path.getLocation());
			path = path.getNext();
		}
		return locations;
	}

	private RoutePath last() {
		RoutePath path = this;
		while(!path.isLast()) {
			path = path.getNext();
		}
		return path;
	}

	public static class Builder {
		private RoutePath pathToBuild;

		public Builder() {
			pathToBuild = new RoutePath();
		}

		public RoutePath build() {
			RoutePath eventBuilt = pathToBuild;
			pathToBuild = new RoutePath();
			return eventBuilt;
		}

		public Builder addLocation(Location location, TransportationType type) {
			pathToBuild.add(location, type);
			return this;
		}
	}

	public List<RouteLeg> gtPathLegs() {
		Assert.isTrue(!this.isAlone(), "please provide a complete route path");
		RoutePath path = this;
		List<RouteLeg> legs = new ArrayList<>();
		while (!path.isLast()) {
			RouteLeg leg = new RouteLeg();
			leg.setTransType(path.getType());
			leg.setStartLocation(path.getLocation());
			leg.setEndLocation(path.getNext().getLocation());
		}
		return legs;
	}
}
