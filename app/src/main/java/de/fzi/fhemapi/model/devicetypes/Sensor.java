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
 * Class that represents all kind of sensors
 * @author Can Yumusak
 *
 */
public class Sensor extends Device {

	/**
	 * default constructor	
	 * @param response the server response
	 * @param server the fhem server
	 */
	public Sensor(DeviceResponse response, FHEMServer server) {
		super(response, server);
	}

	/**
	 * returns whether or not the response is of sensor type
	 * @param response server response
	 * @return whether or not the response is of sensor type
	 */
	public static boolean isOfType(DeviceResponse response) {
		if(response.messageCount != 0)
			return true;
		return false;
	}

}
