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
package de.fzi.fhemapi.model.devicetypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.ls.LSInput;

import de.fzi.fhemapi.listener.DeviceListener;
import de.fzi.fhemapi.listener.DeviceUpdater;
import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.model.server.subelements.State;
import de.fzi.fhemapi.server.FHEMServer;
import de.fzi.fhemapi.server.constants.FHEMParameters;

/**
 * Represents the java-object equivalent of a FHEM device
 * @author Can Yumusak
 *
 */
public class Device {

	/**
	 * This is written in a group-structure on the FHEM-Server to differentiate between the Device Types
	 */
	public static final String TAG = "DEVICE";
	
	/**
	 * Default sleep time when updating the device from the FHEM Server. This is only used when 
	 * activating "listenForChanges"
	 */
	private int updateTime = 500;

	private String name = "";
	private Map<String, String> structures;
	private String def = "UNDEF";
	private String type;
	private FHEMServer server;
	private List<DeviceListener> listenerList = new ArrayList<DeviceListener>();
	private DeviceUpdater updater;
	private State lastState;
	private State commandAccepted;

	/**
	 * Object constructor
	 * @param name the device name
	 * @param type the type of the device
	 * @param def the "def" parameter of the device, adresses are usually written here
	 * @param server an FHEM Server
	 */
	public Device(String name, String type, String def, FHEMServer server) {
		this.name = name;
		this.type = type;
		this.def = def;
	}

	/**
	 * This constructor is used by the DeviceFactory.
	 * @param response the fhem server response
	 * @param server the fhem server
	 */
	public Device(DeviceResponse response, FHEMServer server) {
		update(response);
		this.server = server;
	}

	/**
	 * method used to update this device from the FHEM server
	 */
	public void updateFromFHEM() {
		update(server.getDevice(name));
	}
	
	/**
	 * Update-Method in dependance of the server-response. This should be rewritten if 
	 * this class is extended and new variables added.
	 * @param response
	 */
	protected void update(DeviceResponse response) {
		this.name = response.name;
		this.type = response.type;
		this.def = response.def;

		if(response.lastState != null){
			this.lastState = response.lastState.state;
			this.commandAccepted = response.lastState.commandAccepted;
		}

		if (response.eAttributes != null) {
			if (response.eAttributes.room != null)
				addToStructure("room", response.eAttributes.room);
		}
		
	}

	/**
	 * Adds this device to a structure. Currently this is an offline process.
	 * @param struct the structure type
	 * @param structName the structure name
	 */
	public void addToStructure(String struct, String structName) {
		if (structures == null)
			structures = new HashMap<String, String>();

		structures.put(struct, structName);
	}

	/**
	 * Static Method used by the DeviceFactory
	 * @param response fhem server-response
	 * @return whether or not this device is of this class type
	 */
	public static boolean isOfType(DeviceResponse response) {
		return true;
	}

	/**
	 * The readable name of this class
	 * @return the readable classname of this device
	 */
	public String getReadableClassName() {
		return getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return ("DEV Name: " + name + " Class: " + getReadableClassName());
	}

	/**
	 * Function used to add a device listener.
	 * @param listener a device listener
	 */
	public void addDeviceListener(DeviceListener listener) {
		this.listenerList.add(listener);
		if (updater == null)
			listenForChanges(true);
	}

	/**
	 * Function used to remove a device-listener
	 * @param listener the device listeners
	 */
	public void removeDeviceListener(DeviceListener listener) {
		this.listenerList.remove(listener);
	}

	/**
	 * Clears all the device listener
	 */
	public void removeAllDeviceListener() {
		listenForChanges(false);
		listenerList = new ArrayList<DeviceListener>();
	}

	/**
	 * This should be pretty resource-consuming, so please use it with caution!
	 * This updates this device periodically from the FHEM server in its own thread.
	 * @param state
	 *            whether or not the device should be updated
	 */
	public void listenForChanges(boolean state) {
		if (state) {
			updater = new DeviceUpdater(this, server, updateTime);
			updater.start();
		} else {
			updater.deactivate();
		}
	}
	
	/**
	 * Forces the DeviceFactory to adopt a certain Class at the next instantiation.
	 * This should be set if the user wishes to use a device as a certain device,
	 * the DeviceFactory did not intent to.
	 * @param className
	 */
	public void setDeviceType(String className){
		Map<String, String> params = new HashMap<String, String> ();
		className = (className == null) ? "undef" : className;
		params.put(FHEMParameters.DEVICETYPE, className);
		server.setDevice(name, params);
	}
	
	/**
	 * Get the server of this device
	 * @return this server
	 */
	public FHEMServer getServer(){
		return this.server;
	}
	
	/**
	 * Renames the device locally and on the server
	 * @param newName the new device name
	 */
	public void rename(String newName){
		Map<String, String> params = new HashMap<String, String>();
		params.put(FHEMParameters.NEWNAME, newName);
		if(server.setDevice(name, params).isTrue())
			this.name = newName;
	}
	
	/**
	 * Returns whether or not this device derives from the given clazz
	 * @param clazz the class to check
	 * @return whether or not it derives from clazz
	 */
	@SuppressWarnings("rawtypes")
	public boolean derivesFromClass(Class clazz){
		if(getClass().getSimpleName().equals(clazz.getSimpleName())) return true;

		Class deviceClass = getClass();
		while(!deviceClass.getClass().getSuperclass().getSimpleName().equals(Object.class.getSimpleName())){
			deviceClass = deviceClass.getClass().getSuperclass();
			System.out.println("Vergleiche: "+deviceClass.getSimpleName()+" "+(clazz.getSimpleName()));
			if(deviceClass.getSimpleName().equals(clazz.getSimpleName()))
				return true;
		}
		
		return false;
	}

	public int getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getStructures() {
		return structures;
	}

	public void setStructures(Map<String, String> structures) {
		this.structures = structures;
	}

	public String getDef() {
		return def;
	}

	public void setDef(String def) {
		this.def = def;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<DeviceListener> getListenerList() {
		return listenerList;
	}

	public void setListenerList(List<DeviceListener> listenerList) {
		this.listenerList = listenerList;
	}

	public State getLastState() {
		return lastState;
	}

	public void setLastState(State lastState) {
		this.lastState = lastState;
	}

	public State getCommandAccepted() {
		return commandAccepted;
	}

	public void setCommandAccepted(State commandAccepted) {
		this.commandAccepted = commandAccepted;
	}

	public void setServer(FHEMServer server) {
		this.server = server;
	}

}
