package com.example.user.mathgiant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

class QuitDialogListener extends Activity implements DialogInterface.OnClickListener {

private Activity activity;


    public QuitDialogListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {//when the user click "ok" to leave.
            if((activity.getClass().getName()).equals(PlayActivity.class.getName())){//when the user want to leave the play screen.so he will go to the mainActivity.
                Intent intent = new Intent(activity,MainActivity.class);
                intent.putExtra("new_player",1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//to
                //clear the last activity and go to the play activity.
                ControllMusic.getInstance().stopPlaying();
                ControllMusic.getInstance().releasePlaying();

                activity.startActivity(intent);
            }
            else{//to exit the application.
                ControllMusic.getInstance().stopPlaying();
                ControllMusic.getInstance().releasePlaying();

                activity.finish();
               // super.onBackPressed();
            }
        }
        else if (which == DialogInterface.BUTTON_NEGATIVE) {//when the user click "no" to stay.
           // if(activity.getClass().getName().equals(MainActivity.class.getName()))
            Toast.makeText(activity, "Glad you decided to stay", Toast.LENGTH_SHORT).show();
        }
    }




}
