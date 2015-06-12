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

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import de.bitcoder.homectrl.LCARSConfig;
import de.fzi.fhemapi.model.actuatorparameters.ActuatorParameters;
import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.model.server.LogFileResponse;
import de.fzi.fhemapi.model.server.MessageResponse;
import de.fzi.fhemapi.model.server.ScriptResponse;
import de.fzi.fhemapi.model.server.StructureResponse;
import de.fzi.fhemapi.server.constants.MethodNames;

/**
 * This class does the most work in the communication to the FHEMServer. Instantiate this class first, to use all features. After having
 * this server, it is strongly recommended to act on the base of the Managers.
 * @author Can
 *
 */
@SuppressWarnings("unchecked")
public class FHEMServer {

	private FHEMConnection connection;

	private DeviceManager deviceManager;
	private StructureManager structureManager;
	private LogFileManager logFileManager;
	private ScriptManager scriptManager;
	private ConfigurationManager configManager;


	private static FHEMServer fhemServer = null;

	public static FHEMServer getInstance() throws  java.net.ConnectException
	{
		if ( fhemServer == null )
		{
			fhemServer = new FHEMServer(LCARSConfig.serverIp, LCARSConfig.serverPort);
		}
		return fhemServer;
	}

	/**
	 * default constructor
	 * @param serverIP
	 * @param port
	 * @throws java.net.ConnectException
	 */
	public FHEMServer(String serverIP, int port) throws ConnectException {
		try{
		connection = new FHEMConnection(serverIP, port);
		getConfigManager().defineSensorTypeAttr();
		}catch(Exception e){
            e.printStackTrace();

            throw new ConnectException("Could not find FHEMServer at the IP "+serverIP+":"+port);
		}
	}

	/**
	 * parses the response from server on a getDevice request with the given name
	 * @param name deviceName
	 * @return the response of the server
	 */
	public DeviceResponse getDevice(String name) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("NAME", name);

