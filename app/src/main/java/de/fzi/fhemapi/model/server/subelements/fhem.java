package de.fzi.fhemapi.model.server.subelements;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class fhem {

	@SerializedName("history")
	public ArrayList<HistoryEntry> history = new ArrayList<HistoryEntry>();

	@SerializedName("last_update")
	public String last_update;


    @Override
    public String toString() {

        return ("fhem-Element: history:" + history.toString() + " last_update:" + last_update);
    }

}
