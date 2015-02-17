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
package de.fzi.fhemapi.server;

import java.util.ArrayList;
import java.util.List;

import de.fzi.fhemapi.model.Script;
import de.fzi.fhemapi.model.server.ScriptResponse;

/**
 * This class manages all scripts on the FHEM Server. 
 * @author Can Yumusak
 *
 */
public class ScriptManager {

	FHEMServer server;
	List<Script> scripts;

	/**
	 * default constructor
	 * @param server the FHEM server
	 */
	protected ScriptManager(FHEMServer server) {
		this.server = server;

		rereadScriptListFromFHEM();
	}

	/**
	 * loads all scripts from the FHEM server and lodas them in this manager
	 */
	public void rereadScriptListFromFHEM() {
		scripts = new ArrayList<Script>();
		List<ScriptResponse> scriptResponses = server.getAllScripts();

		for (ScriptResponse response : scriptResponses)
			scripts.add(new Script(response));
	}

	/**
	 * default toString method
	 */
	public String toString() {
		String returnString = ("********* ScriptManager *******  \n");
		for (Script script : scripts)
			returnString += (script) + "\n";
		returnString += ("********* /ScriptManager ******");
		return returnString;
	}

}
