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

import de.fzi.fhemapi.model.Structure;
import de.fzi.fhemapi.model.devicetypes.Device;
import de.fzi.fhemapi.model.server.MessageResponse;
import de.fzi.fhemapi.model.server.StructureResponse;

/**
 * This class manages all structures on the FHEM-Server, like Rooms, Devicetypes, etc..
 * 
 * @author Can Yumusak
 *
 */
public class StructureManager {

	FHEMServer server;
	List<Structure> structures;

	/**
	 * default constructor
	 * @param server the FHEM Server
	 */
	protected StructureManager(FHEMServer server) {
		this.server = server;

		rereadFromFHEM();
	}

	/**
	 * reloads all structures from the server and stores them in this manager
	 */
	public void rereadFromFHEM() {
		List<StructureResponse> structureList = server.getAllStructures();
		structures = new ArrayList<Structure>();
		for (StructureResponse sr : structureList)
			structures.add(new Structure(sr));
	}

	/**
	 * prints all structures from the manager to the console
	 */
	public void printAllStructures() {
		System.out.println("***** StructureManager List ****");
		for (Structure struc : structures)
			System.out.println(struc);
		System.out.println("****** End of StructureManager List *****");
	}

	/**
	 * Returns the Structure with the given name
	 * @param name name of the structure
	 * @return the structure
	 */
	public Structure getStructureWithName(String name) {
		for (Structure structure : structures)
			if (structure.name.equals(name)) {
				return structure;
			}
		return null;
	}

	/**
	 * Returns all devices of a structure
	 * @param structureName the name of the structure
	 * @return list of devices
	 */
	public List<Device> getDevicesInStructure(String structureName) {
		Structure struc = getStructureWithName(structureName);
		return (struc == null) ? null : getDevicesInStructure(struc);
	}

	/**
	 * Gets the devices of a structure
	 * @param struc the structure 
	 * @return a list of devices 
	 */
	public List<Device> getDevicesInStructure(Structure struc) {
		String[] names = new String[struc.deviceNames.size()];
		for (int i = 0; i < names.length; i++)
			names[i] = struc.deviceNames.get(i);

		return server.getDeviceManager().getDevices(names);
	}
	
	/**
	 * Creates a new structure on the FHEM Server.
	 * @param structure the structure to create
	 * @return whether or not the structure was created
	 */
	public MessageResponse createNewStructure(Structure structure){
		MessageResponse response = server.newStructure(structure.name,
				structure.type, structure.deviceNames);
		 response.isTrue("build Structure "+structure);
		 return response;
	}
	
	
	/**
	 * Adds a list of devices to a structure
	 * @param structureName the name of the structure
	 * @param deviceNames the devices to add
	 * @return whether or not this worked
	 */
	public boolean addDeviceToStructure(String structureName, List<String> deviceNames){
		boolean state = server.addDeviceInStructure(structureName, deviceNames).isTrue("add Device "+deviceNames.toString()+" to "+structureName);
		if(state)
			for(Structure struc : structures){
				server.deleteDeviceInStructure(struc.name, deviceNames);
			}
		return state;
	}
	
	/**
	 * Adds a device to a structure
	 * @param structureName the name of the structure
	 * @param deviceName the devices to add
	 * @return whether or not this worked
	 */
	public boolean addDeviceToStructure(String structureName, String deviceName){
		List<String> deviceNames = new ArrayList<>();
		deviceNames.add(deviceName);
		return addDeviceToStructure( structureName, deviceNames);		
	}

	/**
	 * Get all available structures
	 * @return the structure
	 */
	public List<Structure> getStructures(){
		return this.structures;
	}
	
	/**
	 * Returns true if the structure exists locally
	 * @param structureName the name of the strucure
	 * @return true if it exists
	 */
	public boolean structureExists(String structureName){
		for(Structure struc : this.structures){
			if(struc.name.equals(structureName))
				return true;
		}
		return false;
	}
}
