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
/**
 * This class represnts the Parameters used to build an FS20 Actuator.
 * @author Can Yumusak
 *
 */
public class FS20Parameters extends ActuatorParameters {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8220665895285645185L;
	
	/**
	 * FHEM String used in the "TYPE" Parameter
	 */
	public static final String TYPE = "FS20";

	@Override
	public boolean checkParams() {
		if (get(FHEMParameters.DEFINITION).length() < 6
				|| get(FHEMParameters.DEFINITION).length() > 8)
			return false;
		if (get(FHEMParameters.NAME) == null)
			return false;
		return true;
	}

	/**
	 * Default constructor
	 * @param name the name of the actuator
	 * @param housecode Housecode of the FS20 Actuator - it should be 8 digits
	 * @param address the adress of the fs20 device - this should be 2 digits or 4 on 4-base
	 */
	public FS20Parameters(String name, String housecode, String address) {
		put(FHEMParameters.TYPE, FS20Parameters.TYPE);
		put(FHEMParameters.NAME, name);
		put(FHEMParameters.DEFINITION, housecode + " " + address);
	}
	
	/**
	 * Get the names of all needed parameters to construct this actuator. Please note
	 * that these should be in the same order as the constructor of the class uses them.
	 * @return the names of all parameters. This is mainly used by the UI.
	 */
	public static String[] getParameterNames() {
		return new String[] {"Name", "Housecode", "Adress"};
	}

}
