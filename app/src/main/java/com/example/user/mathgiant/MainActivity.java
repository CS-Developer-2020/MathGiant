package com.example.user.mathgiant;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.awt.Button;

public class MainActivity extends AppCompatActivity  {

    int numOfPlayer = 0;
    int keepPlaying = 0;
    Button startGameBtn ;
    Button quitBtn ;
    Button continueBtn;
    Button brainQuitBtn;
    Button brainContinueBtn;
    Button brainStartBtn;
    Button scoreBtn;
    ImageButton orangeBookBtn;
    boolean isClick = false,playing = false;
    private MediaPlayer play;
    private int newplayer = 0;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//clear the time and the battery
        setContentView(R.layout.activity_main);

        startGameBtn = findViewById(R.id.start_btn);
        continueBtn = findViewById(R.id.continue_btn);
        quitBtn = findViewById(R.id.quit_btn);
        brainContinueBtn = findViewById(R.id.brain_continue_btn);
        brainQuitBtn = findViewById(R.id.brain_quit_btn);
        brainStartBtn = findViewById(R.id.brain__start_btn);
        scoreBtn = findViewById(R.id.book_green_btn);
        orangeBookBtn = findViewById(R.id.orange_book_IB);

        Intent intent = getIntent();

        keepPlaying = intent.getIntExtra("keep_playing",0);
        newplayer = intent.getIntExtra("new_player",0);

        startAnimateJump(brainQuitBtn);
        startAnimateJump(brainStartBtn);
        startAnimateJump(brainContinueBtn);

        scoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playing = false;

                Intent intent = new Intent(MainActivity.this,ScoreActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//to
                //clear the last activity and go to the play activity.
                ControllMusic.getInstance().stopPlaying();
                ControllMusic.getInstance().releasePlaying();

                startActivity(intent);
            }
        });

        orangeBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ixl.com"));//connecting to this site to learn math
                ControllMusic.getInstance().stopPlaying();
                ControllMusic.getInstance().releasePlaying();
                startActivity(intent);
            }
        });


        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = true;
                playing = true;
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
             //   intent.putExtra("num_of_player", numOfPlayer);
                intent.putExtra("keep_playing",1);

                startActivity(intent);
            }
        });
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = true;
                if (newplayer == 0) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage("There is no player that registered yet\n please enter a new game");
                    builder1.setCancelable(true);
                    builder1.setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                } else {
                    Intent mapIntent = new Intent(MainActivity.this,MapActivity.class);
                    mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//to
                    //clear the last activity and go to the play activity.
                    ControllMusic.getInstance().stopPlaying();
                    startActivity(mapIntent);
                }
            }
        });
        quitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isClick = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirm shutdown").setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", new MyAlertDialogListener()).setNegativeButton("No", new MyAlertDialogListener())

                        .show();
            }


            class MyAlertDialogListener implements DialogInterface.OnClickListener {


                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if (i == DialogInterface.BUTTON_POSITIVE) {
                        ControllMusic.getInstance().stopPlaying();

                        finish();
                    } else if (i == DialogInterface.BUTTON_NEGATIVE) {
                        Toast.makeText(MainActivity.this, "Glad you decided to stay", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });

    }
    private  void startAnimateJump(View target) {
        int delay  = 100;
        if(target.getId() == R.id.brain_quit_btn){
            delay += 700;
        }
        else if(target.getId() == R.id.brain__start_btn){
            delay += 1400;
        }
        else{
            delay += 2100;
        }


        ObjectAnimator jumpUp = ObjectAnimator.ofFloat(target , "translationY",-200f,0f);
        jumpUp.setDuration(100);
        jumpUp.setStartDelay(delay);
        jumpUp.start();

        ObjectAnimator jumpDown = ObjectAnimator.ofFloat(target,"translationY",-200f,0f);
       jumpDown.setDuration(600);
        jumpDown.setStartDelay(delay);
        jumpDown.start();


        jumpUp.setRepeatCount(Animation.INFINITE);
        jumpDown.setRepeatCount(Animation.INFINITE);



    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Confirm shutdown").setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new QuitDialogListener(MainActivity.this)).setNegativeButton("No", new QuitDialogListener(MainActivity.this))

                .show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(keepPlaying!=1){
//            play = new MediaPlayer();
//            play = MediaPlayer.create(this,R.raw.happy_upbeat_first_screen);
//            play.setLooping(true);
//            play.start();
            ControllMusic.getInstance().initalizeMediaPlayer(this, R.raw.first_screen); // to initalize of media player

            ControllMusic.getInstance().startPlaying();// to start playing music

        }

    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(playing){
//            ControllMusic.getInstance().stopPlaying();
//        }
//    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return super.onKeyDown(keyCode, event);
//    }
}
