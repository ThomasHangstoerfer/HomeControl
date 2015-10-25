package de.bitcoder.homectrl;


import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.server.FHEMServer;

public class LCARSBadFragment extends Fragment {

    TextView textView_measuredTemp = null;
    TextView textView_desiredTemp = null;
    TextView textView_humidity = null;
    TextView textView_battery = null;
    TextView textView_window = null;

    private BarChart linesChart = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lcars_bad, container, false);
        System.out.println("onCreateView");

        textView_measuredTemp = (TextView) view.findViewById(R.id.textView_measuredTemp);
        textView_desiredTemp = (TextView) view.findViewById(R.id.textView_desiredTemp);
        textView_humidity = (TextView) view.findViewById(R.id.textView_humidity);
        textView_battery = (TextView) view.findViewById(R.id.textView_battery);
        textView_window = (TextView) view.findViewById(R.id.textView_window);

        linesChart = (BarChart) view.findViewById(R.id.chartTemp);
        linesChart.setChartType(2);
        linesChart.setShowText(false);

        return view;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        System.out.println("onViewStateRestored");
        updateAll();

        updateTempHistory();
    }

    private void updateAll()
    {
        System.out.println("LCARSBadFragment::updateAll()");

        Thread thread = new Thread(new Runnable() {
            private String result;

            @Override
            public void run() {
                try {

                    final DeviceResponse devHM_SEC = FHEMServer.getInstance().getDevice(LCARSConfig.Bad_Fenstersensor);
                    System.out.println("devHM_SEC: " + devHM_SEC.toString());
                    System.out.println("devHM_SEC.state: " + devHM_SEC.state);

                    final DeviceResponse devClimate = FHEMServer.getInstance().getDevice(LCARSConfig.Bad_Thermostat_Climate);
                    System.out.println("devClimate: " + devClimate.toString());
                    if ( !devClimate.type.equals("Error") )
                    {

                        System.out.println("devClimate.state: " + devClimate.state);
                        System.out.println("devClimate.lastState.desiredTemp:  " + devClimate.lastState.desiredTemp.val);
                        System.out.println("devClimate.lastState.measuredTemp: " + devClimate.lastState.measuredTemp.val);
                        System.out.println("devClimate.lastState.humidity:     " + devClimate.lastState.humidity.val);
                        System.out.println("devClimate.lastState.battery:      " + devClimate.lastState.battery.val);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                textView_measuredTemp.setText(devClimate.lastState.measuredTemp.val+"°");
                                textView_desiredTemp.setText(devClimate.lastState.desiredTemp.val+"°");
                                textView_humidity.setText(devClimate.lastState.humidity.val+"%");
                                textView_battery.setText(devClimate.lastState.battery.val);
                                textView_window.setText(devHM_SEC.state);

                                //Toast.makeText(getActivity().getApplicationContext(), devHM_SEC.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } catch (java.net.ConnectException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();

    }


    private void updateTempHistory()
    {
        System.out.println("LCARSWeatherFragment::updateWeather()");

        Thread thread = new Thread(new Runnable() {
            private String result;

            @Override
            public void run() {
                try {

                    final DeviceResponse devWetter = FHEMServer.getInstance().getDevice(LCARSConfig.BadHistory);
                    System.out.println("devWetter: " + devWetter.toString());
                    System.out.println("devWetter.state: " + devWetter.state);
                    //devWetter.fhemElement.history.get()
                    //devWetter.fhemElement.history.size()
                    //System.out.println("devWetter.lastState.readings = " + devWetter.lastState.readings.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Resources res = getResources();


                            linesChart.addItem("Ganymede", 50, res.getColor(R.color.lcars_green_dark));


                            //devWetter.fhemElement.history = generateTestData();


                            for (int i = devWetter.fhemElement.history.size() - 1; i >= 0; i--) {
                                float val = 0;
                                String valStr = devWetter.fhemElement.history.get(i).val;
                                String valType = null;
                                try {
                                    val = Float.parseFloat(valStr);
                                } catch (NumberFormatException nfe) {
                                    // there might be an additional string, like 'temperature: 23'
                                    String[] strings = valStr.split(" ");
                                    if (strings.length == 2) {
                                        valType = strings[0];
                                        val = Float.parseFloat(strings[1]);
                                    }
                                } catch (NullPointerException npe) {

                                }
                                if (valType != null && valType.startsWith("temperature")) {
                                    //System.out.println("Adding temperature: " + valStr + " float: " + val);
                                    //linesChart.addItem("Temperature", val, res.getColor(i % 2 == 0 ? R.color.lcars_green : R.color.lcars_green_dark));
                                } else if (valType != null && valType.startsWith("humidity")) {
                                    //System.out.println("Adding humidity: " + valStr + " float: " + val);
                                    linesChart.addItem("Humidity", val, res.getColor(i % 2 == 0 ? R.color.lcars_green : R.color.lcars_green_dark));
                                } else {
                                    //System.out.println("Adding " + valStr + " float: " + val);
                                    linesChart.addItem("temp", val, res.getColor(i % 2 == 0 ? R.color.lcars_green : R.color.lcars_green_dark));
                                }
                                //linesChart.addItem(valType, val, res.getColor(i % 2 == 0 ? R.color.lcars_green : R.color.lcars_green_dark));

                            }
                            linesChart.addItem("Ganymede", 50, res.getColor(R.color.lcars_green_dark));

                        }
                    });

                } catch (java.net.ConnectException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();

    }
}