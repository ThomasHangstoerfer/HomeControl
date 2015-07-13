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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.model.server.MessageResponse;
import de.fzi.fhemapi.model.server.ResponseObject;
import de.fzi.fhemapi.model.server.subelements.Reading;
import de.fzi.fhemapi.server.constants.MethodNames;
import de.fzi.fhemapi.server.core.ContentParamDeserializer;
import de.fzi.fhemapi.server.core.ReadingDeserializer;

/**
 * This class represents a connection between java and the fhem server. Here are all the methods which are offered by the "TheOpenTransporter"-API.
 * This is the lowest abstraction of the api and thus should not be used by inexperienced users.
 * @author Can
 *
 */
public class FHEMConnection {

	private String serverIP = "127.0.0.1";
	private int port = 8090;

	/**
	 * default constructor
	 * @param serverIP the server ip
	 * @param port the port of the server
	 * @throws java.net.ConnectException throws an ConnectException if the FHEM-Server could not be found (in this case it checks whether the device "global"
	 * can be parsed.
	 * @throws java.net.URISyntaxException
	 */
	public FHEMConnection(String serverIP, int port) throws ConnectException {
		this.serverIP = serverIP;
		this.port = port;
		System.setProperty("java.net.preferIPv4Stack", "true");
		testConnection();
	}

	/**
	 * tests the connection to the FHEM-Server by getting the "global" device
	 * @return whether or not the connection is possible
	 * @throws java.net.ConnectException
	 * @throws java.net.URISyntaxException
	 */
	public void testConnection() throws ConnectException {
		callMethod("getDevice?NAME=global");
	}

	/**
	 * Simple method for calling a method on the FHEM Server without parameters. Available method names are written in the
	 * constants.MethodNames.java class
	 * @param methodName the method name to be called
	 * @return the serialized JSON String from the server.
	 * @throws java.net.ConnectException if the connection could not be established
	 * @throws java.net.URISyntaxException
	 */
	public String callMethod(String methodName) throws ConnectException {
		return callMethod(methodName, null);
	}
	
	/**
	 * Simple method for calling a method on the FHEM Server without parameters. Available method names are written in the
	 * constants.MethodNames.java class
	 * @param methodName the method name to be called
	 * @param params the parameters of the method
	 * @return the serialized JSON String from the server.
	 * @throws java.net.ConnectException if the connection could not be established
	 * @throws java.net.URISyntaxException
	 */
	public String callMethod(String methodName, Map<String, String> params) throws ConnectException{
		return callMethod(methodName, params, false);
	}
	
	/**
	 * Simple method for calling a method on the FHEM Server without parameters. Available method names are written in the
	 * constants.MethodNames.java class
	 * @param methodName the method name to be called
	 * @param params the parameters of the method
	 * @param doPostRequest if this call should be done as HTTPPost request
	 * @return the serialized JSON String from the server.
	 * @throws java.net.ConnectException if the connection could not be established
	 * @throws java.net.URISyntaxException
	 */
	public String callMethod(String methodName, Map<String, String> params, boolean doPostRequest) throws ConnectException{
		return callMethod(methodName, params, doPostRequest, false);
	}

