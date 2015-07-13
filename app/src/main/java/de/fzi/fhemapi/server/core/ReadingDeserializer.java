package de.fzi.fhemapi.server.core;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import de.fzi.fhemapi.model.server.subelements.Reading;
import de.fzi.fhemapi.model.server.subelements.State;

/**
 * Created by thangstoerfer on 13.07.15.
 */
public class ReadingDeserializer implements JsonDeserializer<Reading> {
    private final Reading reading = new Reading();

    public Reading deserialize(JsonElement jsonStr, Type typeOfSrc, JsonDeserializationContext context) {
        System.out.println("ReadingDeserializer.deserialize(" + jsonStr.toString() + ")");

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        if ( jsonStr.isJsonObject() )
        {
            reading.readings = new HashMap<String, Object>();
            for(Map.Entry<String, JsonElement> entry : jsonStr.getAsJsonObject().entrySet()) {
                System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
                reading.readings.put(entry.getKey(), context.deserialize(entry.getValue(), Object.class));

                if ( entry.getKey().equals( "state" ) ) {
                    reading.state = (State) context.deserialize(entry.getValue(), State.class);
                }
                if ( entry.getKey().equals( "CommandAccepted" ) ) {
                    reading.commandAccepted = (State) context.deserialize(entry.getValue(), State.class);
                }
                if ( entry.getKey().equals( "desired-temp" ) ) {
                    reading.desiredTemp = (State) context.deserialize(entry.getValue(), State.class);
                }
                if ( entry.getKey().equals( "measured-temp" ) ) {
                    reading.measuredTemp = (State) context.deserialize(entry.getValue(), State.class);
                }
                if ( entry.getKey().equals( "humidity" ) ) {
                    reading.humidity = (State) context.deserialize(entry.getValue(), State.class);
                }
                if ( entry.getKey().equals( "battery" ) ) {
                    reading.battery = (State) context.deserialize(entry.getValue(), State.class);
                }

                // TODO check what 'value' is (number, string, array) and handle accordingly
            }
            return reading;
        }
        return null;

    }
}
