package de.bitcoder.homectrl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by thomas on 24.01.15.
 */
public class FhemConnector {

    private Thread telnetThread = null;
    private Socket socket = null;

    private OutputStream outStream = null;
    private PrintWriter outWriter = null;

    private InputStream inStream = null;
    private BufferedReader inReader = null;

    public void FhemConnector()
    {
        telnetThread = new Thread(new Runnable(){
            private String line;
            private String result;
            @Override
            public void run() {
                try {

                    socket = new Socket(LCARSConfig.serverIp, LCARSConfig.serverPort);
                    if ( !socket.isConnected() )
                    {
                        System.out.println("Is not connected");
                    }
                    else
                    {
                        System.out.println("connected");
                    }
                    Thread.sleep(1000, 0);
                    //outgoing stream redirect to socket

                    outStream = socket.getOutputStream();

                    outWriter = new PrintWriter(outStream);

                    outWriter.println("\r\n\r\n\r\nlist\r\n");
                    outWriter.flush();

                    inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    Thread.sleep(1000, 0);

                    char c[] = { 0 };
                    result = "";
                    while (inReader.ready())
                    {
                        int read = inReader.read();
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
                    socket.close();
                    System.out.println("result: " + result);

                } catch (Exception e) {
                    System.out.println("HIEREX");
                    e.printStackTrace();
                }
            }
        });

        telnetThread.start();

    }
}
