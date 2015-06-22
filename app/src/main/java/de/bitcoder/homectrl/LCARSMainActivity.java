package de.bitcoder.homectrl;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Calendar;

import de.bitcoder.homectrl.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LCARSMainActivity extends Activity
        implements StatisticsFragment.OnFragmentInteractionListener,
        LCARSSettingsFragment.OnFragmentInteractionListener,
        LCARSLivingRoomFragment.OnFragmentInteractionListener
{

        public void onFragmentInteraction(Uri uri)
        {

        }

    Intent mainActIntent = null;
    LCARSPartyFragment mPartyFragment = null;
    LCARSBadFragment mBadFragment = null;
    StatisticsFragment mStatisticsFragment = null;
    LCARSSettingsFragment mSettingsFragment = null;
    LCARSLivingRoomFragment mLivingRoomFragment = null;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/lcarsgtj3.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        mainActIntent = new Intent(this, MainActivity.class);

        setContentView(R.layout.activity_lcarsmain);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    /*.add(R.id.container, new LCARSPartyFragment()*/
                    .add(R.id.container, new LCARSLivingRoomFragment())
                    .commit();
        }


        Button btnParty = (Button) findViewById(R.id.button_party);
        btnParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPartyFragment == null) {
                    mPartyFragment = new LCARSPartyFragment();
                }
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_NONE)
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout/*, R.anim.fadein_delay, R.anim.fadeout_delay*/)
                        .replace(R.id.container, mPartyFragment)
                        .commit();
            }
        });
        Button btnBad = (Button) findViewById(R.id.button_bad);
        btnBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mBadFragment == null ) {
                    mBadFragment = new LCARSBadFragment();
                }
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_NONE)
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout/*, R.anim.fadein_delay, R.anim.fadeout_delay*/)
                        .replace(R.id.container, mBadFragment)
                        .commit();
            }
        });

        Button btnLivRoom = (Button) findViewById(R.id.button_livingroom);
            btnLivRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mLivingRoomFragment == null ) {
                    mLivingRoomFragment = new LCARSLivingRoomFragment();
                }
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_NONE)
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout/*, R.anim.fadein_delay, R.anim.fadeout_delay*/)
                        .replace(R.id.container, mLivingRoomFragment)
                        .commit();
            }
        });



        Button btnStatistics = (Button) findViewById(R.id.button_statistics);
        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mStatisticsFragment == null ) {
                    mStatisticsFragment = new StatisticsFragment();
                }
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_NONE)
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout/*, R.anim.fadein_delay, R.anim.fadeout_delay*/)
                        .replace(R.id.container, mStatisticsFragment)
                        .commit();
            }
        });
        Button btnSettings = (Button) findViewById(R.id.button_settings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mSettingsFragment == null )
                    mSettingsFragment = new LCARSSettingsFragment();
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_NONE)
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout/*, R.anim.fadein_delay, R.anim.fadeout_delay*/)
                        .replace(R.id.container, mSettingsFragment)
                        .commit();
            }
        });
        Button btnDev = (Button) findViewById(R.id.button_dev);
        btnDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainActIntent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lcarsmain_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_lcars_party, container, false);
            return rootView;
        }
    }
}
