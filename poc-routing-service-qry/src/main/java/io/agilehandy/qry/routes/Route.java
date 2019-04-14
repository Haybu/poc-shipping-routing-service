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
package io.agilehandy.qry.routes;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.agilehandy.qry.AuditModel;
import io.agilehandy.qry.legs.Leg;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Haytham Mohamed
 **/
@Entity(name = "routes")
@Data
@NoArgsConstructor
public class Route extends AuditModel {

	@Id
	private String routeId;

	private String cargoId;

	private String originOpZone;
	private String originFacility;

	private String destOpZone;
	private String destFacility;

	@OneToMany(
			mappedBy = "route",
			fetch = FetchType.EAGER,
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Leg> legList;
}