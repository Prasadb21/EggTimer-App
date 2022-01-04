package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar seekBar;
    Boolean counter = false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void resetTimer()
    {
        timerTextView.setText("00:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        counter = false;
    }

    public void buttonClicked(View view)
    {
        if(counter)
        {
            resetTimer();
        }
        else {
            counter = true;

            seekBar.setEnabled(false);
            goButton.setText("STOP!");
            countDownTimer = new CountDownTimer( seekBar.getProgress()*1000+100, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l/1000);

                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext() , R.raw.m);
                    mediaPlayer.start();
                    resetTimer();

                }
            }.start();
        }
    }


    public void updateTimer(int secLeft)
    {
        int minutes = secLeft/60;
        int sec = secLeft - (minutes*60);
        String secString = Integer.toString(sec);
        if(sec<=9)
        {
            secString = "0"+secString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secString);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        Button button = (Button) findViewById(R.id.button);
        goButton = (Button) findViewById(R.id.button);
        timerTextView= (TextView) findViewById(R.id.textView);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}