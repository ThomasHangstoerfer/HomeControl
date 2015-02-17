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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import de.fzi.fhemapi.model.server.MessageResponse;

/**
 * This class helps deserializing a serialized list of devicenames from a structure when requesting this from the server.
 * @author Can Yumusak
 *
 */
public class ContentParamDeserializer implements
		JsonDeserializer<List<MessageResponse>> {

	@Override
	public List<MessageResponse> deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		Set<Entry<String, JsonElement>> entrySet = arg0.getAsJsonObject()
				.entrySet();
		List<MessageResponse> returnList = new ArrayList<MessageResponse>();
		for (Entry<String, JsonElement> entry : entrySet) {
			returnList.add(new MessageResponse(entry.getKey(), "Device"));
		}

		return returnList;
	}
}
