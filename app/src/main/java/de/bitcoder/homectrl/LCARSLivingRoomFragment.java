package de.bitcoder.homectrl;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.model.server.MessageResponse;
import de.fzi.fhemapi.server.FHEMServer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LCARSLivingRoomFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LCARSLivingRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LCARSLivingRoomFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView textViewBlindValue = null;
    private TextView textViewLEDRed = null;
    private TextView textViewLEDGreen = null;
    private TextView textViewLEDBlue = null;

    private int red = 0;
    private int green = 0;
    private int blue = 0;

    private int LEDclickStep = 5;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LCARSLivingRoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LCARSLivingRoomFragment newInstance(String param1, String param2) {
        LCARSLivingRoomFragment fragment = new LCARSLivingRoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LCARSLivingRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lcarslivingroom, container, false);
        System.out.println("onCreateView");

        textViewBlindValue = (TextView) view.findViewById(R.id.textView_WzRolladenValue);
        textViewLEDRed = (TextView) view.findViewById(R.id.textView_LED_RED);
        textViewLEDGreen = (TextView) view.findViewById(R.id.textView_LED_GREEN);
        textViewLEDBlue = (TextView) view.findViewById(R.id.textView_LED_BLUE);



        Button btnLEDon= (Button) view.findViewById(R.id.button_LED_ON);
        btnLEDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    private String result;

                    @Override
                    public void run() {
                        try {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("on", "");
                            final MessageResponse resp = FHEMServer.getInstance().setDevice(LCARSConfig.WZ_LED, params);

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
        Button btnLEDoff= (Button) view.findViewById(R.id.button_LED_OFF);
        btnLEDoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    private String result;

                    @Override
                    public void run() {
                        try {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("off", "");
                            final MessageResponse resp = FHEMServer.getInstance().setDevice(LCARSConfig.WZ_LED, params);

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



        Button btnLampOn= (Button) view.findViewById(R.id.button_WzStehlampe_ON);
        btnLampOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    private String result;

                    @Override
                    public void run() {
                        try {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("on", "");
                            final MessageResponse resp = FHEMServer.getInstance().setDevice(LCARSConfig.WZ_Stehlampe, params);

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
        Button btnLampOff= (Button) view.findViewById(R.id.button_WzStehlampe_OFF);
        btnLampOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    private String result;

                    @Override
                    public void run() {
                        try {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("off", "");
                            final MessageResponse resp = FHEMServer.getInstance().setDevice(LCARSConfig.WZ_Stehlampe, params);

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

        Button btnBlindUp = (Button) view.findViewById(R.id.button_WzRolladenUp);
        btnBlindUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    private String result;

                    @Override
                    public void run() {
                        try {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("off", "");
                            final MessageResponse resp = FHEMServer.getInstance().setDevice(LCARSConfig.WZ_Rolladen, params);

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
        Button btnBlindDown = (Button) view.findViewById(R.id.button_WzRolladenDown);
        btnBlindDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    private String result;

                    @Override
                    public void run() {
                        try {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("on", "");
                            final MessageResponse resp = FHEMServer.getInstance().setDevice(LCARSConfig.WZ_Rolladen, params);

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
        Button btnBlindStop = (Button) view.findViewById(R.id.button_WzRolladenStop);
        btnBlindStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    private String result;

                    @Override
                    public void run() {
                        try {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("stop", "");
                            final MessageResponse resp = FHEMServer.getInstance().setDevice(LCARSConfig.WZ_Rolladen, params);

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
        Button btnRedUp = (Button) view.findViewById(R.id.button_LED_RED_UP);
        btnRedUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red = Math.min(100, red + LEDclickStep);
                updateLEDValues();
                sendColor();
            } // onClick
        });
        Button btnRedDown = (Button) view.findViewById(R.id.button_LED_RED_DOWN);
        btnRedDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red = Math.max(0, red - LEDclickStep);
                updateLEDValues();
                sendColor();
            } // onClick
        });
        Button btnGreenUp = (Button) view.findViewById(R.id.button_LED_GREEN_UP);
        btnGreenUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                green = Math.min(100, green + LEDclickStep);
                updateLEDValues();
                sendColor();
            } // onClick
        });
        Button btnGreenDown = (Button) view.findViewById(R.id.button_LED_GREEN_DOWN);
        btnGreenDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                green = Math.max(0, green - LEDclickStep);
                updateLEDValues();
                sendColor();
            } // onClick
        });

        Button btnBlueUp = (Button) view.findViewById(R.id.button_LED_BLUE_UP);
        btnBlueUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blue = Math.min(100, blue + LEDclickStep);
                updateLEDValues();
                sendColor();
            } // onClick
        });
        Button btnBlueDown = (Button) view.findViewById(R.id.button_LED_BLUE_DOWN);
        btnBlueDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blue = Math.max(0, blue - LEDclickStep);
                updateLEDValues();
                sendColor();
            } // onClick
        });

        updateLEDValues();

        return view;
    }

    private void updateLEDValues()
    {
        textViewLEDRed.setText(red + "%");
        textViewLEDGreen.setText(green + "%");
        textViewLEDBlue.setText(blue + "%");
    }

    private void sendColor()
    {
        final String hex = String.format("%02X%02X%02X", red*0xFF/100, green*0xFF/100, blue*0xFF/100);
        //System.out.println("red = " + red + " green = " + green + " blue = " + blue + " => hex = " + hex);

        Thread thread = new Thread(new Runnable() {
            private String result;

            @Override
            public void run() {
                try {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("RGB", hex);
                    final MessageResponse resp = FHEMServer.getInstance().setDevice(LCARSConfig.WZ_LED, params);

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

    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        System.out.println("onViewStateRestored");

/*
        Thread thread = new Thread(new Runnable() {
            private String result;

            @Override
            public void run() {
                try {

                    System.out.println("devWzBlind!!!!!!! ");
//                    final DeviceResponse devWzBlind = FHEMServer.getInstance().getDevice(LCARSConfig.WZ_Rolladen);
//                    System.out.println("devWzBlind: " + devWzBlind.toString());
//                    System.out.println("devWzBlind.state: " + devWzBlind.state);
                    //System.out.println("devWzBlind.lastState.battery:      " + devWzBlind.lastState.battery.val);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //textViewBlindValue.setText(devWzBlind.lastState.measuredTemp.val + "°");
                            textViewBlindValue.setText("11%");

                            //Toast.makeText(getActivity().getApplicationContext(), devHM_SEC.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (java.net.ConnectException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
*/
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
