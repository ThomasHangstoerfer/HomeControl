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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The ConfigurationManager helps interacting in any kind with the configuration file. 
 * 
 * @author Can Yumusak
 *
 */
public class ConfigurationManager {

	FHEMServer server;
	String configFile;
	
	/**
	 * Default Constructor
	 * @param server the FHEM Server
	 */
	public ConfigurationManager(FHEMServer server) {
		this.server = server;
		update();
	}
	
	/**
	 * This method updates the content of this manager from the server.
	 */
	public void update(){
		configFile = server.getFHEMCfg();
	}
	
	/**
	 * Checks if the configuration file contains a text. This also accepts regular expressions.
	 * @param text the string to check 
	 * @return true if it is in the configuration-file
	 */
	public boolean contains(String text){
		Pattern pattern = Pattern.compile(text);
		Matcher m = pattern.matcher(configFile);
		
		if(m.find())
			return true;
		return false;
	}
	
	/**
	 * injects the command to add a user attribute called "deviceType" in the FHEM-Configuration,
	 * if not already existent. This enables the user of this API to force some device as a device type.
	 */
	public void defineSensorTypeAttr(){
		String defineString = "attr global userattr deviceType";
		if(!contains(defineString))
			appendString(defineString, true);
	}	
	
	/**
	 * Appends a string to the configuration-file. This does not check if it already exists there.
	 * One should do that manually beforehand. This method directly uploads this, not changing it locally,
	 * if reread is not set.
	 * @param string The string to append
	 * @param rereadCfg whether or not this manager should update itself after appending a string, leading to 
	 * a newest config-file.
	 */
	public void appendString(String string, boolean rereadCfg){
		configFile += "\n"+string;
		server.setFHEMCfg(configFile);
		if(rereadCfg)
			server.rereadConfiguration();
	}
	
	/**
	 * Deletes a String from the configuration file. It also accepts regular expression, but deletes everything from the beginning
	 * of the string to the end. This method directly uploads this, not changing it locally, if reread is not set.
	 * @param string 
	 * @param rereadCfg
	 */
	public void deleteString(String string, boolean rereadCfg){
		Pattern pattern = Pattern.compile(string);
		Matcher m = pattern.matcher(configFile);
		if(m.find()){
			String newConfigFile = "";
			newConfigFile = configFile.substring(0, m.start())+configFile.substring(m.end(), configFile.length());
			configFile = server.setFHEMCfg(newConfigFile) ? newConfigFile : configFile; 
		}
		if(rereadCfg)
			update();
	}
	
	/**
	 * Returns the local version of the configuration file.
	 * @return the configuration file.
	 */
	public String getConfigFile() {
		return configFile;
	}
	
	/**
	 * Switches the state of the FHEM autocreate mode on the server and directly rereads the result. If autocreate
	 * one wish to set attributes to autocreate, these should be set manually. This only defines the autocreate 
	 * if it is not present yet.
	 * @param state whether or not autocreate should be active.
	 */
	public void setAutocreate(boolean state){
		update();
		if(state){
			setAutoCreateOn();
		}else{
			setAutoCreateOff();
		}
		update();
		server.rereadConfiguration();
	}
	
	private void setAutoCreateOff(){
		Pattern pattern = Pattern.compile("\ndefine \\S+ autocreate");
		String newConfig = configFile;
		Matcher m = pattern.matcher(newConfig);

		if(m.find()){
			newConfig = newConfig.substring(0, m.start()+1)+"#"+newConfig.substring(m.start()+1, newConfig.length());
//			System.out.println("inserted new: "+newConfig.substring(m.start()-2 , m.end()+2));
		}
		
//		pattern = Pattern.compile("attr autocreate");
//		m = pattern.matcher(newConfig);
//		for(int i = 0; m.find(); i++){
//			newConfig = newConfig.substring(0, m.start()-i)+"#"+newConfig.substring(m.start()-i, newConfig.length());
//			System.out.println("Found attr autocreate: "+newConfig.substring(m.start() , m.end()));
//		}
		server.setFHEMCfg(newConfig);
	}
	
	private void setAutoCreateOn(){
		Pattern pattern = Pattern.compile("[#]+define \\S+ autocreate");
		String newConfig = configFile;
		Matcher m = pattern.matcher(newConfig);
		if(m.find()){
			int i = 0;
			while(newConfig.charAt(m.start()+i) == '#')
				i++;

			newConfig = newConfig.substring(0, m.start())+newConfig.substring(m.start()+i, newConfig.length());
//			pattern = Pattern.compile("attr autocreate");
//			m = pattern.matcher(newConfig);
//			while(m.find()){
//				newConfig = configFile.substring(0, m.start())+newConfig.substring(m.start()+1, newConfig.length());
//				System.out.println("Found #attr autocreate: "+newConfig.substring(m.start() , m.end()));
//			}
//			server.setFHEMCfg(newConfig);
		}else{
			appendString("define autocreate autocreate", false);
		}
		server.setFHEMCfg(newConfig);
	}

}
