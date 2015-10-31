package de.bitcoder.homectrl;

import de.fzi.fhemapi.server.FHEMServer;

/**
 * Created by thomas on 09.06.15.
 */
public class LCARSConfig {

    public static final String serverIp = "192.168.178.27"; // pi
    //public static final String serverIp = "192.168.178.44"; // vaio
    //public static final String serverIp = "10.68.113.179"; // fujitsu
    public static final int serverPort = 7073; // Port of TheOpenTransporter

    // device-names in FHEM
    public static final String Bad_CC_RT_Clima = "BadHeizung_Clima";
    public static final String Bad_Fenstersensor = "BadFenster";
    public static final String Bad_Thermostat_Climate = "BadThermostat_Climate";

    public static final String WZ_Stehlampe = "WzStehlampe";
    public static final String WZ_LED       = "LED";
    public static final String WZ_LEDswitch = "LEDswitch";
    public static final String WZ_Rolladen  = "WzRolladen";

    public static final String Wetter = "Wetter";

    public static final String BadHistory = "BadHist";

    public static int LEDclickStep = 10;
}
