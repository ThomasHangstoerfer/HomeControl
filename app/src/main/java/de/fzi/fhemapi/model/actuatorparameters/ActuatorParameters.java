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
package de.fzi.fhemapi.model.actuatorparameters;

import java.util.HashMap;
import java.util.Map;

/**
 * This abstract class represents the parameters needed in FHEM to build a new actuator.
 * It is used by the DeviceManager to create a new Actuator on the server.
 * @author Can Yumusak
 *
 */
public abstract class ActuatorParameters  {

	private Map<String, String> params;

	/**
	 * Checks the validity of the current parameters.
	 * @return whether or not the parameters are valid
	 */
	public abstract boolean checkParams();
	
	/**
	 * Put a new parameter to the list. Look up FHEMParameters.class for more information.
	 * @param parameterName the name of the parameter
	 * @param value value of the parameter
	 */
	public void put(String parameterName, String value){
		if(params == null)
			params = new HashMap<String, String>();
		
		params.put(parameterName, value);
	}
	
	/**
	 * Returns a given parameter
	 * @param parameterName the name of the parameter
	 * @return the value
	 */
	public String get(String parameterName){
		return params.get(parameterName);
	}
	
	/**
	 * Returns the parameters as a map.
	 * @return a HashMap
	 */
	public Map<String, String> asMap(){
		return params;
	}
	
	/**
	 * Get the names of all needed parameters to construct this actuator. Please note
	 * that these should be in the same order as the constructor of the class uses them.
	 * @return the names of all parameters. This is mainly used by the UI.
	 */
	public static String[] getParameterNames(){
		return new String[] {};
	}
}
