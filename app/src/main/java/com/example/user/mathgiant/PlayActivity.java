package com.example.user.mathgiant;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener , Balloon.BalloonListener {
    public static int countMenu = 0;
    public static int countVib = 0;
    public static int numOfQuestion = 1;
    public static boolean ready = false;
    private static final String WRONG_ANSWER = "wrongAnswer";
    private static final String MISSED_ANSWER = "missedAnswer";
    private static final String CORRECT_ANSWER = "correctAnswer";
    private TextView answer1,answer2,answer3,answer4;
    private String[] answersToShow;
    private ViewGroup contentView;//TODO
    public static final int MIN_ANIMATION_DELAY = 500;
    public static final int MAX_ANIMATION_DELAY = 1500;
    private static final int MIN_ANIMATION_DURATION = 1000;
    private static final int MAX_ANIMATION_DURATION = 8000;
    private static final int NUMBER_OF_HEARTH = 3;
    private ImageButton halfIB, vibMenuBtn, questionMenuBtn, exitMenuBtn, musicMenuBtn, hourGlassIB;
    private Button settingsBtn;
    private ObjectAnimator animator, animator1;
    private Vibrator vibe;
    private Boolean isOff = false, quiet = false;
    private List<Balloon> balloonArrayList = new ArrayList<>();///TODO
    private int mNextColor, mBalloonsPopped;//TODO
    //  ViewTreeObserver viewTreeObserver;
    private Animation myAnim;
    private MyBounceInterpolator interpolator;
    private CircleAngleAnimation circleAngleAnimation;
    private Circle circle;
    private MediaPlayer play;
    private int[] balloonColorsAray = {R.mipmap.yellow_balloon, R.mipmap.green_balloon_1, R.mipmap.red_balloon
            , R.mipmap.blue_balloon_1, R.mipmap.purple_balloon, R.mipmap.pink_balloon};
    private int screenHight, screenWidth, numOfSuccssesQuestion = 0;//TODO
    private int xPosition, score, level, heartUsed = 0, number_question = 0;
    private boolean gameStopped, correctAnswer, playing;
    private TextView scoreDisplay,questionsToShow;
    private List<ImageView> heartsArrayList = new ArrayList<>();
    private static boolean isready = true;
    private ArrayList<String> questionsList = new ArrayList<>();
    private ArrayList<String []> answersList = new ArrayList<>();

    private SharedPreferences sp;
    private StringBuilder sb;
    private String[] answer ;
    private Map<String, String[]> questionMap ;

    private String result1,result2,result3,result4,resultCorrect;


    // private BalloonLauncher launcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//clear the time and the battery
        setContentView(R.layout.activity_play);

        setDisplaySize();

        contentView = (ViewGroup) findViewById(R.id.activity_play_layou);

        halfIB = findViewById(R.id.half_balloon_image_btn);
        settingsBtn = findViewById(R.id.Settings_btn);
        hourGlassIB = findViewById(R.id.hour_glass_image_btn);
        questionMenuBtn = findViewById(R.id.menu_question);
        exitMenuBtn = findViewById(R.id.menu_exit);
        vibMenuBtn = findViewById(R.id.menu_vib);
        musicMenuBtn = findViewById(R.id.menu_music);
        circle = findViewById(R.id.circle);
        scoreDisplay = findViewById(R.id.score_TV);
        questionsToShow = findViewById(R.id.question_TV);
        answer1 = findViewById(R.id.answer1_TV);
        answer2 = findViewById(R.id.answer2_TV);
        answer3 = findViewById(R.id.answer3_TV);
        answer4 = findViewById(R.id.answer4_TV);

        heartsArrayList.add((ImageView) findViewById(R.id.heart_1));
        heartsArrayList.add((ImageView) findViewById(R.id.heart_2));
        heartsArrayList.add((ImageView) findViewById(R.id.heart_3));
        sp = getSharedPreferences("class",MODE_PRIVATE);
        sb = new StringBuilder();

        sb.append(sp.getString("a-b",null));
        questionMap = new HashMap<String, String[]>();
        questionMap = readFromFile(sb.toString());

        suffelMap(questionMap);
        for(Map.Entry entry:questionMap.entrySet()){
            questionsList.add((String) entry.getKey());
            answersList.add((String[]) entry.getValue());

        }


       questionsToShow.setText(questionsList.get(numOfQuestion-1));
        answersToShow= answersList.get(numOfQuestion-1);
        answer1.setText(answersToShow[0]);
        answer2.setText(answersToShow[1]);
        answer3.setText(answersToShow[2]);
        answer4.setText(answersToShow[3]);
        resultCorrect = answersToShow[4];
    }

    private void suffelMap(Map<String, String[]> questionMap) {

        //suffel the map
        List keys = new ArrayList(questionMap.keySet());
        Collections.shuffle(keys);
        for (Object o : keys)
            // Access keys/values in a random order
            questionMap.get(o);

    }

    public Map<String, String[]> readFromFile(String file){
        // List<String>list = new ArrayList<String>();
        String q ;
        Map<String, String[]> map = new HashMap<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(file)));

            // do reading, usually loop until end of file reading
            String mLine;
            int size = Integer.parseInt(reader.readLine());
            // list.add(reader.readLine());
            while ((mLine = reader.readLine()) != null && !mLine.equals("#")) {
                q = mLine;
                answer = reader.readLine().trim().split(",");



                map.put(q,answer);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        return map;
    }

    public void setDisplaySize() {
        /* adapt the image to the size of the display */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.mipmap.play_screen), size.x, size.y, true);
    }


    public void didTapButton(View view) {
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        switch (view.getId()) {

            case R.id.half_balloon_image_btn:

                createAnimation(halfIB);
                setVibrate(halfIB, quiet);
                break;

            case R.id.hour_glass_image_btn:

                createAnimation(hourGlassIB);
                setVibrate(hourGlassIB, quiet);
                break;

            case R.id.Settings_btn:

                createAnimation(settingsBtn);//callin to the method that creating the bounce circleAngleAnimation.
                callAnimator(settingsBtn, circle);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                break;
        }
        /* the menu's buttons*/
        vibMenuBtn.setOnClickListener(this);
        musicMenuBtn.setOnClickListener(this);
        exitMenuBtn.setOnClickListener(this);
        questionMenuBtn.setOnClickListener(this);

    }

    /* moving the settings to the circle menu*/
    private void callAnimator(Button settingsBtn, Circle circle) {
        if (countMenu == 0) {//when the user open the menu.
            animator = ObjectAnimator.ofFloat(settingsBtn, "translationX", -130).setDuration(500);
            animator1 = ObjectAnimator.ofFloat(settingsBtn, "translationY", -105).setDuration(500);
            AnimatorSet set = new AnimatorSet();//to play together on X and Y.
            set.playTogether(animator, animator1);
            set.start();
            circleAngleAnimation = new CircleAngleAnimation(circle, 360, countMenu);
            circleAngleAnimation.setDuration(250);//time for angle.
            circle.startAnimation(circleAngleAnimation);
            musicMenuBtn.setVisibility(View.VISIBLE);//when the user is opening the menu.
            questionMenuBtn.setVisibility(View.VISIBLE);//""
            exitMenuBtn.setVisibility(View.VISIBLE);//""
            vibMenuBtn.setVisibility(View.VISIBLE);//""
            countMenu = 1;
        } else if (countMenu == 1) {//when the user closing the menu.
            animator = ObjectAnimator.ofFloat(settingsBtn, "translationX", 10).setDuration(2000);
            animator1 = ObjectAnimator.ofFloat(settingsBtn, "translationY", 10).setDuration(2000);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(animator, animator1);
            set.start();
            circleAngleAnimation = new CircleAngleAnimation(circle, 0, countMenu);
            circleAngleAnimation.setDuration(5000);
            circle.startAnimation(circleAngleAnimation);
            musicMenuBtn.setVisibility(View.INVISIBLE);
            questionMenuBtn.setVisibility(View.INVISIBLE);
            exitMenuBtn.setVisibility(View.INVISIBLE);
            vibMenuBtn.setVisibility(View.INVISIBLE);
            countMenu = 0;
        }
    }

    private void setVibrate(View v, boolean quiet) {
        if (quiet == true)
            vibe.cancel();
        else
            vibe.vibrate(100);

    }

    private void createAnimation(View v) {
        if (v.getId() == R.id.half_balloon_image_btn || v.getId() == R.id.hour_glass_image_btn)
            myAnim = AnimationUtils.loadAnimation(this, R.anim.present_btn);
        else
            myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        v.startAnimation(myAnim);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setTitle("Confirm shutdown").setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new QuitDialogListener(PlayActivity.this)).setNegativeButton("No", new QuitDialogListener(PlayActivity.this))
                    .show();

        }
    }

    @Override
    public void onClick(View v) {
        int option = v.getId();
        switch (option) {

            case R.id.menu_vib:
                if (countVib == 0) {
                    quiet = true;
                    countVib = 1;
                    ((ImageButton) v).setImageResource(R.mipmap.menu_btn_vib_off);

                    setVibrate(v, quiet);
                } else if (countVib == 1) {
                    countVib = 0;
                    quiet = false;
                    ((ImageButton) v).setImageResource(R.mipmap.menu_btn_vib);
                    setVibrate(v, quiet);

                }

                break;

            case R.id.menu_exit:

                onBackPressed();
                break;

            case R.id.menu_music:

                if (!isOff) {
                    ((ImageButton) v).setImageResource(R.mipmap.menu_btn_music_off);
                    // play.pause();
                    ControllMusic.getInstance().pausePlaying();
                    isOff = true;
                } else {
                    ((ImageButton) v).setImageResource(R.mipmap.menu_btn_music_on);
                    // play.start();
                    ControllMusic.getInstance().startPlaying();
                    isOff = false;
                }

                break;

            case R.id.menu_question:
                break;
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        ControllMusic.getInstance().initalizeMediaPlayer(PlayActivity.this, R.raw.play_screen_1);
        ControllMusic.getInstance().startPlaying();
        // readyToStart();
        startLevel();
        //   launchBalloon(screenWidth+300);

    }


    public void startLevel() {
        isready = true;
        //level++;
        //updateDisplay();
        //  numOfSuccssesQuestion++;//the default numOfSuccssesQuestion start is 0.

        createBallon(0);
        //  playing = true;

    }

    private void createBallon(int xPosition) {
        int balloonsLaunched = 0;
        while (balloonsLaunched < 4 && isready == true) {
            xPosition += screenWidth + 300;
            launchBalloon(xPosition);
            // xPosition += screenWidth+300;
            // is on the screen
            balloonsLaunched++;//number of balloonArrayList that launched.

        }
        isready = false;
    }


    private void finishLevel() {
        // TODO: 17/02/2019 dialog and moving to the map
        Toast.makeText(this, "YOU FINISH THE LEVEL", Toast.LENGTH_SHORT).show();
        playing = false;
    }


    public  void popBalloon(Balloon balloon, boolean touched, ValueAnimator animator) {


        // balloonArrayList.remove(balloon);
        // contentView.removeView(balloon);
        if (touched ) {//if the user touched the balloon and its the correct answer
            //increment the score

            contentView.removeView(balloon);
            int b1 = balloonArrayList.indexOf(balloon);
            int trueAnswer = -1;
            for (int i =0; i < answersToShow.length-1 ; i++) {
                if (resultCorrect.equals(answersToShow[i])) {
                    trueAnswer = i;

                    break;

                }
            }
            if (b1 == trueAnswer) {
                correctAnswer = true;
            } else
                correctAnswer = false;


            if (correctAnswer) {
                score += 4;
                numOfSuccssesQuestion++;
                updateDisplay();
                if(numOfSuccssesQuestion == 8){
                    level++;
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder.setTitle("CONGRATS!!").setMessage("You finish level  "+level)
                                .setPositiveButton("Next level", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(PlayActivity.this, MapActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//to
                                        //clear the last activity and go to the play activity.
                                        startActivity(intent);
                                        ControllMusic.getInstance().stopPlaying();
                                        ControllMusic.getInstance().releasePlaying();
                                    }
                                })
                                .show();

                    }
                }
                if(numOfSuccssesQuestion < 8)
                showStatusDialog(CORRECT_ANSWER);//keep going till 8 succsses
                for (Balloon b : balloonArrayList) {
                    // contentView.removeView(b);
                    b.pause();

                }
            }
//if the user choose the wrong answer.
            if(!correctAnswer) {


                for (Balloon b : balloonArrayList) {

                    b.pause();

                }
                for (Balloon b : balloonArrayList) {

                    contentView.removeView(b);

                }
                if (balloonArrayList.size() == 4) {

                    heartUsed++;
                    if (heartUsed <= NUMBER_OF_HEARTH) {//if i can remove life from the game.

                        heartsArrayList.get(heartsArrayList.size() - heartUsed).setVisibility(View.INVISIBLE);
                        if (score >= 4)
                            score -= 4;
                        updateDisplay();
                    }
                    balloonArrayList.clear();
                    if (heartUsed == NUMBER_OF_HEARTH) {
                        gameOver(true);

                    }
                    if(heartUsed < NUMBER_OF_HEARTH)
                        showStatusDialog(WRONG_ANSWER);
                }

            }



        }




        else {
            //the balloonArrayList toched the spikes.


            contentView.removeView(balloon);
            for (Balloon b : balloonArrayList) {
                b.pause();
            }

            if (balloonArrayList.size() == 4) {

                heartUsed++;
                if (heartUsed <= NUMBER_OF_HEARTH) {//if i can remove life from the game.

                    heartsArrayList.get(heartsArrayList.size() - heartUsed).setVisibility(View.INVISIBLE);
                    if (score >= 4)
                        score -= 4;
                    updateDisplay();
                }
                balloonArrayList.clear();
                if (heartUsed == NUMBER_OF_HEARTH) {
                    gameOver(true);

                }
                if(heartUsed < NUMBER_OF_HEARTH)
                    showStatusDialog(MISSED_ANSWER);
            }
        }


    }


    private void showStatusDialog(String status) {

        String title;
        String message;
        switch (status) {
            case MISSED_ANSWER:


                title = "Missed Question";
                message = "You lost your chance\nPress next for a new Question";
                break;
            case WRONG_ANSWER:

                title = "Wrong Answer";
                message = "Press next for a new Question";
                break;
            default:
                title = "Right Answer!!!";
                message = "Press next for a new Question";
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Next", new dialogStatus(status)).show();


    }

    private void gameOver(boolean b) {
        // TODO: 17/02/2019 game over dialog
        for (Balloon balloon : balloonArrayList) {
            contentView.removeView(balloon);
            balloon.setPopped(true);
        }
        balloonArrayList.clear();
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over !!!");
        builder.setMessage("Press OK to start a new game , or Cancle to go to menu");

        // add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PlayActivity.this, MapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//to
                //clear the last activity and go to the play activity.
                startActivity(intent);
                ControllMusic.getInstance().stopPlaying();
                ControllMusic.getInstance().releasePlaying();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//to
                //clear the last activity and go to the play activity.
                startActivity(intent);
                ControllMusic.getInstance().stopPlaying();
                ControllMusic.getInstance().releasePlaying();
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        playing = false;
    }







    private void updateDisplay() {
        scoreDisplay.setText(String.valueOf(score));

    }



    private  void launchBalloon(int x) {
//create a balloon with different color.

        final Balloon balloon = new Balloon(this, balloonColorsAray[mNextColor], 150);
        balloonArrayList.add(balloon);

//      Set balloon vertical position and dimensions, add to container
        balloon.setX(x);
        balloon.setY(screenHight + balloon.getHeight());
        // balloon.setY(screenHight );


        contentView.addView(balloon);


//      Let 'er fly
        // int duration = Math.max(MIN_ANIMATION_DURATION, MAX_ANIMATION_DURATION - (numOfSuccssesQuestion * 1000));
        int duration = 3000;
        balloon.releaseBalloon(screenHight, duration);

        //    }

        if (mNextColor +1  == balloonColorsAray.length) {
            mNextColor = 0;
        } else {
            mNextColor++;
        }


    }
    public void readyToStart() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
        builder.setCancelable(false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setTitle("Are you Ready?").setMessage("Are you redy to start the game?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //when the user click "ok" .
                            if(which == DialogInterface.BUTTON_POSITIVE )
                            {
                                Toast.makeText(PlayActivity.this, "Good Luck!!!", Toast.LENGTH_SHORT).show();

                            }



                        }
                    });


            builder.create().show();
            // startLevel();

        }

    }


    private class dialogStatus implements DialogInterface.OnClickListener {
        private String status;
        public dialogStatus(String status) {
            this.status = status;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which ==DialogInterface.BUTTON_POSITIVE ) {
                switch (status) {
                    case MISSED_ANSWER:
                        for (Balloon b : balloonArrayList) {
                            contentView.removeView(b);


                        }
                        break;
                    case WRONG_ANSWER:
//                        for (Balloon b : balloonArrayList) {
//                            contentView.removeView(b);
//
//
//                        }
                        balloonArrayList.clear();

                        break;
                    case CORRECT_ANSWER:

                        for (Balloon b : balloonArrayList) {
                            contentView.removeView(b);


                        }
                        balloonArrayList.clear();
                        break;

                }
                numOfQuestion++;
                if(numOfQuestion==20){
                    numOfQuestion = 1;

                }

                questionsToShow.setText(questionsList.get(numOfQuestion-1));
                answersToShow= answersList.get(numOfQuestion-1);
                answer1.setText(answersToShow[0]);
                answer2.setText(answersToShow[1]);
                answer3.setText(answersToShow[2]);
                answer4.setText(answersToShow[3]);
                resultCorrect = answersToShow[4];
                startLevel();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sp.edit();
    }
}
