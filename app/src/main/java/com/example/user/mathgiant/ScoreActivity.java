package com.example.user.mathgiant;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ScoreActivity extends AppCompatActivity {

    Button backScoreBtn;
Button settingsBtn;
    private MediaPlayer play;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        backScoreBtn = findViewById(R.id.back_score_btn);
      //  settingsBtn = findViewById(super.settingsBtn.getId());

//didTapButton(settingsBtn);

        backScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//to
                //clear the last activity and go to the play activity.
                ControllMusic.getInstance().stopPlaying();
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {//when the user press back button.moving to the main activity.
        super.onBackPressed();
        Intent intent = new Intent(ScoreActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//to
        //clear the last activity and go to the play activity.
        ControllMusic.getInstance().stopPlaying();
        startActivity(intent);
    }


//    @Override
//    public void didTapButton(View view) {
//        super.didTapButton(view);
//
//    }

//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//    }

    @Override
    protected void onStart() {
        //TODO  - CHECKIN IF I CAN CFREATE CLASS
        super.onStart();
//        play = new MediaPlayer();
////        play = MediaPlayer.create(this,R.raw.energetic_rock_high_scores);
////        play.setLooping(true);
////        play.start();
        ControllMusic.getInstance().initalizeMediaPlayer(this,R.raw.high_scores);
        ControllMusic.getInstance().startPlaying();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//       // play.stop();
//        ControllMusic.getInstance().stopPlaying();
//    }
}
