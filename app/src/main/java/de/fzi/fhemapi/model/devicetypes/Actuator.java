/*******************************************************************************
 * Copyright 2007-2014 FZI, http://www.fzi.de
 *                 Forschungszentrum Informatik - Information Process Engineering (IPE)
 *  
 *                 See the NOTICE file distributed with this work for additional
 *                 information regarding copyright ownership
 *                
 *                 Licensed under the Apache License, Version 2.0 (the "License");
 *                 you may not use this file except in compliance with the License.
 *                 You may obtain a copy of the License at
 *                
 *                   http://www.apache.org/licenses/LICENSE-2.0
 *                
 *                 Unless required by applicable law or agreed to in writing, software
 *                 distributed under the License is distributed on an "AS IS" BASIS,
 *                 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *                 See the License for the specific language governing permissions and
 *                 limitations under the License.
 * @author tzentek - <a href="mailto:zentek@fzi.de">Tom Zentek</a>
 * @author cyumusak - <a href="mailto:canyumusak@gmail.com">Can Yumusak</a>
 ******************************************************************************/
package de.fzi.fhemapi.model.devicetypes;

import java.util.Map;

import de.fzi.fhemapi.model.actuatorparameters.ActuatorParameters;
import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.server.FHEMServer;
import de.fzi.fhemapi.server.constants.FHEMParameters;
/**
 * Class representing an Actuator
 * @author Can Yumusak
 *
 */
public class Actuator extends Device {

	Map<String, String> params;

	/**
	 * default constructor
	 * @param response server response
	 * @param server server
	 */
	public Actuator(DeviceResponse response, FHEMServer server) {
		super(response, server);
		update(response);

	}

	/**
	 * default constructor
	 * @param name name of the device
	 * @param params 
	 * @param server
	 */
	public Actuator(String name, ActuatorParameters params, FHEMServer server) {
		super(name, params.get(FHEMParameters.TYPE), params
				.get(FHEMParameters.DEFINITION), server);
	}
	
	/**
	 * Returns whether or not the DeviceResponse instance is of this type
	 * @param response the given server-DeviceResponse
	 * @return the result
	 */
	public static boolean isOfType(DeviceResponse response) {
		return false;
	}
}
