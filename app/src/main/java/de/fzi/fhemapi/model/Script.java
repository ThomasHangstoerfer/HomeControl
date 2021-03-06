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
package de.fzi.fhemapi.model;

import de.fzi.fhemapi.model.server.ScriptResponse;
/**
 * This class respresents the java-object equivalent of a FHEM-Script. 
 * It is built by the ScriptManager in fucntion of the given ScriptResponse.
 * @author Can Yumusak
 *
 */
public class Script {

	String name;
	String com;
	String regexp;

	/**
	 * Standard constructor
	 * @param response FHEM Server ScriptResponse
	 */
	public Script(ScriptResponse response) {
		name = response.name;
		com = response.com;
		regexp = response.regexp;
	}

	/**
	 * Default toString() method
	 */
	@Override
	public String toString() {
		return "*** SCRIPT Name: " + name + " Com: " + com
				+ " Reg. Expression: " + regexp;
	}
}
