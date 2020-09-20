package com.example.user.mathgiant;

import android.app.Activity;
import android.content.DialogInterface;

class dialogFalseListener extends PlayActivity implements DialogInterface.OnClickListener{

private Activity activity;
    public dialogFalseListener(PlayActivity playActivity) {
        this.activity = playActivity;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
       // if(which == DialogInterface.BUTTON_POSITIVE)
     //startLevel();
    }
}
