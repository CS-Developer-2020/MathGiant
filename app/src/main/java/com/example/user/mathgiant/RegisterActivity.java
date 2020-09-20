package com.example.user.mathgiant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

    Button startBtn;
    Button backBtn;
    EditText playerET;
    Spinner classesSp;
    RadioGroup radioDifficult;
    String player;
    String classGrade;
    long points;
    private MediaPlayer play;
    Boolean playing = false;
    int keepPlaying = 1;
    private boolean newPlayer = false;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    StringBuilder sb;
    private static String fileName2 ="normal_1.txt";
    private static String fileName1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//clear the time and the battery


        setContentView(R.layout.activity_register);

        startBtn = findViewById(R.id.start_register_btn);
        backBtn = findViewById(R.id.back_btn);
        playerET = findViewById(R.id.player_ET);
        classesSp = findViewById(R.id.class_spinner);
        radioDifficult = findViewById(R.id.radio_group_difficult);

        sp = getSharedPreferences("class", MODE_PRIVATE);
         editor = sp.edit();
        player = playerET.getText().toString();
        sb = new StringBuilder();
        Intent intent = getIntent();



        keepPlaying = intent.getIntExtra("keep_playing", 0);
        newPlayer = intent.getBooleanExtra("new_player", false);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.class_grades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classesSp.setAdapter(adapter);

        classesSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch (position) {

                    case 0:
                        fileName1 = "alef_bet_";


                        break;
                    case 1:
                        fileName1 = "gimel_dalet_";

                        break;
                    case 2:

                        //TODO
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                fileName1 = "alef_bet_";
            }
        });






        radioDifficult.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.normal_radio_btn:
                        fileName2 = "normal_1.txt";
                        break;
                    case R.id.advance_radio_btn:
                        fileName2 = "advenced_1.txt";
                        break;
                    default:
                        return;
                }

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playing = true;
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("keep_playing",1);



                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


           startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.append(fileName1).append(fileName2);
                playing = false;

                Intent intent = new Intent(RegisterActivity.this, PlayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//to
                Intent regIntent = new Intent(RegisterActivity.this,MainActivity.class);
             //   regIntent.putExtra("new_player",newPlayer);
                //clear the last activity and go to the play activity.
ControllMusic.getInstance().stopPlaying();
                ControllMusic.getInstance().releasePlaying();


                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {//when the user press back button.moving to the main activity.
        super.onBackPressed();
        playing = true;
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        intent.putExtra("keep_playing",1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//to
        //clear the last activity and go to the play activity.
        startActivity(intent);

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(keepPlaying == 0){

            ControllMusic.getInstance().initalizeMediaPlayer(this,R.raw.first_screen);
            ControllMusic.getInstance().startPlaying();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

         editor.putString("a-b",sb.toString());
         sb.append(player);
        editor.putString("player_name",player.toString());
         editor.commit();
    }

}
