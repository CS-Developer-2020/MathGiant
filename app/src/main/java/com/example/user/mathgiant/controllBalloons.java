package com.example.user.mathgiant;

import android.content.Context;

public class controllBalloons {




    private static controllBalloons refrence = null;


    public static controllBalloons getInstance(){
        if(refrence == null){
            refrence = new controllBalloons();
        }
        return refrence;
    }

    public void initalizeBallons(Context context, int ballonID){

    }


}