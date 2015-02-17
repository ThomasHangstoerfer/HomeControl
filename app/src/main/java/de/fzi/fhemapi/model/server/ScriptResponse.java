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

import com.google.gson.annotations.SerializedName;

/**
 * This class represents the response from the server to a script request
 * @author Can Yumusak
 *
 */
public class ScriptResponse extends ResponseObject {

	@SerializedName("NAME")
	public String name;
	@SerializedName("COM")
	public String com;
	@SerializedName("DEF")
	public String def;
	@SerializedName("TYPE")
	public String type;
	@SerializedName("REGEXP")
	public String regexp;
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
	@SerializedName("NTFY_ORDER")
	public String ntfyOrder;

	@Override
	public String toString() {
		return "***SCRIPT Name: " + name + " COM: " + com + " REGEXP: "
				+ regexp + " NTFY_ORDER: " + ntfyOrder;
	}

}
