package de.bitcoder.test.util;

import com.google.gson.*;

import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.model.server.subelements.Reading;
import de.fzi.fhemapi.server.core.ReadingDeserializer;

/**
 * Created by thangstoerfer on 13.07.2015.
 */
public class GSONTester {

    public static void main(String[] args) {

        String jsonClimate =
                "{'NAME':'ClimateBad'," +
                        " 'DEF':'HM_TC_IT'," +
                        " 'READINGS': {" +
                        "    'state': {'TIME':'14:25:46', 'VAL':'ok'}, " +
                        "    'desired-temp': {'TIME':'14:25:46', 'VAL':'23'}, " +
                        "    'measured-temp': {'TIME':'14:25:46', 'VAL':'21'}, " +
                        "    'battery': {'TIME':'14:25:46', 'VAL':'ok'}, " +
                        "    'humidity': {'TIME':'14:25:46', 'VAL':'56'} " +
                        "    }" +
                        "}";
        String jsonWeather=
                "{'NAME':'Wetter'," +
                        " 'DEF':'Weather'," +
                        " 'READINGS': {" +
                        "    'state': {'TIME':'15:23:01', 'VAL':'ok'}, " +
                        "    'fc1_condition': 'windy', " +
                        "    'fc2_condition': 'sunny', " +
                        "    'fc3_condition': 'snow', " +
                        "    'temperature': {'TIME':'15:23:01', 'VAL':'21'}, " +
                        "    'pressure': {'TIME':'15:23:01', 'VAL':'510'}, " +
                        "    'humidity': {'TIME':'15:23:01', 'VAL':'56'} " +
                        "    }" +
                        "}";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Reading.class, new ReadingDeserializer());
        Gson gson = gsonBuilder.create();
        DeviceResponse climate = gson.fromJson(jsonClimate, DeviceResponse.class);

        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Reading.class, new ReadingDeserializer());
        gson = gsonBuilder.create();
        DeviceResponse wetter = gson.fromJson(jsonWeather, DeviceResponse.class);

        System.out.println("====================");
        System.out.println("CLIMATE:");
        System.out.println(climate);
        System.out.println(climate.lastState.state.val);
        System.out.println(climate.lastState.state.timestamp);
        System.out.println("====================");
        System.out.println("WETTER:");
        System.out.println(wetter);
        System.out.println(wetter.lastState.state.val);
        System.out.println(wetter.lastState.state.timestamp);
        System.out.println("====================");

    }
}
