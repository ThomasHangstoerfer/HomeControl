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

import de.fzi.fhemapi.model.server.MessageResponse;
import de.fzi.fhemapi.server.FHEMServer;

public class LCARSPartyFragment extends Fragment {

    Calendar c = Calendar.getInstance();
    double tempValue = 18.0; // init-value


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
        View view = inflater.inflate(R.layout.fragment_lcars_party, container, false);
        System.out.println("onCreateView");

        final LCARSButton btnSetPartyMode = (LCARSButton) view.findViewById(R.id.button_set);
        btnSetPartyMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    private String result;

                    @Override
                    public void run() {
                        try {
                            // set CUL_HM_HM_CC_RT_DN_2DD63F_Clima controlParty 19 31.01.15 9:30 31.01.15 16:00

                            String param = tempValue + " 31.01.15 9:30 " + // start-date of party-mode
                                    Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "." +
                                    Integer.toString(c.get(Calendar.MONTH)+1) + "." +
                                    Integer.toString(c.get(Calendar.YEAR)).substring(2) + " " +
                                    Integer.toString(c.get(Calendar.HOUR_OF_DAY)) + ":" +
                                    Integer.toString(c.get(Calendar.MINUTE));
                            System.out.println("param: " + param);

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("controlParty", param);
                            final MessageResponse resp = FHEMServer.getInstance().setDevice(LCARSConfig.Bad_CC_RT_Clima, params);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btnSetPartyMode.showResult( resp.isTrue() );
                                    Toast.makeText(getActivity().getApplicationContext(), resp.toString(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        } catch (java.net.ConnectException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                thread.start();
            } // onClick
        });

        Button btnClrPartyMode = (Button) view.findViewById(R.id.button_clear);
        btnClrPartyMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    private String result;

                    @Override
                    public void run() {
                        try {
                            // set CUL_HM_HM_CC_RT_DN_2DD63F_Clima controlParty 19 31.01.15 9:30 31.01.15 16:00
                            String param = "19.0 21.08.07 10:00 30.07.10 10:00 ";

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("controlParty", param);
                            final MessageResponse resp = FHEMServer.getInstance().setDevice(LCARSConfig.Bad_CC_RT_Clima, params);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity().getApplicationContext(), resp.toString(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        } catch (java.net.ConnectException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                thread.start();
            } // onClick
        });

        Button dayUp = (Button) view.findViewById(R.id.buttonDayUp);
        dayUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.DAY_OF_MONTH, 1);
                updateAll();
            }
        });
        Button dayDown = (Button) view.findViewById(R.id.buttonDayDown);
        dayDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.DAY_OF_MONTH, -1);
                updateAll();
            }
        });
        Button monthUp = (Button) view.findViewById(R.id.buttonMonthUp);
        monthUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.MONTH, 1);
                updateAll();
            }
        });
        Button monthDown = (Button) view.findViewById(R.id.buttonMonthDown);
        monthDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.MONTH, -1);
                updateAll();
            }
        });
        Button hourUp = (Button) view.findViewById(R.id.buttonHourUp);
        hourUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.HOUR_OF_DAY, 1);
                updateAll();
            }
        });
        Button hourDown = (Button) view.findViewById(R.id.buttonHourDown);
        hourDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.HOUR_OF_DAY, -1);
                updateAll();
            }
        });
        Button minUp = (Button) view.findViewById(R.id.buttonMinuteUp);
        minUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Cal1: " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));

                int i = c.get(Calendar.MINUTE);
                //min.setText(Integer.toString(i<30?30:00));
                //int h = c.get(Calendar.HOUR_OF_DAY);
                if ( i < 30 )
                {
                    c.set(Calendar.MINUTE, 30);
                }
                else
                {
                    c.add(Calendar.HOUR_OF_DAY, 1);
                    c.set(Calendar.MINUTE, 00);
                }
                System.out.println("Cal2: " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));

                updateAll();
            }
        });
        Button minDown = (Button) view.findViewById(R.id.buttonMinuteDown);
        minDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("1Cal: " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
                int i = c.get(Calendar.MINUTE);
                if ( i == 0 )
                {
                    c.add(Calendar.MINUTE, -30);
                }
                else if ( i > 0  && i <= 30  )
                {
                    c.set(Calendar.MINUTE, 00);
                }
                else if ( i > 30 )
                {
                    c.set(Calendar.MINUTE, 30);
                }
                System.out.println("2Cal: " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
                updateAll();
            }
        });
        Button tempUp = (Button) view.findViewById(R.id.buttonTempUp);
        tempUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempValue += 0.5;
                updateAll();
            }
        });
        Button tempDown = (Button) view.findViewById(R.id.buttonTempDown);
        tempDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempValue -= 0.5;
                updateAll();
            }
        });
        //updateAll();

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
        System.out.println("updateAll()1: " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));

        int i = c.get(Calendar.MINUTE);
        if ( i > 0 && i < 30 )
        {
            c.set(Calendar.MINUTE, 30);
        }
        else if ( i > 30 )
        {
            c.add(Calendar.HOUR_OF_DAY, 1);
            c.set(Calendar.MINUTE, 00);
        }
        System.out.println("updateAll()2: " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));

        TextView day = (TextView) getView().findViewById(R.id.textView_day);
        day.setText(String.format("%02d.", c.get(Calendar.DAY_OF_MONTH)));

        TextView month = (TextView) getView().findViewById(R.id.textView_month);
        month.setText(String.format("%02d.", c.get(Calendar.MONTH)+1));

        TextView hour = (TextView) getView().findViewById(R.id.textView_hour);
        hour.setText(String.format("%02d:", c.get(Calendar.HOUR_OF_DAY)));

        TextView min = (TextView) getView().findViewById(R.id.textView_minute);
        min.setText(String.format("%02d", c.get(Calendar.MINUTE)));

        TextView temp = (TextView) getView().findViewById(R.id.textView_degree);
        temp.setText(String.format("%.1f°", tempValue));

    }
}