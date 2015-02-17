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

import java.util.ArrayList;
import java.util.List;

import de.fzi.fhemapi.model.actuatorparameters.ActuatorParameters;
import de.fzi.fhemapi.model.devicetypes.Device;
import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.model.server.MessageResponse;
import de.fzi.fhemapi.server.constants.FHEMParameters;
import de.fzi.fhemapi.server.core.DeviceFactory;

/**
 * The DeviceManager is a class which enables all interaction with the server devices.
 * @author Can Yumusak
 *
 */
public class DeviceManager {
	FHEMServer server;
	List<Device> devices;

	/**
	 * default constructor
	 * @param server an fhem server
	 */
	protected DeviceManager(FHEMServer server) {
		this.server = server;

		update();
	}

	/**
	 * method called to update all devices from server
	 */
	public void update() {
		List<DeviceResponse> response = server.getAllDevices();
		devices = new ArrayList<Device>();

		for (DeviceResponse device : response) {
			devices.add(DeviceFactory.getDevice(device, server));
		}
	}

	/**
	 * this method downloads a single device from server and adds it to the manager.
	 * @param deviceName the name of the device
	 */
	public void update(String deviceName) {
		devices.add(DeviceFactory.getDevice(server.getDevice(deviceName),
				server));
	}

	/**
	 * prints a list of all devices on the console
	 */
	public void printAllDevices() {
		System.out.println("******* Devices from DeviceManager: ");
		for (Device device : devices)
			System.out.println(device);
		System.out.println("******* End of Devices from DeviceManager");

	}

	
	/**
	 * prints a List<? extends Device> List to the console
	 * @param deviceList the list to print
	 */
	public static void printDevices(List<? extends Device> deviceList) {
		System.out.println("******* Devices from DeviceManager: ");
		for (Device device : deviceList)
			System.out.println(device);
		System.out.println("******* End of Devices from DeviceManager");

	}

	/**
	 * returns all devices from a special device type
	 * @param clazz the class of the device type
	 * @return a list containing all devices
	 */
	@SuppressWarnings("unchecked") 
	public <T extends Device> List<T> getDevicesFromType(Class<T> clazz) {
		List<T> devices = new ArrayList<T>();

		for (Device device : this.devices) {
			if (clazz.isInstance(device))
				devices.add((T) device);
		}

		return devices;
	}

	/**
	 * get a device from the manager with a given name
	 * @param deviceName the device name
	 * @return null if the device does not exist
	 */
	public Device getDevice(String deviceName) {
		for (Device device : this.devices) {
			if (device.getName().equals(deviceName))
				return device;
		}
		return null;
	}

	/**
	 * get devices by their names in the array
	 * @param deviceName the device names
	 * @return a list with all the devices
	 */
	public List<Device> getDevices(String[] deviceName) {
		List<Device> devices = new ArrayList<Device>();

		for (Device device : this.devices) {
			for (String name : deviceName)
				if (device.getName().equals(name))
					devices.add(device);
		}
		return devices;
	}

	/**
	 * Get all devices whose names start with a string
	 * @param subName the string to search for
	 * @return List of matching devices
	 */
	public List<Device> getDevicesStartsWith(String subName) {
		List<Device> devices = new ArrayList<Device>();

		for (Device device : this.devices) {
			if (device.getName().startsWith(subName))
				devices.add(device);
		}
		return devices;
	}

	/**
	 * returns a list with all saved devices
	 * @return the list of all saved devices
	 */
	public List<Device> getDevices() {
		return devices;
	}

	/**
	 * creates a new actuator on the server. The parameters and type of the actuator is determined by the
	 * ActuatorParamters class. Please use the corresponding class implementing ActuatorParameters to
	 * generate a new actuator. An example is FS20Parameters.class. Please look up the whole package 
	 * de.fzi.actuatorparameters for details.
	 * 
	 * The server response is printed on the console and returned.
	 * @param params the actuator parameters
	 * @return whether or not the server created the device
	 */
	public boolean createNewActuator(ActuatorParameters params) {
		if (params.checkParams()) {
			MessageResponse response = server.newDevice(params);
			return response.isTrue("creating actuator "+params.get(FHEMParameters.NAME));
		}
		System.err.println("Creating device " + params.get(FHEMParameters.NAME)
				+ " failed, please use the right FHEMParameters!");
		return false;
	}
	
	/**
	 * creates a new actuator on the server. The parameters and type of the actuator is determined by the
	 * ActuatorParamters class. Please use the corresponding class implementing ActuatorParameters to
	 * generate a new actuator. An example is FS20Parameters.class. Please look up the whole package 
	 * de.fzi.actuatorparameters for details.
	 * 
	 * The server response is printed on the console and returned.
	 * @param params the actuator parameters
	 * @return the response from server
	 */
	public MessageResponse createNewActuatorAsMessage(ActuatorParameters params) {
		if (params.checkParams()) {
			MessageResponse response = server.newDevice(params);
			return response;
		}
		System.err.println("Creating device " + params.get(FHEMParameters.NAME)
				+ " failed, please use the right FHEMParameters!");
		return new MessageResponse();
	}

	/**
	 * deletes a device on the server. The server response is printed on the console.
	 * @param deviceName name of the device
	 * @return whether or not the server could delete the device. 
	 */
	public boolean deleteDevice(String deviceName) {
		MessageResponse mr = server.deleteDevice(deviceName);
		return mr.isTrue("deleting Device "+deviceName);
	}
	
	/**
	 * Reloading Device from the server
	 * @param deviceName the device name
	 * @return the device
	 */
	public Device reloadDevice(String deviceName){
		devices.remove(getDevice(deviceName));
		Device dev = DeviceFactory.getDevice(server.getDevice(deviceName), server);
		devices.add(dev);
		return dev;
	}

}
