package de.bitcoder.homectrl;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import de.fzi.fhemapi.model.devicetypes.Device;
import de.fzi.fhemapi.model.server.DeviceResponse;
import de.fzi.fhemapi.server.DeviceManager;
import de.fzi.fhemapi.server.FHEMServer;


public class MainActivity extends ActionBarActivity {

    public TextView txtView2;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        txtView2 = (TextView) findViewById(R.id.textView2);

        System.out.println("+++++++++++++++++");
        System.out.println("Starting");
        System.out.println("+++++++++++++++++");



    }



    public void onLCARSButtonClicked(View v) {
        Intent i = new Intent(this, LCARSMainActivity.class);
        startActivity(i);
    }
    public void onDevListButtonClicked(View v) {
        Intent i = new Intent(this, DeviceListActivity.class);
        startActivity(i);
    }
    public void onPartyButtonClicked(View v) {
        Intent i = new Intent(this, PartyModeActivity.class);
        startActivity(i);
    }

    public void onTestButtonClicked(View v) {

        final TextView txtView =  (TextView) findViewById(R.id.textView2);

        Thread thread = new Thread(new Runnable() {
            private String result;

            @Override
            public void run() {
                try {
                    //FHEMServer s = new FHEMServer("127.0.0.1", 7073);
                    //FHEMServer s = new FHEMServer("192.168.178.49", 7073);
                    FHEMServer s = new FHEMServer(LCARSConfig.serverIp, LCARSConfig.serverPort);

                    //List<DeviceResponse> devs = s.getAllDevices();
                    //for (DeviceResponse d : devs) {
                    //    System.out.println("name: " + d.name);
                    //}

                    DeviceManager dm = s.getDeviceManager();
                    List<Device> devices = dm.getDevices();

                    result = "";
                    for (Device d : devices )
                    {
                        String dname = d.getName();
                        System.out.println("name: " + dname + " type: " + d.getType());

                        if ( !dname.startsWith("SVG") )
                        {
                            DeviceResponse dr = s.getDevice(dname);
                            result += dname + "\n"; // + "  " + dr.eAttributes.toString();
                            result += "  " + dr.type + "\n";
                            result += "  " + dr.device +"\n";
                            //dr.eAttributes
                        }
                        else
                        {
                            result += dname + "\n";
                        }

                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //TextView txtView =  (TextView) findViewById(R.id.textView2);
                            //tvTime.setText(""+((System.currentTimeMillis()-startTime)/1000));
                            txtView.append( result );
                        }
                    });

                }
                catch (java.net.ConnectException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void ListButtonClicked(View v) {
        System.out.println("ListButtonClicked");

        final TextView txtView =  (TextView) findViewById(R.id.textView2);

        //txtView.setText("Start\n");
        // Better solution here: http://stackoverflow.com/questions/6343166/android-os-networkonmainthreadexception
        Thread thread = new Thread(new Runnable(){
            private String line;
            private String result;
            @Override
            public void run() {
                try {


                    InetAddress Address = null;
                    try {
                        //Address = InetAddress.getByName("pi");
                        Address = InetAddress.getByName(LCARSConfig.serverIp);

                        System.out.println(Address);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }

                    Socket s = new Socket(LCARSConfig.serverIp, LCARSConfig.serverPort);
                    if ( !s.isConnected() )
                    {
                        System.out.println("Is not connected");
                        return;
                    }
                    else
                    {
                        System.out.println("HIER1");
                        System.out.println("connected");
                    }
                    Thread.sleep(1000, 0);
                    //outgoing stream redirect to socket
                    System.out.println("HIER1");
                    OutputStream out = s.getOutputStream();
                    System.out.println("HIER2");
                    PrintWriter output = new PrintWriter(out);
                    System.out.println("HIER3");
                    output.println("\r\n\r\n\r\nlist\r\n");
                    output.flush();
                    System.out.println("HIER4c");
                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    System.out.println("HIER5");
                    Thread.sleep(1000, 0);

                    char c[] = { 0 };
                    result = "";
                    while (input.ready())
                    {
                        int read = input.read();
                        if ( read == -1 )
                            System.out.println("nothing");
                        else
                        {
                            c[0] = (char)read;
                            result += new String(c);
                        }
                    }
                    System.out.println("HIER6");
                    //Close connection
                    s.close();
                    System.out.println("result: " + result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //TextView txtView =  (TextView) findViewById(R.id.textView2);
                            //tvTime.setText(""+((System.currentTimeMillis()-startTime)/1000));
                            txtView.append( result );
                        }
                    });

                } catch (Exception e) {
                    System.out.println("HIEREX");
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
