package de.fzi.fhemapi.model.server.subelements;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Reading {

	@SerializedName("state")
	public State state;
	
	@SerializedName("CommandAccepted")
	public State commandAccepted;

	@SerializedName("desired-temp")
	public State desiredTemp;

	@SerializedName("measured-temp")
	public State measuredTemp;

	@SerializedName("humidity")
	public State humidity;

	@SerializedName("battery")
	public State battery;

	public Map<String, Object> readings = null;

}
