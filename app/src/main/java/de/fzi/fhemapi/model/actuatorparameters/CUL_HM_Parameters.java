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

import de.fzi.fhemapi.server.constants.FHEMParameters;

public class CUL_HM_Parameters extends ActuatorParameters{

	public static final String TYPE = "CUL_HM";
	
	
	@Override
	public boolean checkParams() {
		if(get(FHEMParameters.DEFINITION).length() != 6 && get(FHEMParameters.DEFINITION).length() != 8 )
			return false;
		return true;
	}
	
	
	/**
	 * default constructor
	 * @param deviceName name of the device
	 * @param hexCode the hex-code of your homematic device; This currently does not work, as HomeMatic devices
	 * act as actuators and sensors at the same time.
	 */
	public CUL_HM_Parameters(String deviceName, String hexCode){
		put(FHEMParameters.NAME, deviceName);
		put(FHEMParameters.DEFINITION, hexCode);
		put(FHEMParameters.TYPE, TYPE);
	}
	
	/**
	 * Get the names of all needed parameters to construct this actuator. Please note
	 * that these should be in the same order as the constructor of the class uses them.
	 * @return the names of all parameters. This is mainly used by the UI.
	 */
	public static String[] getParameterNames() {
		return new String[] {"Name", "Hex Code"};
	}
}
