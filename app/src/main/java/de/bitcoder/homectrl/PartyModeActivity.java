package de.bitcoder.homectrl;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bitcoder.homectrl.R;
import de.bitcoder.homectrl.dummy.LCARSConfig;
import de.fzi.fhemapi.model.devicetypes.Device;
import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.model.server.MessageResponse;
import de.fzi.fhemapi.server.DeviceManager;
import de.fzi.fhemapi.server.FHEMServer;

//public class PartyModeActivity extends ActionBarActivity {
public class PartyModeActivity extends Activity {

    Calendar c = Calendar.getInstance();
    double tempValue = 18.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_party_mode);

        //View decorView = getWindow().getDecorView();
        //int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        //decorView.setSystemUiVisibility(uiOptions);

        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        //        ActionBar actionBar = getActionBar();
        //        actionBar.hide();

        //int seconds = c.get(Calendar.SECOND);

        updateAll();

        Button btnSetPartyMode = (Button) findViewById(R.id.buttonSet);
        btnSetPartyMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    private String result;

                    @Override
                    public void run() {
                        try {

                            String degree = "19";
                            // set CUL_HM_HM_CC_RT_DN_2DD63F_Clima controlParty 19 31.01.15 9:30 31.01.15 16:00

                            String param =  degree + " 31.01.15 9:30 " +
                                    Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "." +
                                    Integer.toString(c.get(Calendar.MONTH)) + "." +
                                    Integer.toString(c.get(Calendar.YEAR)).substring(2) + " " +
                                    Integer.toString(c.get(Calendar.HOUR_OF_DAY)) + ":" +
                                    Integer.toString(c.get(Calendar.MINUTE));
                            System.out.println("param: " + param);

                            FHEMServer s = new FHEMServer(LCARSConfig.serverIp, LCARSConfig.serverPort);

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("controlParty", param);
                            final MessageResponse  resp = s.setDevice(LCARSConfig.Bad_CC_RT_Clima, params);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), resp.toString(),
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

        Button btnClrPartyMode = (Button) findViewById(R.id.buttonClear);
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

                            FHEMServer s = new FHEMServer(LCARSConfig.serverIp, LCARSConfig.serverPort);

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("controlParty", param);
                            final MessageResponse  resp = s.setDevice(LCARSConfig.Bad_CC_RT_Clima, params);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), resp.toString(),
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

        ImageButton dayUp = (ImageButton) findViewById(R.id.buttonDayUp);
        dayUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.DAY_OF_MONTH, 1);
                updateAll();
            }
        });
        ImageButton dayDown = (ImageButton) findViewById(R.id.buttonDayDown);
        dayDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.DAY_OF_MONTH, -1);
                updateAll();
            }
        });
        ImageButton monthUp = (ImageButton) findViewById(R.id.buttonMonthUp);
        monthUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.MONTH, 1);
                updateAll();
            }
        });
        ImageButton monthDown = (ImageButton) findViewById(R.id.buttonMonthDown);
        monthDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.MONTH, -1);
                updateAll();
            }
        });
        ImageButton hourUp = (ImageButton) findViewById(R.id.buttonHourUp);
        hourUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.HOUR_OF_DAY, 1);
                updateAll();
            }
        });
        ImageButton hourDown = (ImageButton) findViewById(R.id.buttonHourDown);
        hourDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.HOUR_OF_DAY, -1);
                updateAll();
            }
        });
        ImageButton minUp = (ImageButton) findViewById(R.id.buttonMinUp);
        minUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = c.get(Calendar.MINUTE);
                //min.setText(Integer.toString(i<30?30:00));

                c.add(Calendar.MINUTE, 30-i);
                updateAll();
            }
        });
        ImageButton minDown = (ImageButton) findViewById(R.id.buttonMinDown);
        minDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = c.get(Calendar.MINUTE);
                c.add(Calendar.MINUTE, -30+i);
                updateAll();
            }
        });
        ImageButton tempUp = (ImageButton) findViewById(R.id.buttonTempUp);
        tempUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempValue += 0.5;
                updateAll();
            }
        });
        ImageButton tempDown = (ImageButton) findViewById(R.id.buttonTempDown);
        tempDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempValue -= 0.5;
                updateAll();
            }
        });

    }

    private void updateAll()
    {
        TextView day = (TextView) findViewById(R.id.textViewDay);
        //day.setText(Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + ".");
        String formatted = String.format("%02d.", c.get(Calendar.DAY_OF_MONTH));
        day.setText(formatted);

        TextView month = (TextView) findViewById(R.id.textViewMonth);
        //month.setText(Integer.toString(c.get(Calendar.MONTH)+1) + ".");
        formatted = String.format("%02d.", c.get(Calendar.MONTH)+1);
        month.setText(formatted);

        TextView hour = (TextView) findViewById(R.id.textViewHour);
        hour.setText(Integer.toString(c.get(Calendar.HOUR_OF_DAY)));

        TextView min = (TextView) findViewById(R.id.textViewMin);
        int i = c.get(Calendar.MINUTE);
        min.setText(Integer.toString(i<30?30:00));

        TextView temp = (TextView) findViewById(R.id.textViewTemp);
        temp.setText(String.format("%.1f", tempValue));

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_party_mode, menu);
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
    */
}
