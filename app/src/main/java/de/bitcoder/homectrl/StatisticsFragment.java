package de.bitcoder.homectrl;

import android.app.Activity;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import de.bitcoder.homectrl.R;
import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.model.server.subelements.HistoryEntry;
import de.fzi.fhemapi.model.server.subelements.State;
import de.fzi.fhemapi.server.FHEMServer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatisticsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public StatisticsFragment() {
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

    private BarChart chart = null;
    private BarChart linesChart = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        System.out.println("onCreateView");
        Resources res = getResources();

        chart = (BarChart) view.findViewById(R.id.chart);
        chart.setChartType(0);
        chart.setShowText(false);
/*
        chart.addItem("Ganymede", 50, res.getColor(R.color.lcars_green_dark));
        for (int i = 0; i < 30; i++ )
        {
            float randomNum = 10 + (int)(Math.random()*90);
            chart.addItem("Ganymede", randomNum, res.getColor(i % 2 == 0 ? R.color.lcars_green : R.color.lcars_green_dark));

        }
        chart.addItem("Ganymede", 50, res.getColor(R.color.lcars_green_dark));
*/
        /*
        final BarChart levels = (BarChart) view.findViewById(R.id.chart2);
        levels.setChartType(1);
        levels.setShowText(false);

        levels.addItem("Ganymede", 50, res.getColor(R.color.lcars_green_dark));
        for (int i = 0; i < 30; i++ )
        {
            float randomNum = 10 + (int)(Math.random()*90);
            levels.addItem("Ganymede", randomNum, res.getColor(i%2==0?R.color.lcars_green:R.color.lcars_green_dark));

        }
        levels.addItem("Ganymede", 50, res.getColor(R.color.lcars_green_dark));
*/
        linesChart = (BarChart) view.findViewById(R.id.chart3);
        linesChart.setChartType(2);
        linesChart.setShowText(false);
/*
        linesChart.addItem("Ganymede", 50, res.getColor(R.color.lcars_green_dark));
        for (int i = 0; i < 30; i++ )
        {
            float randomNum = 10 + (int)(Math.random()*90);
            linesChart.addItem("Ganymede", randomNum, res.getColor(i%2==0?R.color.lcars_green:R.color.lcars_green_dark));

        }
        linesChart.addItem("Ganymede", 50, res.getColor(R.color.lcars_green_dark));
*/
        updateTempHistory();

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

    private ArrayList<HistoryEntry> generateTestData()
    {
        ArrayList<HistoryEntry> history = new ArrayList<HistoryEntry>();

        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:53:45";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:53:45&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:53:25";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 62"; 	entry.text = "2015-09-16T10:53:25&nbsp;&nbsp;BadThermostat_Climate 62.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:51:31";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:51:31&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:51:11";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:51:11&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:49:02";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.1"; 	entry.text = "2015-09-16T10:49:02&nbsp;&nbsp;BadThermostat_Weather 22.1 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:48:42";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:48:42&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:46:19";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:46:19&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:45:59";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:45:59&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:43:22";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:43:22&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:43:02";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:43:02&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:41:14";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:41:14&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:40:54";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:40:54&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:38:52";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:38:52&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:38:32";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:38:32&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:36:15";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:36:15&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:35:55";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:35:55&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:33:24";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:33:24&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:33:04";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:33:04&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:31:22";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:31:22&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:31:02";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:31:02&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:29:06";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:29:06&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:28:46";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:28:46&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:26:36";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:26:36&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:26:16";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:26:16&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:23:51";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:23:51&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:23:31";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:23:31&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:20:51";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:20:51&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:20:31";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T10:20:31&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:18:42";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:18:42&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:18:22";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T10:18:22&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:16:17";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:16:17&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:15:57";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:15:57&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:13:39";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:13:39&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:13:19";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:13:19&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:10:46";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:10:46&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:10:26";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:10:26&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:08:42";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:08:42&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:08:22";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:08:22&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:06:25";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T10:06:25&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:06:05";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:06:05&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:03:52";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T10:03:52&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:03:32";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T10:03:32&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:01:06";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T10:01:06&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T10:00:46";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T10:00:46&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:58:05";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:58:05&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:57:45";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:57:45&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:55:53";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:55:53&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:55:33";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:55:33&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:53:27";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:53:27&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:53:07";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:53:07&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:50:47";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:50:47&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:50:27";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:50:27&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:47:52";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:47:52&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:47:32";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:47:32&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:45:47";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:45:47&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:45:27";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:45:27&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:43:27";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:43:27&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:43:07";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:43:07&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:40:53";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:40:53&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:40:33";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:40:33&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:38:04";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:38:04&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:37:44";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:37:44&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:35:01";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:35:01&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:34:41";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:34:41&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:32:48";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:32:48&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:32:28";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:32:28&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:30:20";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:30:20&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:30:00";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T09:30:00&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:27:38";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:27:38&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:27:18";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T09:27:18&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:24:41";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:24:41&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:24:21";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:24:21&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:22:34";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:22:34&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:22:14";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:22:14&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:20:13";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:20:13&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:19:53";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:19:53&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:17:37";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:17:37&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:17:17";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:17:17&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:14:47";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:14:47&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:14:27";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:14:27&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:12:46";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:12:46&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:12:26";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:12:26&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:10:31";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:10:31&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:10:11";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:10:11&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:08:01";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:08:01&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:07:41";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:07:41&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:05:17";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:05:17&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:04:57";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:04:57&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:02:19";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:02:19&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:01:59";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T09:01:59&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T09:00:10";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T09:00:10&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:59:50";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T08:59:50&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:57:47";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T08:57:47&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:57:27";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 62"; 	entry.text = "2015-09-16T08:57:27&nbsp;&nbsp;BadThermostat_Climate 62.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:55:09";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T08:55:09&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:54:49";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 62"; 	entry.text = "2015-09-16T08:54:49&nbsp;&nbsp;BadThermostat_Climate 62.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:52:17";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T08:52:17&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:51:57";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 63"; 	entry.text = "2015-09-16T08:51:57&nbsp;&nbsp;BadThermostat_Climate 63.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:50:14";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T08:50:14&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:49:54";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 63"; 	entry.text = "2015-09-16T08:49:54&nbsp;&nbsp;BadThermostat_Climate 63.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:47:57";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T08:47:57&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:47:37";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 63"; 	entry.text = "2015-09-16T08:47:37&nbsp;&nbsp;BadThermostat_Climate 63.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:45:26";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T08:45:26&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:45:06";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 63"; 	entry.text = "2015-09-16T08:45:06&nbsp;&nbsp;BadThermostat_Climate 63.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:42:40";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T08:42:40&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:42:20";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 64"; 	entry.text = "2015-09-16T08:42:20&nbsp;&nbsp;BadThermostat_Climate 64.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:39:40";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T08:39:40&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:39:20";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 64"; 	entry.text = "2015-09-16T08:39:20&nbsp;&nbsp;BadThermostat_Climate 64.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:37:29";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T08:37:29&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:37:09";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 65"; 	entry.text = "2015-09-16T08:37:09&nbsp;&nbsp;BadThermostat_Climate 65.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:35:04";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T08:35:04&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:34:44";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 65"; 	entry.text = "2015-09-16T08:34:44&nbsp;&nbsp;BadThermostat_Climate 65.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:32:24";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T08:32:24&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:29:30";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T08:29:30&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:27:26";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T08:27:26&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:27:06";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 67"; 	entry.text = "2015-09-16T08:27:06&nbsp;&nbsp;BadThermostat_Climate 67.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:25:07";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T08:25:07&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:24:47";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 68"; 	entry.text = "2015-09-16T08:24:47&nbsp;&nbsp;BadThermostat_Climate 68.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:22:34";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T08:22:34&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:22:14";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 68"; 	entry.text = "2015-09-16T08:22:14&nbsp;&nbsp;BadThermostat_Climate 68.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:19:46";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T08:19:46&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:19:26";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 66"; 	entry.text = "2015-09-16T08:19:26&nbsp;&nbsp;BadThermostat_Climate 66.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:16:44";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T08:16:44&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:16:24";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 71"; 	entry.text = "2015-09-16T08:16:24&nbsp;&nbsp;BadThermostat_Climate 71.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:14:32";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T08:14:32&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:14:12";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 72"; 	entry.text = "2015-09-16T08:14:12&nbsp;&nbsp;BadThermostat_Climate 72.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:12:05";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T08:12:05&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:11:45";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 72"; 	entry.text = "2015-09-16T08:11:45&nbsp;&nbsp;BadThermostat_Climate 72.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:09:24";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T08:09:24&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:09:04";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 72"; 	entry.text = "2015-09-16T08:09:04&nbsp;&nbsp;BadThermostat_Climate 72.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:06:28";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T08:06:28&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:06:08";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 74"; 	entry.text = "2015-09-16T08:06:08&nbsp;&nbsp;BadThermostat_Climate 74.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:04:21";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T08:04:21&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:04:01";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 75"; 	entry.text = "2015-09-16T08:04:01&nbsp;&nbsp;BadThermostat_Climate 75.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:02:01";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T08:02:01&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T08:01:41";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 76"; 	entry.text = "2015-09-16T08:01:41&nbsp;&nbsp;BadThermostat_Climate 76.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:59:26";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T07:59:26&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:59:06";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 78"; 	entry.text = "2015-09-16T07:59:06&nbsp;&nbsp;BadThermostat_Climate 78.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:56:36";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T07:56:36&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:56:16";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 77"; 	entry.text = "2015-09-16T07:56:16&nbsp;&nbsp;BadThermostat_Climate 77.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:53:33";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T07:53:33&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:53:13";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 74"; 	entry.text = "2015-09-16T07:53:13&nbsp;&nbsp;BadThermostat_Climate 74.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:51:18";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T07:51:18&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:50:58";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 73"; 	entry.text = "2015-09-16T07:50:58&nbsp;&nbsp;BadThermostat_Climate 73.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:48:50";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T07:48:50&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:48:30";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 73"; 	entry.text = "2015-09-16T07:48:30&nbsp;&nbsp;BadThermostat_Climate 73.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:46:06";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T07:46:06&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:45:46";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 75"; 	entry.text = "2015-09-16T07:45:46&nbsp;&nbsp;BadThermostat_Climate 75.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:43:09";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T07:43:09&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:42:49";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 74"; 	entry.text = "2015-09-16T07:42:49&nbsp;&nbsp;BadThermostat_Climate 74.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:41:01";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.7"; 	entry.text = "2015-09-16T07:41:01&nbsp;&nbsp;BadThermostat_Weather 22.7 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:40:41";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 77"; 	entry.text = "2015-09-16T07:40:41&nbsp;&nbsp;BadThermostat_Climate 77.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:38:38";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.6"; 	entry.text = "2015-09-16T07:38:38&nbsp;&nbsp;BadThermostat_Weather 22.6 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:38:18";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 78"; 	entry.text = "2015-09-16T07:38:18&nbsp;&nbsp;BadThermostat_Climate 78.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:36:02";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T07:36:02&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:35:42";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 76"; 	entry.text = "2015-09-16T07:35:42&nbsp;&nbsp;BadThermostat_Climate 76.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:33:10";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T07:33:10&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:32:50";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 70"; 	entry.text = "2015-09-16T07:32:50&nbsp;&nbsp;BadThermostat_Climate 70.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:31:09";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T07:31:09&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:30:49";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 69"; 	entry.text = "2015-09-16T07:30:49&nbsp;&nbsp;BadThermostat_Climate 69.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:28:53";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T07:28:53&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:28:33";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 65"; 	entry.text = "2015-09-16T07:28:33&nbsp;&nbsp;BadThermostat_Climate 65.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:26:22";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T07:26:22&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:26:02";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 62"; 	entry.text = "2015-09-16T07:26:02&nbsp;&nbsp;BadThermostat_Climate 62.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:23:37";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T07:23:37&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:23:17";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T07:23:17&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:20:38";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T07:20:38&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:20:18";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 61"; 	entry.text = "2015-09-16T07:20:18&nbsp;&nbsp;BadThermostat_Climate 61.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:18:28";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T07:18:28&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:18:08";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T07:18:08&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:16:04";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T07:16:04&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:15:44";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T07:15:44&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:13:25";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T07:13:25&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:13:05";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T07:13:05&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:10:32";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T07:10:32&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:10:12";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T07:10:12&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:08:29";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T07:08:29&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:06:11";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T07:06:11&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:05:51";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T07:05:51&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:03:38";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T07:03:38&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:03:18";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T07:03:18&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:00:52";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T07:00:52&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T07:00:32";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T07:00:32&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:57:50";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T06:57:50&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:57:30";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:57:30&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:55:39";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T06:55:39&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:55:19";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T06:55:19&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:53:13";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T06:53:13&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:52:53";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:52:53&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:50:32";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T06:50:32&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:50:12";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:50:12&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:47:37";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T06:47:37&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:47:17";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:47:17&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:45:32";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T06:45:32&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:45:12";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T06:45:12&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:43:12";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T06:43:12&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:42:52";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:42:52&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:40:38";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T06:40:38&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:40:18";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:40:18&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:37:50";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T06:37:50&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:37:30";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:37:30&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:34:47";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T06:34:47&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:34:27";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:34:27&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:32:33";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T06:32:33&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:32:13";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:32:13&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:30:05";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T06:30:05&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:29:45";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:29:45&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:27:23";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.2"; 	entry.text = "2015-09-16T06:27:23&nbsp;&nbsp;BadThermostat_Weather 22.2 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:27:03";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:27:03&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:24:26";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T06:24:26&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:24:06";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:24:06&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:22:19";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T06:22:19&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
/*
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:21:59";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:21:59&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:19:58";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T06:19:58&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:19:38";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:19:38&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:17:22";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T06:17:22&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:17:02";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:17:02&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:14:31";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T06:14:31&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:14:11";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:14:11&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:12:31";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T06:12:31&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:12:11";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:12:11&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:10:15";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T06:10:15&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:09:55";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:09:55&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:07:46";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T06:07:46&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:07:26";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:07:26&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:05:02";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T06:05:02&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:04:42";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:04:42&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:02:03";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T06:02:03&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T06:01:43";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T06:01:43&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:59:54";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:59:54&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:59:34";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:59:34&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:57:31";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:57:31&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:57:11";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:57:11&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:54:53";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:54:53&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:54:33";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:54:33&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:52:01";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:52:01&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:51:41";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:51:41&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:49:58";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:49:58&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:49:38";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:49:38&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:47:41";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:47:41&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:47:21";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:47:21&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:45:10";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:45:10&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:44:50";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:44:50&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:42:24";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:42:24&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:42:04";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:42:04&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:39:24";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:39:24&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:39:04";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:39:04&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:37:13";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:37:13&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:36:53";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:36:53&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:34:48";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:34:48&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:34:28";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:34:28&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:32:08";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:32:08&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:31:48";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:31:48&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:29:14";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:29:14&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:28:54";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:28:54&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:27:10";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:27:10&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:26:50";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:26:50&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:24:51";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:24:51&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:24:31";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:24:31&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:22:18";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:22:18&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:21:58";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:21:58&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:19:30";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:19:30&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:19:10";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:19:10&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:16:28";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:16:28&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:16:08";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:16:08&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:14:15";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:14:15&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:13:55";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:13:55&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:11:48";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:11:48&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:11:28";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T05:11:28&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:09:07";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:09:07&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:08:47";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:08:47&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:06:11";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:06:11&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:05:51";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:05:51&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:04:05";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:04:05&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:03:45";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:03:45&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:01:44";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T05:01:44&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T05:01:24";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T05:01:24&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:59:09";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:59:09&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:58:49";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:58:49&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:56:20";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:56:20&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:56:00";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:56:00&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:54:20";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:54:20&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:54:00";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:54:00&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:52:05";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:52:05&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:51:45";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:51:45&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:49:37";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:49:37&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:49:17";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:49:17&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:46:53";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:46:53&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:46:33";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:46:33&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:43:56";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:43:56&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:43:36";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:43:36&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:41:48";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:41:48&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:41:28";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:41:28&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:39:25";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:39:25&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:39:05";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:39:05&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:36:48";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:36:48&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:36:28";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:36:28&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:33:57";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:33:57&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:33:37";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:33:37&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:31:55";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:31:55&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:31:35";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:31:35&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:29:39";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:29:39&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:29:19";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:29:19&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:27:08";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:27:08&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:26:48";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:26:48&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:24:23";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:24:23&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:24:03";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:24:03&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:21:24";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:21:24&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:21:04";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:21:04&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:19:14";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:19:14&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:18:54";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:18:54&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:16:50";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:16:50&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:16:30";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:16:30&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:14:11";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:14:11&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:13:51";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:13:51&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:11:18";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:11:18&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:10:58";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:10:58&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:09:14";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:09:14&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:08:54";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:08:54&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:06:56";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:06:56&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:06:36";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:06:36&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:04:24";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:04:24&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:04:04";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:04:04&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:01:37";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T04:01:37&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T04:01:17";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T04:01:17&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:58:36";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T03:58:36&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:58:16";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:58:16&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:56:24";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T03:56:24&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:56:04";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:56:04&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:53:58";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T03:53:58&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:53:38";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:53:38&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:51:18";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T03:51:18&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:50:58";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:50:58&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:48:23";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T03:48:23&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:48:03";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:48:03&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:46:17";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T03:46:17&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:45:57";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:45:57&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:43:58";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T03:43:58&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:43:38";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:43:38&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:41:23";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T03:41:23&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:41:03";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:41:03&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:38:35";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.3"; 	entry.text = "2015-09-16T03:38:35&nbsp;&nbsp;BadThermostat_Weather 22.3 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:38:15";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:38:15&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:35:32";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:35:32&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:35:12";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:35:12&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:33:18";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:33:18&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:32:58";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:32:58&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:30:50";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:30:50&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:30:30";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:30:30&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:28:08";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:28:08&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:27:48";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:27:48&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:25:11";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:25:11&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:24:51";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:24:51&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:23:04";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:23:04&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:22:44";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:22:44&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:20:42";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:20:42&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:20:22";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:20:22&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:18:06";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:18:06&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:17:46";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:17:46&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:15:16";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:15:16&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:13:15";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:13:15&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:12:55";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:12:55&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:11:00";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:11:00&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:10:40";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:10:40&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:08:30";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:08:30&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:08:10";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:08:10&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:05:46";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:05:46&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:05:26";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:05:26&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:02:47";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:02:47&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:02:27";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:02:27&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:00:38";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T03:00:38&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T03:00:18";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T03:00:18&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:58:15";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:58:15&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:57:55";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:57:55&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:55:37";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:55:37&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:55:17";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:55:17&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:52:45";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:52:45&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:52:25";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:52:25&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:50:42";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:50:42&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:50:22";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:50:22&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:48:25";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:48:25&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:48:05";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:48:05&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:45:54";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:45:54&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:45:34";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:45:34&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:43:08";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:43:08&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:42:48";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:42:48&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:40:07";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:40:07&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:39:47";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:39:47&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:37:57";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:37:57&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:37:37";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:37:37&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:35:31";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:35:31&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:35:11";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:35:11&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:32:52";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:32:52&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:32:32";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:32:32&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:29:58";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:29:58&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:29:38";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:29:38&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:27:53";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:27:53&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:27:33";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:27:33&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:25:34";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:25:34&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:25:14";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:25:14&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:23:01";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:23:01&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:22:41";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:22:41&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:20:13";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:20:13&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:19:53";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:19:53&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:17:11";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:17:11&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:16:51";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:16:51&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:14:58";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:14:58&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:14:38";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:14:38&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:12:31";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:12:31&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:12:11";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:12:11&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:09:50";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:09:50&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:09:30";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T02:09:30&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:06:54";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:06:54&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:06:34";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:06:34&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:04:48";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:04:48&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:04:28";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:04:28&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:02:27";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T02:02:27&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T02:02:07";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T02:02:07&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:59:52";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:59:52&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:59:32";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:59:32&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:57:02";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:57:02&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:56:42";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:56:42&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:55:02";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:55:02&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:54:42";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:54:42&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:52:48";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:52:48&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:52:28";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:52:28&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:50:19";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:50:19&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:49:59";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:49:59&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:47:36";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:47:36&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:47:16";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:47:16&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:44:38";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:44:38&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:44:18";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:44:18&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:42:30";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:42:30&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:42:10";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:42:10&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:40:07";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:40:07&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:39:47";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T01:39:47&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:37:30";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:37:30&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:37:10";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T01:37:10&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:34:39";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:34:39&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:34:19";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T01:34:19&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:32:37";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:32:37&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:32:17";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:32:17&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:30:21";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:30:21&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:30:01";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:30:01&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:27:50";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:27:50&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:27:30";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:27:30&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:25:05";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.4"; 	entry.text = "2015-09-16T01:25:05&nbsp;&nbsp;BadThermostat_Weather 22.4 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:24:45";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:24:45&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:22:05";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T01:22:05&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:21:45";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:21:45&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:19:56";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T01:19:56&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:19:36";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:19:36&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:17:31";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T01:17:31&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:17:11";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:17:11&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:14:52";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T01:14:52&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:14:32";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:14:32&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:11:59";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T01:11:59&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:11:39";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:11:39&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:09:56";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T01:09:56&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:09:36";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:09:36&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:07:38";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T01:07:38&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:07:18";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:07:18&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:05:05";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T01:05:05&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:04:45";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:04:45&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:02:18";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T01:02:18&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T01:01:58";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T01:01:58&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:59:17";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:59:17&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:58:57";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:58:57&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:57:05";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:57:05&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:56:45";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:56:45&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:54:39";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:54:39&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:54:19";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:54:19&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:51:58";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:51:58&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:51:38";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:51:38&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:49:03";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:49:03&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:48:43";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:48:43&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:46:58";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:46:58&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:46:38";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:46:38&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:44:38";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:44:38&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:44:18";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:44:18&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:42:04";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:42:04&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:41:44";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:41:44&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:39:15";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:39:15&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:38:55";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:38:55&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:36:12";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:36:12&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:35:52";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 60"; 	entry.text = "2015-09-16T00:35:52&nbsp;&nbsp;BadThermostat_Climate 60.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:33:59";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:33:59&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:33:39";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:33:39&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:31:31";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:31:31&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:31:11";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:31:11&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:28:48";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:28:48&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:28:28";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:28:28&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:25:51";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:25:51&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:25:31";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:25:31&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:23:44";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:23:44&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:23:24";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:23:24&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:21:22";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:21:22&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:21:02";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:21:02&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:18:46";	entry.name = "BadThermostat_Weather"; 	entry.val = "temperature: 22.5"; 	entry.text = "2015-09-16T00:18:46&nbsp;&nbsp;BadThermostat_Weather 22.5 °C"; history.add( entry ); }
        { HistoryEntry entry = new HistoryEntry(); entry.timestamp = "2015-09-16T00:18:26";	entry.name = "BadThermostat_Climate"; 	entry.val = "humidity: 59"; 	entry.text = "2015-09-16T00:18:26&nbsp;&nbsp;BadThermostat_Climate 59.0 %"; history.add( entry ); }
*/
        return history;
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


                            for (int i = devWetter.fhemElement.history.size()-1; i >= 0; i-- )
                            {
                                float val = 0;
                                String valStr = devWetter.fhemElement.history.get(i).val;
                                String valType = null;
                                try {
                                    val = Float.parseFloat(valStr);
                                }
                                catch (NumberFormatException nfe)
                                {
                                    // there might be an additional string, like 'temperature: 23'
                                    String[] strings = valStr.split(" ");
                                    if ( strings.length == 2 )
                                    {
                                        valType = strings[0];
                                        val = Float.parseFloat(strings[1]);
                                    }
                                }
                                catch (NullPointerException npe)
                                {

                                }
                                if (valType != null && valType.startsWith("temperature") )
                                {
                                    //System.out.println("Adding temperature: " + valStr + " float: " + val);
                                    //linesChart.addItem("Temperature", val, res.getColor(i % 2 == 0 ? R.color.lcars_green : R.color.lcars_green_dark));
                                }
                                else if (valType != null && valType.startsWith("humidity") )
                                {
                                    //System.out.println("Adding humidity: " + valStr + " float: " + val);
                                    linesChart.addItem("Humidity", val, res.getColor(i % 2 == 0 ? R.color.lcars_green : R.color.lcars_green_dark));
                                }
                                else
                                {
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();

    }
}
