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
package de.fzi.fhemapi.model.server.subelements;

import com.google.gson.annotations.SerializedName;

import de.fzi.fhemapi.server.constants.FHEMParameters;

/**
 * This class helps deserializing the "ATTRIBUTES" attribute of a serialized device from Perl. It is not used yet but should
 * give the user the ability to used it in case of need.
 * @author Can Yumusak
 *
 */
public class EAttributes {

	public String configfile;
	public String verbose;
	public String userattr;
	public String version;
	public String modpath;
	public String nofork;
	public String autoload_undefinded_devices;
	public String motd;
	public String statefile;
	public String logfile;
	public String room;
	
	@SerializedName(FHEMParameters.DEVICETYPE)
	public String deviceType;

	@Override
	public String toString() {
		return ("      (EATTR) Config File: " + configfile + " verbose " + verbose);
	}

}
