package de.fzi.fhemapi.model.server.subelements;

import com.google.gson.annotations.SerializedName;

public class Reading {

	@SerializedName("state")
	public State state;
	
	@SerializedName("CommandAccepted")
	public State commandAccepted;

}
