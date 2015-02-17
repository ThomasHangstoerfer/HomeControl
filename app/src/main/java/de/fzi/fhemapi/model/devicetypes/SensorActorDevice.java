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

import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.server.FHEMServer;

/**
 * Class that represents all kind of combination of sensors and actors. To this moment
 * this concept could not be finalized. The idea is to hide the intern logic of two
 * devices with setters/getters.
 * @author Can Yumusak
 *
 */
public class SensorActorDevice extends Device{

	public static final String TAG = "SENSORACTORDEVICE";

	private Sensor sensorDevice;
	private Actuator actuatorDevice;

	/**
	 * default constructor
	 * @param response the server response to construct this.
	 * @param server the FHEM server.
	 */
	public SensorActorDevice(DeviceResponse response, FHEMServer server) {
		super(response, server);
		this.sensorDevice = new Sensor(response, server);
		this.actuatorDevice = new Actuator(response, server);
	}
	
	
	
}
