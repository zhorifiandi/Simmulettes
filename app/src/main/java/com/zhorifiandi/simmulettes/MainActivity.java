package com.zhorifiandi.simmulettes;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.anastr.speedviewlib.SpeedView;
import com.zhorifiandi.simmulettes.Service.LocationService;
import com.zhorifiandi.simmulettes.Service.RotateService;
import com.zhorifiandi.simmulettes.Service.ShakeService;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity{

    SpeedView speedoMeter;
    Button button2;
    Button button3;

    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;

    //TextView textView;
    //TextView textView4;

    int gigi = 0;

    static float speed = 0;
    float MAX_SPEED = 200;
    private Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;
    Handler m_handler = new Handler();
    Handler m_handler2 = new Handler();

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //textView.setText(intent.getStringExtra("x") + "\n" + intent.getStringExtra("y"));
        }
    };
    private BroadcastReceiver messageReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finishAffinity();
        }
    };
    private BroadcastReceiver messageReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //textView4.setText(intent.getStringExtra("lat") + "\n" + intent.getStringExtra("long"));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignView();
        button2.setText("Accelerate");
        button3.setText("Brake");

        button4.setBackgroundColor(Color.GRAY);
        button5.setBackgroundColor(Color.GRAY);
        button6.setBackgroundColor(Color.GRAY);
        button7.setBackgroundColor(Color.GRAY);
        button8.setBackgroundColor(Color.GRAY);
        button9.setBackgroundColor(Color.GRAY);

        button4.setText("1");
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gigi = 1;
                button4.setBackgroundColor(Color.BLUE);
                button5.setBackgroundColor(Color.GRAY);
                button6.setBackgroundColor(Color.GRAY);
                button7.setBackgroundColor(Color.GRAY);
                button8.setBackgroundColor(Color.GRAY);
                button9.setBackgroundColor(Color.GRAY);
            }
        });
        button5.setText("2");
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gigi = 2;
                button4.setBackgroundColor(Color.GRAY);
                button5.setBackgroundColor(Color.BLUE);
                button6.setBackgroundColor(Color.GRAY);
                button7.setBackgroundColor(Color.GRAY);
                button8.setBackgroundColor(Color.GRAY);
                button9.setBackgroundColor(Color.GRAY);
            }
        });
        button6.setText("3");
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gigi = 3;
                button4.setBackgroundColor(Color.GRAY);
                button5.setBackgroundColor(Color.GRAY);
                button6.setBackgroundColor(Color.BLUE);
                button7.setBackgroundColor(Color.GRAY);
                button8.setBackgroundColor(Color.GRAY);
                button9.setBackgroundColor(Color.GRAY);
            }
        });
        button7.setText("4");
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gigi = 4;
                button4.setBackgroundColor(Color.GRAY);
                button5.setBackgroundColor(Color.GRAY);
                button6.setBackgroundColor(Color.GRAY);
                button7.setBackgroundColor(Color.BLUE);
                button8.setBackgroundColor(Color.GRAY);
                button9.setBackgroundColor(Color.GRAY);
            }
        });
        button8.setText("5");
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gigi = 5;
                button4.setBackgroundColor(Color.GRAY);
                button5.setBackgroundColor(Color.GRAY);
                button6.setBackgroundColor(Color.GRAY);
                button7.setBackgroundColor(Color.GRAY);
                button8.setBackgroundColor(Color.BLUE);
                button9.setBackgroundColor(Color.GRAY);
            }
        });
        button9.setText("R");
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gigi = 6;
                button4.setBackgroundColor(Color.GRAY);
                button5.setBackgroundColor(Color.GRAY);
                button6.setBackgroundColor(Color.GRAY);
                button7.setBackgroundColor(Color.GRAY);
                button8.setBackgroundColor(Color.GRAY);
                button9.setBackgroundColor(Color.BLUE);
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(
                messageReceiver, new IntentFilter("custom-event-name"));
        LocalBroadcastManager.getInstance(this).registerReceiver(
                messageReceiver2, new IntentFilter("custom-event-na"));
        LocalBroadcastManager.getInstance(this).registerReceiver(
                messageReceiver3, new IntentFilter("custom-event-nam"));
        startService(new Intent(getApplicationContext(),RotateService.class));
        startService(new Intent(getApplicationContext(),ShakeService.class));
        startService(new Intent(getApplicationContext(),LocationService.class));

        button2.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View arg0) {
                        mAutoIncrement = true;
                        repeatUpdateHandler.post( new RptUpdater() );
                        return false;
                    }
                }
        );

        button2.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if( (event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL)
                        && mAutoIncrement ){
                    mAutoIncrement = false;
                }
                return false;
            }
        });

        button3.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View arg0) {
                        mAutoDecrement = true;
                        m_handler2.post( new Brake() );
                        return false;
                    }
                }
        );

        button3.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if( (event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL)
                        && mAutoDecrement ){
                    mAutoDecrement = false;
                }
                return false;
            }
        });
        (new Decrement()).run();


    }


    protected void assignView() {
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);
        button7 = (Button)findViewById(R.id.button7);
        button8 = (Button)findViewById(R.id.button8);
        button9 = (Button)findViewById(R.id.button9);
        //textView = (TextView) findViewById(R.id.textView);
        //textView4 = (TextView) findViewById(R.id.textView4);
        speedoMeter = (SpeedView)findViewById(R.id.speedView);
        speedoMeter.setMaxSpeed((int)MAX_SPEED);
    }

    class RptUpdater implements Runnable {
        public void run() {
            if (gigi != 6) {
                if (speed < gigi * 40) {
                    if (mAutoIncrement) {
                        speed++;
                        speedoMeter.speedTo(speed);
                        System.out.println("INCRE");
                        repeatUpdateHandler.postDelayed(this, 25);
                    }
                }
            }
            else {
                if (speed < 40) {
                    if (mAutoIncrement) {
                        speed++;
                        speedoMeter.speedTo(speed);
                        System.out.println("INCRE");
                        repeatUpdateHandler.postDelayed(this, 25);
                    }
                }
            }

        }
    }

    class Brake implements Runnable {
        public void run() {
            if (speed > 0) {

                if( mAutoDecrement  ) {
                    speed--;
                    speedoMeter.speedTo(speed);
                    System.out.println("BRAKE");
                    repeatUpdateHandler.postDelayed(this, 25);
                }
            }

        }
    }

    class Decrement implements Runnable {
        public void run() {
            if (speed > 0) {

                if (!(mAutoIncrement || mAutoDecrement)) {
                    speed--;
                    speedoMeter.speedTo(speed);
                    System.out.println("DECRE");
                }
            }

            m_handler.postDelayed(this, 100 );
        }
    }


}
