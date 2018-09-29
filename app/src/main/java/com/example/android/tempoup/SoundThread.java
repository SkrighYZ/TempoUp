package com.example.android.tempoup;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class SoundThread extends Thread {

    private Context context;
    private MediaPlayer mp;
    private boolean canPlay;
    private int timeInterval; // in milliseconds

    public SoundThread(Context context, int bpm) {
        this.context = context;
        mp = MediaPlayer.create(context, R.raw.tick);
        mp.setLooping(false);
        timeInterval = Utils.bpmToMilli(bpm);
        Log.d("SoundThread", "Time interval = " + Integer.toString(timeInterval));
    }

    @Override
    public void run(){
        while(canPlay){
            long startTime = System.currentTimeMillis();
            mp.seekTo(0);
            mp.start();
            long endTime = System.currentTimeMillis();
            long deltaTime = endTime - startTime;
            Log.d("SoundThread", "Trying to sleep." + deltaTime);
            if (deltaTime < timeInterval){
                Log.d("SoundThread", "Trying to sleep." + deltaTime);
                try{
                    Log.d("SoundThread", "Trying to sleep." + deltaTime);
                    Thread.sleep(timeInterval - deltaTime);
                } catch (InterruptedException e){
                    Log.d("SoundThread", "Thread Cannot Sleep.");
                    e.printStackTrace();
                }
            }
        }
    }

    public void play(){
        canPlay = true;
        this.start();
    }

    public void pause(){
        canPlay = false;
        while(true){
            try {
                this.join();
                break;
            } catch (InterruptedException e) {
                Log.d("Thread", "Thread joining failed.");
                e.printStackTrace();
            }
        }
        mp.stop();
        mp.reset();
        mp.release();
        mp = null;
    }
}
