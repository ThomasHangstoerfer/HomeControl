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

import java.util.ArrayList;
import java.util.List;

import de.fzi.fhemapi.model.server.MessageResponse;
import de.fzi.fhemapi.model.server.StructureResponse;

/**
 * This class represents a structure on the server.
 * @author Can Yumusak
 *
 */
public class Structure {
	
	public String name;
	public String type;
	public List<String> deviceNames;

	/**
	 * default constructor
	 * @param sr the server response this structure should be built from
	 */
	public Structure(StructureResponse sr) {
		this.name = sr.name;
		this.type = sr.type;
		deviceNames = new ArrayList<String>();
		for (MessageResponse mr : sr.content){
			deviceNames.add(mr.message);
		}
	}
	
	/**
	 * default constructor
	 * @param name the name of the structure
	 * @param type the type of the structure
	 * @param deviceNames the names of the devices in this structure
	 */
	public Structure(String name, String type, List<String> deviceNames){
		this.name = name;
		this.type = type;
		this.deviceNames = deviceNames;
	}

	/**
	 * default toString method
	 */
	@Override
	public String toString() {
		String devices = "";
		for (String names : deviceNames)
			devices += names + " ";
		return ("*** STRUCTURE: Name: " + name + " Type: " + type
				+ " Devices: " + devices);
	}

}
