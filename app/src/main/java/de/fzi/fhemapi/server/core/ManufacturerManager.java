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
package de.fzi.fhemapi.server.core;

import java.util.ArrayList;
import java.util.List;

import de.fzi.fhemapi.model.actuatorparameters.ActuatorParameters;
import de.fzi.fhemapi.model.actuatorparameters.CUL_HM_Parameters;
import de.fzi.fhemapi.model.actuatorparameters.CUL_MAX_Parameters;
import de.fzi.fhemapi.model.actuatorparameters.FS20Parameters;
import de.fzi.fhemapi.model.actuatorparameters.HMLANParameters;
import de.fzi.fhemapi.model.actuatorparameters.KNXParameters;

/**
 * Class managing the manufacturer parameters. One should register "ActuatorParameters.class" instances here.
 * @author Can Yumusak
 *
 */
public class ManufacturerManager {
	
	private static ManufacturerManager instance;
	List<Class<? extends ActuatorParameters>> classes;
	
	private ManufacturerManager(){
		classes = new ArrayList<Class<? extends ActuatorParameters>>();
		initManufacturers();
	}

	private void initManufacturers() {
		classes.add(CUL_HM_Parameters.class);
		classes.add(CUL_MAX_Parameters.class);
		classes.add(FS20Parameters.class);
		classes.add(HMLANParameters.class);
		classes.add(KNXParameters.class);
	}
	

	/**
	 * Registers a manufacturer class on this manager.
	 * @param clazz The class to register
	 */
	public static void registerManufacturer(Class<? extends ActuatorParameters> clazz){
		getInstance().classes.add(clazz);
	}
	
	/**
	 * Returns the instance of this singleton class
	 * @return this instance
	 */
	public static ManufacturerManager getInstance(){
		if(instance == null)
			instance = new ManufacturerManager();
		return instance;
	}
	
	/**
	 * Get all the classes registered in this manager.
	 */
	public static List<Class<? extends ActuatorParameters>> getClasses(){
		return getInstance().classes;
	}
	
	/**
	 * Get all the classes simple names registered in this manager.
	 */
	public static String[] getClassNames(){
		String[] classNames = new String[getInstance().classes.size()];
		for(int i = 0; i < classNames.length; i++){
			classNames[i] = getInstance().classes.get(i).getSimpleName();
		}
		return classNames;
	}
}
