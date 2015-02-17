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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import de.fzi.fhemapi.model.devicetypes.Actuator;
import de.fzi.fhemapi.model.devicetypes.Device;
import de.fzi.fhemapi.model.devicetypes.OnOffActuator;
import de.fzi.fhemapi.model.devicetypes.Sensor;
import de.fzi.fhemapi.model.devicetypes.SensorActorDevice;
import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.server.FHEMServer;
/**
 * Class giving a bridge between FHEM-Server-responses and Java Objects for devices.
 * @author Can Yumusak
 *
 */
public class DeviceFactory {
	
	
	List<Class<? extends Device>> classes;
	private static DeviceFactory instance;
	
	
	private DeviceFactory(){
		initDefaultDeviceTypes();
	}
	
	private void initDefaultDeviceTypes(){
		classes = new LinkedList<>();
		classes.add(Actuator.class);
		classes.add(Sensor.class);
		classes.add(Device.class);
		classes.add(SensorActorDevice.class);
		classes.add(OnOffActuator.class);
		
		orderClassList();
	}

	/**
	 * Generates Java-Devices in function of the given FHEM-DeviceResponse
	 * @param response FHEM DeviceResponse
	 * @param server FHEMServer
	 * @return the device as a java object
	 */
	public static Device getDevice(DeviceResponse response, FHEMServer server) {
		try{
			// Return an instance if it is forced
			if(response.eAttributes != null && response.eAttributes.deviceType != null){
				for(Class<? extends Device> clazz : getInstance().classes){
					if(response.eAttributes.deviceType.equals(clazz.getSimpleName())){
						Constructor<? extends Device> constructor = clazz.getConstructor(DeviceResponse.class, FHEMServer.class);
						return (Device) constructor.newInstance(response, server); 
					}
				}
			}
			
			// Try automatic recognition
			for(Class<? extends Device> clazz : getInstance().classes){
				Method method = clazz.getMethod("isOfType", DeviceResponse.class);
				boolean isOfType = (boolean) method.invoke(null, response);
				if(isOfType){
					Constructor<? extends Device> constructor = clazz.getConstructor(DeviceResponse.class, FHEMServer.class);
					return (Device) constructor.newInstance(response, server);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		return new Device (response, server);
	}
	
	
	/**
	 * Registers a custom device type
	 * @param clazz the class to be registered, this must extends the class Device
	 */
	public static void registerDeviceType(Class<? extends Device> clazz){
		getInstance().classes.add(clazz);
		getInstance().orderClassList();
	}
	
	/**
	 * Gets all registered devicetypes
	 * @return a list with all registered device types
	 */
	public static String[] getAvailableDevicetypes(){
		String[] returnArray = new String[getInstance().classes.size()];
		for(int i = 0; i < returnArray.length; i++){
			returnArray[i] = getInstance().classes.get(i).getSimpleName();
		}
		return returnArray;
	}
	
	/**
	 * returns the singleton object of this class
	 * @return the instance
	 */
	public static DeviceFactory getInstance(){
		if(instance == null)
			instance = new DeviceFactory();
		return instance;
	}
	
	
	private void orderClassList(){
		List<Class<? extends Device>> orderedList = new LinkedList<Class<? extends Device>>();
		
		List<DeviceIntPair> classesList = new LinkedList<DeviceIntPair>();
		for(Class<? extends Device> c : classes){
			classesList.add(new DeviceIntPair(c, getSuperclasses(c)));
		}
		Collections.sort(classesList);
		
		for(DeviceIntPair pair : classesList)
			orderedList.add(pair.clazz);
			
		this.classes = orderedList;
	}
	
	private int getSuperclasses(Class clazz){
		Class c = clazz;
		int counter = 0;
		while(c != null){
			counter++;
			c = c.getSuperclass();
		}
		return counter;
	}
	
	private class DeviceIntPair implements Comparable<DeviceIntPair>{
		public Class<? extends Device> clazz;
		public int superclassAmount;
		
		public DeviceIntPair(Class<? extends Device> clazz, int superclassAmount) {
			this.clazz = clazz;
			this.superclassAmount = superclassAmount;
		}

		@Override
		public int compareTo(DeviceIntPair o) {
			return o.superclassAmount - this.superclassAmount;
		}
	}
	
	/**
	 * Returns the List of all available classes
	 * @return all available classes
	 */
	public static List<Class<? extends Device>> getClasses(){
		return getInstance().classes;
	}
}
