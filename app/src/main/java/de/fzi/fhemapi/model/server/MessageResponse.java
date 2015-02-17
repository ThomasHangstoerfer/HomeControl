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
package de.fzi.fhemapi.model.server;

import com.google.gson.annotations.SerializedName;

/**
 * This class represents the response from the server to a methodcall. It indicates if the call was successfull or not.
 * @author Can Yumusak
 *
 */
public class MessageResponse extends ResponseObject {

	@SerializedName("MESSAGE")
	public String message = "ERROR";
	@SerializedName("TYPE")
	public String type = "NO CONNECTION";

	/**
	 * Emtpy constructor, default value is "ERROR: NO CONNECTION"
	 */
	public MessageResponse() {};

	/**
	 * default constructor
	 * @param message
	 * @param type
	 */
	public MessageResponse(String message, String type) {
		this.message = message;
		this.type = type;
		
	}

	@Override
	public String toString() {
		return type + ": " + message;
	}

	/**
	 * Checks if this MessageResponse is true or not, prints the error to the console if not and returns a boolean.
	 * @return whether or not this object is true.
	 */
	public boolean isTrue() {
		if (message.equals("true"))
			return true;
		System.err.println(message);
		return false;
	}
	

	/**
	 * This is a small and handy method which allows printing an operationName to the console. This was used
	 * for debugging purposes. The console shows "Success/Faield + operationname" and the error in case
	 * of fail when using this method.
	 * @param operationName
	 * @return whether or not this returns true
	 */
	public boolean isTrue(String operationName) {
		if(isTrue()){
			System.out.println("Success "+operationName);
			return true;
		}else{
			System.err.println("Failed "+operationName);
			return false;
		}
	}
	
}
