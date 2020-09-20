package com.example.user.mathgiant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MapActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private StringBuilder sb;
    private int level;
    private Animation myAnim;
    private MyBounceInterpolator interpolator;
    private ImageView medal1,medal2,medal3,medal4,medal5;
    private ImageView lock2,lock3,lock4,lock5;
    private ImageButton l1,l2,l3,l4,l5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//clear the time and the battery

        setContentView(R.layout.activity_map);

        sp = getSharedPreferences("class",MODE_PRIVATE);
        editor = sp.edit();
        level = sp.getInt("level",1);
        medal1 = findViewById(R.id.medal_1_IV);
        medal2 = findViewById(R.id.medal_2_IV);
        medal3 = findViewById(R.id.medal_3_IV);
        medal4 = findViewById(R.id.medal_4_IV);
        medal5 = findViewById(R.id.medal_5_IV);
        lock2 = findViewById(R.id.lock_2_IV);
        lock3 = findViewById(R.id.lock_3_IV);
        lock4 = findViewById(R.id.lock_4_IV);
        lock5 = findViewById(R.id.lock_5_IV);
        l1 = findViewById(R.id.level_1_IB);
        l2 = findViewById(R.id.level_2_IB);
        l3 = findViewById(R.id.level_3_IB);
        l4 = findViewById(R.id.level_4_IB);
        l5 = findViewById(R.id.level_5_IB);

        checkLevel(level);







    }

    private void checkLevel(int level) {
        switch (level){
            case 1:
                break;
            case 2:
                medal1.setVisibility(View.VISIBLE);
                lock2.setVisibility(View.INVISIBLE);
                l2.setAlpha((float) 0.9);
                break;

            case 3:
                medal2.setVisibility(View.VISIBLE);
                lock3.setVisibility(View.INVISIBLE);
                l3.setAlpha((float) 0.9);
                break;

            case 4:
                medal3.setVisibility(View.VISIBLE);
                lock4.setVisibility(View.INVISIBLE);
                l4.setAlpha((float) 0.9);
                break;

            case 5:
                medal4.setVisibility(View.VISIBLE);
                lock5.setVisibility(View.INVISIBLE);
                l5.setAlpha((float) 0.9);
                break;
        }
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(MapActivity.this,MainActivity.class);
        intent.putExtra("new_player",1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//to
        //clear the last activity and go to the play activity.
        startActivity(intent);
        ControllMusic.getInstance().stopPlaying();
    }

    @Override
    protected void onStart() {
        super.onStart();

            ControllMusic.getInstance().initalizeMediaPlayer(this,R.raw.main_map_1);
            ControllMusic.getInstance().startPlaying();


    }

    @Override
    public void onClick(View v) {
        int option = v.getId();

        switch (option){

            case R.id.level_1_IB:
                createAnimation(v);
                moveActivity();

                break;

            case R.id.level_2_IB:
                createAnimation(v);
                if( level > 1 ){
                v.setAlpha(1);
                    moveActivity();

                }

                break;


            case R.id.level_3_IB:
                createAnimation(v);

                if(level > 2 ){


                    moveActivity();

                }

                break;


            case R.id.level_4_IB:
                createAnimation(v);
                if( level > 3  ){
                    moveActivity();

                }

                break;


            case R.id.level_5_IB:
                createAnimation(v);

                if( level > 4 ){
                    moveActivity();

                }

                break;

            case R.id.lock_2_IV:
                createAnimation(v);

                break;

            case R.id.lock_3_IV:
                createAnimation(v);
                break;

            case R.id.lock_4_IV:
                createAnimation(v);
                break;

            case R.id.lock_5_IV:
                createAnimation(v);
                break;




        }

    }

    private void moveActivity() {
        Intent intent = new Intent(MapActivity.this, PlayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        ControllMusic.getInstance().stopPlaying();
        ControllMusic.getInstance().releasePlaying();


    }


    private void createAnimation(View v) {
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        v.startAnimation(myAnim);
    }

    @Override
    protected void onPause() {
        super.onPause();


    }
}