		return (DeviceResponse) connection.getSingleObject(
				MethodNames.GET_DEVICE, params, DeviceResponse.class);
	}

	/**
	 * returns a list of DeviceResponse.java class, with all the devices available on the server
	 * @return a list of deviceResponse from server
	 */
	public List<DeviceResponse> getAllDevices() {
		return (List<DeviceResponse>) connection.getList(
				MethodNames.GET_ALL_DEVICES,
				new TypeToken<List<DeviceResponse>>() {
				}.getType());
	}

	/**
	 * deletes a device with the given name
	 * @param name the name of the device
	 * @return the response from server
	 */
	public MessageResponse deleteDevice(String name) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("NAME", name);

		return connection.executeMethod(MethodNames.DEL_DEVICE, params);
	}

	/**
	 * creates a new device with the given values
	 * @param name the name of the device
	 * @param devType the type of the device
	 * @param params needed parameters.. mostly dependent of the device type
	 * @return the server response
	 */
	public MessageResponse newDevice(String name, String devType,
			Map<String, String> params) {
		params.put("NAME", name);
		params.put("TYPE", devType);
		return connection.executeMethod(MethodNames.NEW_DEVICE, params);
	}

	/**
	 * creates a new device on the server with the given ActuatorParameters
	 * @param params the params of the device
	 * @return the server response
	 */
	public MessageResponse newDevice(ActuatorParameters params) {
		return connection.executeMethod(MethodNames.NEW_DEVICE, params.asMap());
	}
	
	/**
	 * creates a new device on the server in dependence of the given parameters as map
	 * @param params parameters of the device
	 * @return the server response
	 */
	public MessageResponse newDevice(Map<String, String> params) {
		return connection.executeMethod(MethodNames.NEW_DEVICE, params);
	}

	/**
	 * changes attributes of the device on the server. Can be used to turn actors on for example.
	 * @param name the device name
	 * @param params the attributes to be changed with their new values
	 * @return the server response
	 */
	public MessageResponse setDevice(String name, Map<String, String> params) {
		params.put("NAME", name);

		return connection.executeMethod(MethodNames.SET_DEVICE, params);
	}

	/**
	 * Get all nondefined devices on the server
	 * @return a list of deviceresponses
	 */
	public List<DeviceResponse> getNondefinedDevices() {
		return (List<DeviceResponse>) connection.getList(
				MethodNames.GET_NONDEFINED_DEVICES,
				new TypeToken<List<DeviceResponse>>() {
				}.getType());
	}

	// ******** LOG FUNCTIONS ******

	/**
	 * Returns a list of LogFileResponse from Server with all available log files. This should be 
	 * @return a list of all the response from the server to this request (logfiles)
	 */
	public List<LogFileResponse> getAvailableLogFiles() {
		return (List<LogFileResponse>) connection.getList(
				MethodNames.GET_AVAILABLE_LOGFILES,
				new TypeToken<List<LogFileResponse>>() {
				}.getType());
	}

	/**
	 * Returns a LogFileResponse of a specific device.
	 * @param name the device name
	 * @param parameters parameters (e.g. starting from a date, from a month etc)
	 * @return the logFileResponse
	 */
	public LogFileResponse getLogFromDevice(String name,
			Map<String, String> parameters) {
		if (parameters == null)
			parameters = new HashMap<String, String>();
		parameters.put("NAME", name);

		return (LogFileResponse) connection
				.getSingleObject(MethodNames.GETLOGFROMDEVICE, parameters,
						LogFileResponse.class);
	}

	/**
	 * Returns a LogFileResponse of a specific device.
	 * @param name the device name
	 * @return the logFileResponse
	 */
	public LogFileResponse getLogFromDevice(String name) {
		return getLogFromDevice(name, null);
	}

	// ******** STRUCTURE FUNCTIONS ******

	/**
	 * Returns all structures stored in FHEM	
	 * @return a List of StructureResponses from the server
	 */
	public List<StructureResponse> getAllStructures() {
		return (List<StructureResponse>) connection.getList(
				MethodNames.GETALLSTRUCTURES,
				new TypeToken<List<StructureResponse>>() {
				}.getType());
	}

	/**
	 * Returns the structure with the given name
	 * @param name the server response to this request
	 * @return the server response to this request
	 */
	public StructureResponse getStructure(String name) {
		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("NAME", name);
		return (StructureResponse) connection.getSingleObject(
				MethodNames.GETSTRUCTURE, parameters, StructureResponse.class);
	}

	/**
	 * Creates a new structure of a given type and name.
	 * @param name 
	 * @param structureType
	 * @param deviceNames
	 * @return the server response
	 */
	public MessageResponse newStructure(String name, String structureType,
			List<String> deviceNames) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("NAME", name);
		params.put("ATTR", structureType);
		String def = "";
		for (String deviceName : deviceNames)
			def += " " + deviceName;
		params.put("DEVICES", def);

		return connection.executeMethod(MethodNames.NEWSTRUCTURE, params);
	}

	/**
	 * Deletes the structure with the given name
	 * @param name the name of the structure to delete
	 * @return the messageresponse from the server on the given request
	 */
	public MessageResponse deleteStructure(String name) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("NAME", name);

		return connection.executeMethod(MethodNames.DELETESTRUCTURE, params);
	}

	/**
	 * Adds devices to a structure and returns the response from the server
	 * @param name the name of the structure
	 * @param deviceNames names of the devices to add
	 * @return the response from the server
	 */
	public MessageResponse addDeviceInStructure(String name,
			List<String> deviceNames) {
		Map<String, String> params = new HashMap<String, String>();

		params.put("NAME", name);

		String def = "";

		for (String devName : deviceNames)
			def += " " + devName;
		params.put("DEVICES", def);

		return connection.executeMethod(MethodNames.ADDDEVICEINSTRUCTURE,
				params);
	}

	/**
	 * Deletes devices from a structure
	 * @param name the name of the structure
	 * @param deviceNames the names of the devices to delete
	 * @return response from the server
	 */
	public MessageResponse deleteDeviceInStructure(String name,
			List<String> deviceNames) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("NAME", name);

		String def = "";

		for (String devName : deviceNames)
			def += " " + devName;
		params.put("DEVICES", def);

		return connection.executeMethod(MethodNames.DELETEDEVICEINSTRUCTURE,
				params);
	}

	/**
	 * This method renews the devices in an existing structure. 
	 * @param name the name of the structure
	 * @param deviceNames names of the devices
	 * @return the server response
	 */
	public MessageResponse renewDeviceInStructure(String name,
			List<String> deviceNames) {
		Map<String, String> params = new HashMap<String, String>();

		params.put("NAME", name);

		String def = "";
		for (String devName : deviceNames)
			def += " " + devName;
		params.put("DEVICES", def);

		return connection.executeMethod(MethodNames.RENEWDEVICEINSTRUCTURE,
				params);
	}

	// ****** SCRIPT FUNCTIONS ******

	/**
	 * Returns all scripts on the FHEM server
	 * @return List of ScriptResponses from server
	 */
	public List<ScriptResponse> getAllScripts() {
		return (List<ScriptResponse>) connection.getList(
				MethodNames.GETALLSCRIPTS,
				new TypeToken<List<ScriptResponse>>() {
				}.getType());
	}

	/**
	 * Gets a single script from the server
	 * @param name the name of the script
	 * @return a scriptresponse from the server
	 */
	public ScriptResponse getScript(String name) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("NAME", name);

		return (ScriptResponse) connection.getSingleObject(
				MethodNames.GETSCRIPT, params, ScriptResponse.class);
	}

	/**
	 * Adds a script to the FHEM-Server. Please see "TheOpenTransporter" API for
	 * more information about scripts.
	 * @param name the name of the script
	 * @param regexp regular expressions for the script
	 * @param commands commands of the script
	 * @return the server response
	 */
	public MessageResponse addScript(String name, String regexp, String commands) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("NAME", name);
		params.put("REGEXP", regexp);
		params.put("COM", commands);

		return connection.executeMethod(MethodNames.ADDSCRIPT, params);
	}

	/**
	 * Deletes a script on the server.
	 * @param name the script name
	 * @return the server response
	 */
	public MessageResponse deleteScript(String name) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("NAME", name);

		return connection.executeMethod(MethodNames.DELETESCRIPT, params);
	}

	/**
	 * Updates a script on the server, changing it's regexp and command. Please see
	 * the TheOpenTransporter API for more information about scripts in detail.
	 * @param name the name of the script
	 * @param regexp regular expression explaining the script
	 * @param commands command of the script
	 * @return the server response
	 */
	public MessageResponse updateScript(String name, String regexp,
			String commands) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("NAME", name);
		params.put("REGEXP", regexp);
		params.put("COM", commands);

		return connection.executeMethod(MethodNames.UPDATESCRIPT, params);
	}

	// ***** COMMAND FUNCTIONS *****

	/**
	 * Returns the version of the FHEM Server. This should have a format like "1.0.0"
	 * @return a string containg the serverversion
	 */
	public String getVersion() {
		try {
			String jsonString = connection.callMethod(MethodNames.GETVERSION);
			JsonObject object = (JsonObject) new JsonParser().parse(jsonString);
			String version = object.get("Version").toString();
			return version.substring(1, version.length() - 1);
		} catch (ConnectException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Saves current changes on the FHEM-Server
	 * @return the server response
	 */
	public MessageResponse save() {
		return connection.executeMethod(MethodNames.SAVE,
				new HashMap<String, String>());
	}

	/**
	 * Gets an FHEM-File from the server as plain text.
	 * @param path the path of the FHEM-File
	 * @return the file as plain text
	 */
	public String getFHEMFile(String path) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("NAME", path);

		try {
//			return connection.callMethod(MethodNames.GETFHEMFILE, params);
			return connection.callMethod(MethodNames.GETFHEMFILE, params, false, true);
		} catch (ConnectException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets the FHEM-Config file.
	 * @return the FHEM-Config file as plain text.
	 */
	public String getFHEMCfg() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("NAME", "fhem.cfg");

		try {
			return connection.callMethod(MethodNames.GETFHEMFILE, params, true, true);
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Overwrites the current config file with the one in the String. Please
	 * use this with caution, as the old config-file is overwritten and thus cannot
	 * be restored. Each command should be separated by a linebreak with "\\n".
	 * @param data the config file as plain text.
	 * @return whether or not it could be set
	 */
	public boolean setFHEMCfg(String data) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DATA", data);

		return connection.executeMethod(MethodNames.SETFHEMCFG, params, true).isTrue();
	}

	/**
	 * Updates the FHEM Server. This may or may not work depending of the TheOpenTransporter version
	 * and it's compatibility with the FHEM-Server. In the case of this classes author, the 
	 * TheOpenTranspoter plattform had to be modified a bit, changing the "TheOpenTransporter_FntUpdateFhem"
	 * subroutine to "CommandRereadCfg" instead of "CommandUpdateFHEM", if you want this method
	 * to reread the config. Further changes are open to the single user but are not supported by this API.
	 * @return the server response. In case of CommandRereadCfg every error produces by the new config is parsed as well.
	 */
	public MessageResponse updateFHEM() {
		return connection.executeMethod(MethodNames.UPDATEFHEM);
	}
	
	/**
	* This only works if you altered the THE OPEN TRANSPORTER API as it does not support this operation
	* natively. Please download a new version of THE OPEN TRANSPORTER from the google project of this java API.
	* It should support this operation.
	*
	 */
	public MessageResponse rereadConfiguration() {
		return connection.executeMethod(MethodNames.REREADCFG);
	}

	/**
	 * Returns the DeviceManager of this server. All device-changes should be done with this manager.
	 * Please only use the public-methods given by this class to implement your own Manager.
	 * @return the device manager
	 */
	public DeviceManager getDeviceManager() {
		if (deviceManager == null)
			deviceManager = new DeviceManager(this);
		return this.deviceManager;
	}

	/**
	 * Gets the StructureManager of this server. All changes in structures should be done with this manager.
	 * @return the structure manager
	 */
	public StructureManager getStructureManager() {
		if (structureManager == null)
			structureManager = new StructureManager(this);

		return this.structureManager;
	}

	/**
	 * Gets the LogFileManager of this server. Please manipulate with logs on the server only using this.
	 * @return the log file manager
	 */
	public LogFileManager getLogFileManager() {
		if (logFileManager == null)
			logFileManager = new LogFileManager(this);

		return this.logFileManager;
	}

	/**
	 * Gets the ScriptManager of this server. Please manipulate with scripts on the server only using this.
	 * @return the script manager.
	 */
	public ScriptManager getScriptManager() {
		if (scriptManager == null)
			scriptManager = new ScriptManager(this);

		return scriptManager;
	}
	
	/**
	 * Gets the configuration manager of this server
	 * @return the config manager
	 */
	public ConfigurationManager getConfigManager(){
		if (configManager == null)
			configManager = new ConfigurationManager(this);
		return this.configManager;
	}
	
}
