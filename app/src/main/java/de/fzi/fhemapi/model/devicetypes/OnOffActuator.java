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

import java.util.HashMap;
import java.util.Map;

import de.fzi.fhemapi.model.actuatorparameters.ActuatorParameters;
import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.server.FHEMServer;


/**
 * Class representing an Actuator which is able to be turned on or off
 * @author Can Yumusak
 *
 */
public class OnOffActuator extends Actuator {

	public Boolean state;

	
	/**
	 * default constructor
	 * @param response fhem server response
	 * @param server fhem server
	 */
	public OnOffActuator(DeviceResponse response, FHEMServer server) {
		super(response, server);
	}

	/**
	 * default constructor
	 * @param name device name
	 * @param params actuator parameters
	 * @param server the fhem server
	 */
	public OnOffActuator(String name, ActuatorParameters params,
			FHEMServer server) {
		super(name, params, server);
	}

	/**
	 * returns whether or not the server response is of this type
	 * @param response the server response
	 * @return value
	 */
	public static boolean isOfType(DeviceResponse response) {
		return true;
	}

	@Override
	public String toString() {
		String dev = super.toString();
		if (state != null)
			dev += " State: " + state;
		return dev;
	}

	/**
	 * turns on this device
	 */
	public void turnOn() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("NAME", getName());
		params.put("STATE", "on");
		if (getServer().setDevice(getName(), params).isTrue()) {
			this.state = true;
		}
	}

	/**
	 * turns off the device
	 */
	public void turnOff() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("NAME", getName());
		params.put("STATE", "off");
		if (getServer().setDevice(getName(), params).isTrue()) {
			this.state = false;
		}
	}

	/**
	 * Return null if the Object is not an Instance of OnOffActuator, the casted
	 * object otherwise
	 * 
	 * @param dev
	 *            Device to cast
	 * @return The Device as OnOffActuator
	 */
	public static OnOffActuator castObject(Device dev) {
		if (OnOffActuator.class.isInstance(dev))
			return (OnOffActuator) dev;
		return null;
	}
	
	@Override
	protected void update(DeviceResponse response) {
		super.update(response);
		
		if (response.state != null) {
			if (response.state.equals("on"))
				state = true;
			else if (response.state.equals("off"))
				state = false;
		}
	}

}
