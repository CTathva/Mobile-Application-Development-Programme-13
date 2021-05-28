package com.akash.cp.vtu.vtupartb_5;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements Base{
    private Button mButtonForward,mButtonPause, mButtonPlay,mButtonRewind;

    private MediaPlayer mediaPlayer;

    private double startTime = 0;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private static int delay=0;
    private static int period=1000;
    private SeekBar seekbar;
    public static final String TAG ="MainActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listener();
    }

    @Override
    public void init() {
        mButtonForward = (Button) findViewById(R.id.forward);
        mButtonPause = (Button) findViewById(R.id.pause);
        mButtonPlay = (Button)findViewById(R.id.play);
        mButtonRewind = (Button)findViewById(R.id.rewind);
        mediaPlayer = MediaPlayer.create(this, R.raw.zayn);
        seekbar = (SeekBar)findViewById(R.id.seekBar);
        seekbar.setMax(mediaPlayer.getDuration());
    }

    @Override
    public void listener() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekbar.setProgress(mediaPlayer.getCurrentPosition());


                ;
            }
        }, delay, period);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if(mediaPlayer!=null && b)
                {
                    mediaPlayer.seekTo(progress);

                }
                final long mMinutes=(progress/1000)/60;
                final int mSeconds=((progress/1000)%60);
                Log.d(TAG,"progress "+mMinutes+" min "+ mSeconds+" sec");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        mButtonPlay.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Play",Toast.LENGTH_SHORT).show();
                Log.d(TAG,"play" );
                mediaPlayer.start();

            }
        });

        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pause",Toast.LENGTH_SHORT).show();
                Log.d(TAG,"pause" );
                mediaPlayer.pause();

            }
        });

        mButtonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    if (currentPosition + forwardTime <= mediaPlayer.getDuration()) {
                        mediaPlayer.seekTo(currentPosition + forwardTime);
                    } else {
                        mediaPlayer.seekTo(mediaPlayer.getDuration());
                    }
                }

            }
        });

        mButtonRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    if (currentPosition - backwardTime >= 0) {
                        mediaPlayer.seekTo(currentPosition - backwardTime);
                    } else {
                        mediaPlayer.seekTo(0);
                    }
                }
            }
        });
    }
    String secToTime(int sec) {
        int second = sec % 60;
        int minute = sec / 60;
        if (minute >= 60) {
            int hour = minute / 60;
            minute %= 60;
            return hour + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
        }
        return minute + ":" + (second < 10 ? "0" + second : second);
    }
    }