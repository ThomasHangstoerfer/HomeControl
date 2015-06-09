package de.bitcoder.homectrl;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.bitcoder.homectrl.dummy.LCARSConfig;
import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.model.server.MessageResponse;
import de.fzi.fhemapi.server.FHEMServer;

public class LCARSBadFragment extends Fragment {

    String devName_Clima = "CUL_HM_HM_SEC_SC_2_2ADBA7"; // CUL_HM_HM_CC_RT_DN_2DD63F_WindowRec,

    TextView textView_measuredTemp = null;
    TextView textView_desiredTemp = null;
    TextView textView_humidity = null;
    TextView textView_battery = null;
    TextView textView_window = null;

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

        return view;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        System.out.println("onViewStateRestored");
        updateAll();
    }

    private void updateAll()
    {
        System.out.println("LCARSBadFragment::updateAll()");

        Thread thread = new Thread(new Runnable() {
            private String result;

            @Override
            public void run() {
                try {

                    FHEMServer s = new FHEMServer(LCARSConfig.serverIp, LCARSConfig.serverPort);

                    final DeviceResponse devHM_SEC = s.getDevice("CUL_HM_HM_SEC_SC_2_2ADBA7");
                    System.out.println("devHM_SEC: " + devHM_SEC.toString());
                    System.out.println("devHM_SEC.state: " + devHM_SEC.state);

                    final DeviceResponse devClimate = s.getDevice("CUL_HM_HM_TC_IT_WM_W_EU_34B153_Climate");
                    System.out.println("devClimate: " + devClimate.toString());
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

                } catch (java.net.ConnectException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();

    }
}