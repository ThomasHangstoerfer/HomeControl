package de.fzi.fhemapi.server.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fzi.fhemapi.model.server.subelements.HistoryEntry;
import de.fzi.fhemapi.model.server.subelements.Reading;
import de.fzi.fhemapi.model.server.subelements.State;
import de.fzi.fhemapi.model.server.subelements.fhem;

/**
 * Created by thangstoerfer on 13.07.15.
 */
public class FhemElementDeserializer implements JsonDeserializer<fhem> {
    private final fhem fhemElement = new fhem();


    public fhem deserialize(JsonElement jsonStr, Type typeOfSrc, JsonDeserializationContext context) {
        System.out.println("ReadingDeserializer.deserialize(" + jsonStr.toString() + ")");

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        if ( jsonStr.isJsonObject() )
        {
            //reading.readings = new HashMap<String, Object>();
            for(Map.Entry<String, JsonElement> entry : jsonStr.getAsJsonObject().entrySet()) {
                System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
                //reading.readings.put(entry.getKey(), context.deserialize(entry.getValue(), State.class));

                if ( entry.getKey().equals( "history" ) ) {

                    JsonArray arr = entry.getValue().getAsJsonArray();
                    for (int i = 0; i < arr.size(); i++ )
                    {
                        JsonElement el = arr.get(i);
                        JsonArray a = el.getAsJsonArray();
                        if ( a.size() >= 4 )
                        {
                            HistoryEntry he = new HistoryEntry();
                            he.timestamp = a.get(0).getAsString();
                            he.name = a.get(1).getAsString();
                            he.val = a.get(2).getAsString();
                            he.text = a.get(3).getAsString();
                            fhemElement.history.add(he);
                        }
                        else
                        {
                            System.out.println("Invalid array-size!");
                        }


                    }
                }
                if ( entry.getKey().equals( "last_update" ) ) {
                    //reading.battery = (State) context.deserialize(entry.getValue(), State.class);
                }

                // TODO check what 'value' is (number, string, array) and handle accordingly
            }
            return fhemElement;
        }
        return null;

    }
}
