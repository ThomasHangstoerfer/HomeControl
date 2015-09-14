package de.bitcoder.homectrl;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.model.server.subelements.State;
import de.fzi.fhemapi.server.FHEMServer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LCARSWeatherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LCARSWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LCARSWeatherFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView textView_fc1_day = null;
    TextView textView_fc2_day = null;
    TextView textView_fc3_day = null;
    TextView textView_fc4_day = null;

    ImageView imageView_fc1_icon = null;
    ImageView imageView_fc2_icon = null;
    ImageView imageView_fc3_icon = null;
    ImageView imageView_fc4_icon = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LCARSSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LCARSWeatherFragment newInstance(String param1, String param2) {
        LCARSWeatherFragment fragment = new LCARSWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LCARSWeatherFragment() {
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
        View view = inflater.inflate(R.layout.fragment_lcarsweather, container, false);
        textView_fc1_day = (TextView) view.findViewById(R.id.textView_fc1_day);
        textView_fc2_day = (TextView) view.findViewById(R.id.textView_fc2_day);
        textView_fc3_day = (TextView) view.findViewById(R.id.textView_fc3_day);
        textView_fc4_day = (TextView) view.findViewById(R.id.textView_fc4_day);

        imageView_fc1_icon = (ImageView) view.findViewById(R.id.imageView_fc1_icon);
        imageView_fc2_icon = (ImageView) view.findViewById(R.id.imageView_fc2_icon);
        imageView_fc3_icon = (ImageView) view.findViewById(R.id.imageView_fc3_icon);
        imageView_fc4_icon = (ImageView) view.findViewById(R.id.imageView_fc4_icon);

        return view;

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

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        System.out.println("onViewStateRestored");
        updateWeather();
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


    private void updateWeather()
    {
        System.out.println("LCARSWeatherFragment::updateWeather()");

        Thread thread = new Thread(new Runnable() {
            private String result;

            @Override
            public void run() {
                try {

                    final DeviceResponse devWetter = FHEMServer.getInstance().getDevice(LCARSConfig.Wetter);
                    System.out.println("devWetter: " + devWetter.toString());
                    System.out.println("devWetter.state: " + devWetter.state);
                    //System.out.println("devWetter.lastState.readings = " + devWetter.lastState.readings.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            State state_fc1_dow = (State)devWetter.lastState.readings.get("fc1_day_of_week");
                            textView_fc1_day.setText(state_fc1_dow.val);
                            State state_fc2_dow = (State)devWetter.lastState.readings.get("fc2_day_of_week");
                            textView_fc2_day.setText(state_fc2_dow.val);
                            State state_fc3_dow = (State)devWetter.lastState.readings.get("fc3_day_of_week");
                            textView_fc3_day.setText(state_fc3_dow.val);
                            State state_fc4_dow = (State)devWetter.lastState.readings.get("fc4_day_of_week");
                            textView_fc4_day.setText(state_fc4_dow.val);
/*
                            fc3_condition
                                    fc1_low_c
                                    fc1_hight_c
*/
                            //if(Drawable.createFromPath(drawableFile.getAbsolutePath())!=null){

                             //  State state_fc1_icon = (State)devWetter.lastState.readings.get("fc4_icon");
                            //imageView_fc1_icon.setImageURI("file://res/drawable/weather/cloudy.png");
                            //imageView_fc1_icon.setImageDrawable(Drawable.createFromPath("/app/src/res/drawable/weather/cloudy.png"));

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