	/**
	 * Simple method for calling a method on the FHEM Server with parameters. Available method names are written in the
	 * constants.MethodNames.java class
	 * @param methodName the method name to be called
	 * @param params the parameters of the method
	 * @param doPostRequest if this call should be done as HTTPPost request
	 * @return the serialized JSON String from the server.
	 * @throws java.net.ConnectException if the connection could not be established
	 * @throws java.net.URISyntaxException
	 */
	public String callMethod(String methodName, Map<String, String> params, boolean doPostRequest, boolean markLineBreak)
			throws ConnectException {
		String paramsString = "";

        if ( methodName == MethodNames.GETFHEMFILE)
        {
            System.out.println("methodName = MethodNames.GETFHEMFILE");
        }
        else {
            System.out.println("methodName = " + methodName );
        }
		if (params != null) {
            //if ( !doPostRequest )
            //    paramsString = "?";
            for (Entry<String, String> entry : params.entrySet()) {
                if (paramsString.length() > 2)
					paramsString += "&";
				paramsString += entry.getKey() + "=" + entry.getValue();
			}
		}
        System.out.println("paramStr.length()          = " + paramsString.length());
        System.out.println("paramStr.getBytes().length = " + paramsString.getBytes().length);
		URL url;
		try {
            if(!doPostRequest)
                url = new URI("http", serverIP + ":" + port, "/" + methodName,
                        paramsString, null).toURL();
            else
                url = new URI("http", serverIP + ":" + port, "/" + methodName, null, null).toURL();
/*
            if(!doPostRequest)
                url = new URI("http", serverIP + ":" + port, "", methodName
                        + paramsString, null).toURL();
            else
                url = new URI("http", serverIP + ":" + port, "", methodName, null).toURL();
*/
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			DataOutputStream wr = null;
			if(doPostRequest){
				con.setDoOutput(true);
				con.setDoInput(true);
				con.setInstanceFollowRedirects(false); 
				con.setRequestMethod("POST"); 
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
				con.setRequestProperty("charset", "utf-8");
                //con.setRequestProperty("Content-Length", "" + Integer.toString(paramsString.getBytes().length));
                con.setRequestProperty("Content-Length", "" + Integer.toString(paramsString.length()));
				con.setUseCaches (false);
				
				wr = new DataOutputStream( con.getOutputStream() );
                //wr.writeBytes(paramsString.substring(1, paramsString.length()));
                wr.writeBytes(paramsString);
			}

			// This currently is pretty time consuming - could be because of the
			// server
            InputStream is = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in  = new BufferedReader(isr);
			StringBuilder response = new StringBuilder();
			String inputLine;

			while ((inputLine = in.readLine()) != null){
				response.append(inputLine);
				if(markLineBreak)
					response.append("\n");
			}
			

			in.close();
			
			if(doPostRequest){
				wr.flush();
				wr.close();
			}
			
			con.disconnect();
			return response.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Execute a method on the server which returns a MessageResponse. Normally this is something like true/false/error, including
	 * the message of the error.
	 * @param methodName the method name to be called
	 * @param doHttpPost if the method should be executed as post request
	 * @return the serialized JSON String from the server.
	 * @throws java.net.ConnectException if the connection could not be established
	 */
	public MessageResponse executeMethod(String methodName,
			Map<String, String> params, boolean doHttpPost) {
		try {
			String object = callMethod(methodName, params, doHttpPost);

			if (object.charAt(2) == 'C') {
				List<MessageResponse> response = new Gson().fromJson(
						toJavaJSON(object),
						new TypeToken<List<MessageResponse>>() {
						}.getType());
				return response.get(0);
			}

			return new Gson().fromJson(object, MessageResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageResponse();
		}
	}
	
	/**
	 * Execute a method on the server which returns a MessageResponse. Normally this is something like true/false/error, including
	 * the message of the error.
	 * @param methodName the method name to be called
	 * @param params the parameters
	 * @return the serialized JSON String from the server.
	 * @throws java.net.ConnectException if the connection could not be established
	 */
	public MessageResponse executeMethod(String methodName,
			Map<String, String> params) {
		return executeMethod(methodName, params, false);
	}
	
	/**
	 * Execute a method on the server which returns a MessageResponse. Normally this is something like true/false/error, including
	 * the message of the error.
	 * @param methodName the method name to be called
	 * @return the serialized JSON String from the server.
	 * @throws java.net.ConnectException if the connection could not be established
	 */
	public MessageResponse executeMethod(String methodName) {
		return executeMethod(methodName, null, false);
	}

	/**
	 * This represents a way to get a list from the fhem server. It can be used by writing the
	 * method-name and the return type (e.g.the type of a List filled with DeviceResponse.class can 
	 * be gotten by the call "Type type = new TypeToken<List<DeviceResponse>>(){}.getType();" For details
	 * look up the GSON library. 
	 * @param methodName the method name
	 * @param type the type as explained above
	 * @return the corresponding list or null if the call was not possible
	 */
	public List<? extends ResponseObject> getList(String methodName, Type type) {
		try {
			String jsonObject = callMethod(methodName);
			Gson gson = new GsonBuilder().registerTypeAdapter(
					new TypeToken<List<MessageResponse>>() {
					}.getType(), new ContentParamDeserializer()).create();

			return gson.fromJson(toJavaJSON(jsonObject), type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String toJavaJSON(String jsonObject) {
		JsonParser parser = new JsonParser();
		JsonObject object = (JsonObject) parser.parse(jsonObject);
		return object.get("Content").toString();
	}
	
	/**
	 * Gets a single object from the server. Some methods in fhem do that. The response is 
	 * represented by a ResponseObject. 
	 * @param methodName the name of the method
	 * @param params the parameters of the call; Mostly a name and some other stuff
	 * @param clazz the class type to return, eg. DeviceType.class
	 * @return the object.
	 */
	@SuppressWarnings("unchecked")
	public ResponseObject getSingleObject(String methodName,
			Map<String, String> params,
			@SuppressWarnings("rawtypes") Class clazz) {
		try {
			String jsonObject = callMethod(methodName, params);
			final int lineSize = 200;
			if ( jsonObject.length() > lineSize )
			{
				System.out.println("jsonObject: ");
				String s = jsonObject;
				while ( s.length() > lineSize )
				{
					System.out.println( s.substring(0, lineSize) );
					s = s.substring(lineSize);
				}
				System.out.println( s );
			}
			else
			{
				System.out.println("jsonObject: " + jsonObject);
			}
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Reading.class, new ReadingDeserializer());
			Gson gson = gsonBuilder.create();

			// TODO hier sollte clazz anstelle von DeviceResponse.class verwendet werden
			// sonst funktioniert das nur fuer DeviceResponses
			return gson.fromJson(jsonObject, DeviceResponse.class);
			//return new Gson().fromJson(jsonObject, DeviceResponse.class);
//            return new Gson().fromJson(jsonObject, clazz);

            //return new ResponseObject();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}
