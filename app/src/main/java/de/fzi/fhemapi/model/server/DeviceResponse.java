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
package de.fzi.fhemapi.model.server;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import de.fzi.fhemapi.model.server.subelements.EAttributes;
import de.fzi.fhemapi.model.server.subelements.Reading;
/**
 * This class represents the response from the server to a device request
 * @author Can
 *
 */
public class DeviceResponse extends ResponseObject {

	@SerializedName("NAME")
	public String name;
	@SerializedName("DEF")
	public String def;
	
	@SerializedName("READINGS")
	public Reading lastState;
	@SerializedName("TYPE")
	public String type;
	@SerializedName("NR")
	public String nr;
	@SerializedName("ATTRIBUTES")
	public String attributes;
	@SerializedName("STATE")
	public String state;
	@SerializedName("SETS")
	public String sets;
	@SerializedName("guid")
	public String guid;
	@SerializedName("EATTR")
	public EAttributes eAttributes;
    @SerializedName("MSGCNT")
    public int messageCount;
    @SerializedName("device")
    public String device;


	/**
	 * Default constructor
	 * @param name
	 */
	public DeviceResponse(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ("DEVICE **** Name: " + name + " Def: " + def + " Type: " + type + eAttributes);
	}

	/**
	 * prints a List of DeviceResponse to the console
	 * @param list
	 */
	public static void printDevices(List<DeviceResponse> list) {
		System.out.println("******** DEVICE LIST *********");

		for (DeviceResponse response : list) {
			System.out.println(response);
		}

		System.out.println("******** LIST EX *********");
	}
}
