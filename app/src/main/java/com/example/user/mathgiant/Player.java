package com.example.user.mathgiant;

import android.app.Activity;
import android.view.View;

public class Player  {

    private String name;
    private long score;

    public Player(String name, long score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public long getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
