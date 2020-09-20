package com.example.user.mathgiant;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

public class HighScore extends AppCompatActivity {

    private static final String PREFS_GLOBAL = "prefs_global";//to identify the particular Preference.
    private static final String PREF_TOP_SCORE = "pref_top_score";
    private MediaPlayer play;

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(
                PREFS_GLOBAL, Context.MODE_PRIVATE);
    }

    //  Setters and getters for global preferences
    /* this method compare the new score with the top score and return boolean.*/
    public static boolean isTopScore(Context context, int newScore) {
        int topScore = getPreferences(context).getInt(PREF_TOP_SCORE, 0);
        return newScore > topScore;
    }
        /* return the top score*/
    public static int getTopScore(Context context) {
        return getPreferences(context).getInt(PREF_TOP_SCORE, 0);
    }
        /* set a new score to the top score*/             //TODO : LEARNING SHAREDPREFERENCE
    public static void setTopScore(Context context, int score) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putInt(PREF_TOP_SCORE, score);
        editor.apply();
    }

}