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
package de.fzi.fhemapi.listener;

import de.fzi.fhemapi.model.devicetypes.Device;
import de.fzi.fhemapi.server.FHEMServer;

/**
 * This updater periodically refreshs all variables of a device, when registered. This class is automatically registered to a device, when 
 * a listener is added. This class should be used with caution, as it can lead to a lot of threads and a lot of overhead on the server, as 
 * it usually is pretty slow.
 * @author Can Yumusak
 *
 */
public class DeviceUpdater extends Thread {

	Device device;
	/**
	 * true if the updater is active.
	 */
	boolean isActive = true;
	FHEMServer server;
	int sleepTime;

	/**
	 * default constructor
	 * @param device the device to be registered
	 * @param server
	 * @param updateTime
	 */
	public DeviceUpdater(Device device, FHEMServer server, int updateTime) {
		this.device = device;
		this.server = server;
		this.sleepTime = updateTime;
	}

	@Override
	public void run() {
		while (isActive) {
			device.updateFromFHEM();
			for (DeviceListener listener : device.getListenerList())
				listener.onUpdate();
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * deactivates this updater
	 */
	public void deactivate() {
		isActive = false;
	}
	
	@Override
	public synchronized void start() {
		isActive = true;
		super.start();
	}

}
