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
package de.fzi.fhemapi.server.constants;

/**
 * This class holds all methodnames used by the API between perl and java
 * @author Can Yumusak
 *
 */
public final class MethodNames {

	private MethodNames() {
	};

	/**
	 * DEVICE FUNCTIONS
	 */
	public static final String GET_ALL_DEVICES = "getAllDevices";
	public static final String GET_DEVICE = "getDevice";
	public static final String SET_DEVICE = "SETDEVICE";
	public static final String NEW_DEVICE = "NEWDEVICE";
	public static final String DEL_DEVICE = "DELDEVICE";
	public static final String GET_NONDEFINED_DEVICES = "GETNONDEFINEDDEVICES";

	/**
	 * STRUCTURE FUNCTIONS
	 */

	public static final String GETSTRUCTURE = "GETSTRUCTURE";
	public static final String GETALLSTRUCTURES = "GETALLSTRUCTURES";
	public static final String NEWSTRUCTURE = "NEWSTRUCTURE";
	public static final String DELETESTRUCTURE = "DELETESTRUCTURE";
	public static final String ADDDEVICEINSTRUCTURE = "ADDDEVICEINSTRUCTURE";
	public static final String DELETEDEVICEINSTRUCTURE = "DELETEDEVICEINSTRUCTURE";
	public static final String RENEWDEVICEINSTRUCTURE = "RENEWDEVICEINSTRUCTURE";

	/**
	 * LOG FUNCTIONS
	 */

	public static final String GET_AVAILABLE_LOGFILES = "GETAVAILABLELOGFILES";
	public static final String GETLOGFROMDEVICE = "GETLOGFROMDEVICE";
	public static final String GETSVGFROMDEVICE = "GETSVGFROMDEVICE";

	/**
	 * SCRIPT FUNCTIONS
	 */

	public static final String GETALLSCRIPTS = "GETALLSCRIPTS";
	public static final String GETSCRIPT = "GETSCRIPT";
	public static final String ADDSCRIPT = "ADDSCRIPT";
	public static final String DELETESCRIPT = "DELETESCRIPT";
	public static final String UPDATESCRIPT = "UPDATESCRIPT";

	/**
	 * COMMAND FUNCTIONS
	 */

	public static final String SAVE = "SAVE";
	public static final String GETFHEMFILE = "GETFHEMFILE";
	public static final String SETFHEMCFG = "SETFHEMCFG";
	public static final String UPDATEFHEM = "UPDATEFHEM";
	public static final String REREADCFG = "REREADCFG";
	public static final String HELP = "HELP";
	public static final String GETVERSION = "GETVERSION";
}
