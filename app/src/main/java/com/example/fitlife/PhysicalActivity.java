package com.example.fitlife;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//A timer for the physical activity. A basic stopwatch timing the user on how long they completed their activity
public class PhysicalActivity extends AppCompatActivity {

    private int id;
    private String ex_name;
    private String ex_details, type;

    private int seconds = 0;

    // Is the stopwatch running?
    private boolean running;

    TextView exDetails, exName, date, timer;
    Button cancelBtn, startStopBtn;
    private boolean wasRunning;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    TextView strictpress,Squat,biceps,deadlift,benchpress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical);

        exDetails = findViewById(R.id.ex_details);
        exName = findViewById(R.id.ex_name);
        date = findViewById(R.id.date);

        cancelBtn = findViewById(R.id.cancel_btn);
        startStopBtn = findViewById(R.id.start_stop_btn);
        timer = findViewById(R.id.timer);



//Create id resources for different exercises

        strictpress = findViewById(R.id.strictpress);
        Squat = findViewById(R.id.Squat);
        biceps = findViewById(R.id.biceps);
        deadlift = findViewById(R.id.deadlift);
        benchpress = findViewById(R.id.benchpress);


//        int userid = new SessionManager(CardioTrainDetailsActivity.this).getUserId();
//
//        Toast.makeText(this, new Repo(this).getAllCardioModel(userid).size() + "", Toast.LENGTH_SHORT)
//             .show();

        date.setText(simpleDateFormat.format(Calendar.getInstance(Locale.ENGLISH)
                .getTime()));
        runTimer();

        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            }
        });
        // Button for stopwatch stop and start running
        startStopBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                running = !running;

                if (running)
                {
                    startStopBtn.setText("Stop");
                    cancelBtn.setVisibility(View.VISIBLE);
                }
                else
                {
                    startStopBtn.setText("Start");
                }

            }
        });



        strictpress.setOnClickListener(ov);
        Squat.setOnClickListener(ov);
        biceps.setOnClickListener(ov);
        deadlift.setOnClickListener(ov);

        benchpress.setOnClickListener(ov);



    }

    View.OnClickListener ov = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            TextView b = (TextView)view;
            String buttonText = b.getText().toString();
            Intent intent = new Intent(getApplicationContext(),TimerActivity.class);
            intent.putExtra("name",buttonText);
            startActivity(intent);

        }
    };
//Create a timer to record workout
    private void runTimer()
    {


        // Creates a new Handler
        final Handler handler = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable()
        {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                // Set the text view text.
                timer.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (running)
                {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }
}