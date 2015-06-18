package de.bitcoder.homectrl;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by thomas on 12.06.15.
 */
public class LCARSButton extends Button {

    private Timer myTimer;

    public LCARSButton(Context context) {
        super(context);
    }

    public LCARSButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LCARSButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showResult(boolean successful)
    {
        System.out.println("LCARSButton.showResult(" + successful + ")");
        setButtonState(successful ? 1 : 2);

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }
        }, 3000);
    }

    private void TimerMethod()
    {
        System.out.println("TimerMethod()");
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        //getParent().runOnUiThread(Timer_Tick);

        // TODO this must be called in UIThread
        //setButtonState(0);

    }

    public void setButtonState(int state)
    {
        System.out.println("LCARSButton.setButtonState(" + state + ")");
        if ( state == 0 ) // normal state
        {
            myTimer.cancel();
            //this.setTextColor(0x000000);
        }
        else if ( state == 1 ) // successful-highlighted
        {
            //this.setBackgroundColor(getResources().getColor(R.color.lcars_orange));
            //this.setTextColor(0x00ff00);
        }
        else if ( state == 2 ) // failed-highlighted
        {
            //this.setTextColor(0xff0000);
        }

    }
}
