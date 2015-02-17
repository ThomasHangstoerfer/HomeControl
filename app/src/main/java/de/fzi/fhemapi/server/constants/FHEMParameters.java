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
 * This class holds constants of the FHEMParmeteres used in the server communication.
 * @author Can Yumusak
 *
 */
public final class FHEMParameters {

	
	private FHEMParameters() {
	};

	public static final String NAME = "NAME";
	public static final String NEWNAME = "NEWNAME";
	public static final String CURRENTSTATE = "STATE";
	public static final String LAST_CHANGED_STATE = "state";
	public static final String DEFINITION = "DEF";
	public static final String TYPE = "TYPE";
	public static final String NUMBER = "NR";
	public static final String EATTRIBUTES = "EATTR";
	public static final String DEVICETYPE = "deviceType";

}
