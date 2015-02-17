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

import de.fzi.fhemapi.model.LogFile;
import de.fzi.fhemapi.model.server.LogFileResponse;
/**
 * The LogFile Manager. This class is used to manage all changes to the log file.
 * @author Can Yumusak
 *
 */
public class LogFileManager {

	FHEMServer server;
	List<LogFile> logs;

	/**
	 * Protected Constructor
	 * @param server
	 */
	protected LogFileManager(FHEMServer server) {
		this.server = server;

		rereadLogListFromFHEM();
	}
	
	/**
	 * loads the list of log files from the FHEM server and loads them in this Manager.
	 */
	public void rereadLogListFromFHEM() {
		logs = new ArrayList<LogFile>();
		List<LogFileResponse> responses = server.getAvailableLogFiles();

		for (LogFileResponse lr : responses)
			logs.add(new LogFile(lr));
	}

	/**
	 * Prints all log lists on the console
	 */
	public void printLogList() {
		System.out.println("********** LogFiles of LogFileManager ****");
		for (LogFile log : logs)
			System.out.println(log);
		System.out.println("********** End of LogFiles");
	}

}
