package com.example.user.mathgiant;

import android.content.Context;
import android.media.MediaPlayer;

public class ControllMusic {

    private static ControllMusic refrence = null;
    private MediaPlayer play;

    public static ControllMusic getInstance(){
        if(refrence == null){
            refrence = new ControllMusic();
        }
        return refrence;
    }

    public void initalizeMediaPlayer(Context context, int musicId){
        play = new MediaPlayer();
            play = MediaPlayer.create(context,musicId);
            play.setLooping(true);

// add initalization of media player in it and loop it
    }

    public void startPlaying(){
        play.start();
// add code to start playing music
    }

    public void stopPlaying(){
        play.stop();
// add code to stop playing music
    }

    public void pausePlaying(){
        play.pause();
// add code to pause playing music
    }
    public void releasePlaying(){
        play.release();
// add code to pause playing music
    }

}