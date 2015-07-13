package de.bitcoder.test.util;

import com.google.gson.*;

import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.model.server.subelements.Reading;
import de.fzi.fhemapi.model.server.subelements.State;
import de.fzi.fhemapi.server.core.ReadingDeserializer;

/**
 * Created by thangstoerfer on 13.07.2015.
 */
public class GSONTester {

    public static void main(String[] args) {

        String wttr = "{" +
                "    'LOCATION': '15002754'," +
                "    'NAME': 'Wetter'," +
                "    'DEF': '15002754 3600 de'," +
                "    'READINGS': {" +
                "        'wind_direction': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '220'" +
                "        }," +
                "        'visibility': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': ' '" +
                "        }," +
                "        'pressure': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '1019'" +
                "        }," +
                "        'state': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'T: 23  H: 52  W: 18  P: 1019'" +
                "        }," +
                "        'fc5_code': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '32'" +
                "        }," +
                "        'wind_chill': {" +
                "            'TIME': '2015 -07-12 11:47:35'," +
                "            'VAL': '23'" +
                "        }," +
                "        'fc1_low_c': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '14'" +
                "        }," +
                "        'fc1_day_of_week': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'So'" +
                "        }," +
                "        'fc3_condition': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'heit er'" +
                "        }," +
                "        'fc4_code': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '32'" +
                "        }," +
                "        'fc4_high_c': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '30'" +
                "        }," +
                "        'code': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '28'" +
                "        }," +
                "        'temperature': {" +
                "            'TIME': '2015-07-12 11:4 7:35'," +
                "            'VAL': '23'" +
                "        }," +
                "        'fc2_low_c': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '15'" +
                "        }," +
                "        'fc2_code': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '26'" +
                "        }," +
                "        'fc1_high_c': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '27'" +
                "        }," +
                "        'fc2_day_of_week': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'Mo'" +
                "        }," +
                "        'pressure_trend': {" +
                "            'TIME': '2015-07-12 09:47:34'," +
                "            'VAL': '1'" +
                "        }," +
                "        'fc2_condition': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'wolkig'" +
                "        }," +
                "        'fc1_icon': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'partly_cloudy'" +
                "        }," +
                "        'current_date_time': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '12 Jul 2015 11:00 am CEST'" +
                "        }," +
                "        'day_of_week': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'So'" +
                "        }," +
                "        'wind_speed': {" +
                "            'TIME': '2015-07-12 11 :47:35'," +
                "            'VAL': '18'" +
                "        }," +
                "        'fc4_day_of_week': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'Mi'" +
                "        }," +
                "        'pressure_trend_txt': {" +
                "            'TIME': '2015-07-12 09:47:34'," +
                "            'VAL': 'steigend'" +
                "        }," +
                "        'fc5_day_of_week': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VA L': 'Do'" +
                "        }," +
                "        'fc5_low_c': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '18'" +
                "        }," +
                "        'fc4_condition': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'sonnig'" +
                "        }," +
                "        'icon': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'mostlycloudy'" +
                "        }," +
                "        'fc3_low_c': {" +
                "            'TI ME': '2015-07-12 11:47:35'," +
                "            'VAL': '14'" +
                "        }," +
                "        'fc4_low_c': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '15'" +
                "        }," +
                "        'fc4_icon': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'sunny'" +
                "        }," +
                "        'fc1_condition': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'teilweise wolkig'" +
                "        }," +
                "        'wind_condition': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'Wind: SW 18 km/h'" +
                "        }," +
                "        'wind': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '18'" +
                "        }," +
                "        'fc3_day_of_week': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'Di'" +
                "        }," +
                "        'fc3_code': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '34'" +
                "        }," +
                "        'city': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'Remchingen, Germany'" +
                "        }," +
                "        'fc3_icon': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'mostly_sunny'" +
                "        }," +
                "        'fc3_high_c': {" +
                "            'T IME': '2015-07-12 11:47:35'," +
                "            'VAL': '29'" +
                "        }," +
                "        'fc5_high_c': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '34'" +
                "        }," +
                "        'fc1_code': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '30'" +
                "        }," +
                "        'humidity': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '52'" +
                "        }," +
                "        'fc5_icon': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'sunny'" +
                "        }," +
                "        'fc2_high_c': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '26'" +
                "        }," +
                "        'temp_f': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '73'" +
                "        }," +
                "        'pressure_trend_sym': {" +
                "            'TIME': '2015-07- 12 09:47:34'," +
                "            'VAL': '+'" +
                "        }," +
                "        'temp_c': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': '23'" +
                "        }," +
                "        'fc5_condition': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'sonnig'" +
                "        }," +
                "        'fc2_icon': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'cloudy'" +
                "        }," +
                "        'condi tion': {" +
                "            'TIME': '2015-07-12 11:47:35'," +
                "            'VAL': 'Ã¼berwiegend wolkig'" +
                "        }" +
                "    }," +
                "    'TYPE': 'Weather'," +
                "    'UNITS': 'c'," +
                "    'NR': '45'," +
                "    'ATTRIBUTES': 'verbose:0,1,2,3,4,5 room group comment alias eventMap userReadings localicons eve nt-on-change-reading event-on-update-reading event-aggregator event-min-interval stateFormat cmdIcon devStateIcon devStateStyle deviceType icon sortby webCmd widgetOverride userattr'," +
                "    'STATE': 'T: 23  H : 52  W: 18  P: 1019'," +
                "    'fhem': {" +
                "        'interfaces': 'temperature;humidity;wind'" +
                "    }," +
                "    'SETS': 'update'," +
                "    'guid': '45'," +
                "    'LANG': 'de'," +
                "    'EATTR': {" +
                "        'room': 'Wettervorhersage'" +
                "    }," +
                "    'INTERVAL': '3600'" +
                "}";

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
        Gson gson = null;
/*
        gsonBuilder.registerTypeAdapter(Reading.class, new ReadingDeserializer());
        gson = gsonBuilder.create();
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
*/
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Reading.class, new ReadingDeserializer());
        gson = gsonBuilder.create();
        DeviceResponse wetterResp = gson.fromJson(wttr, DeviceResponse.class);

        System.out.println("====================");
        System.out.println("WETTER:");
        System.out.println(wetterResp);
        System.out.println(wetterResp.lastState.state.val);
        System.out.println(wetterResp.lastState.state.timestamp);
        State s = (State)wetterResp.lastState.readings.get("wind_direction");

        System.out.println("wind_direction: " + s.val);

        System.out.println("====================");


    }
}
