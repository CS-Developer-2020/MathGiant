package com.example.user.mathgiant;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Question extends  Activity  implements Serializable {
    private int numQuestion;
    private String question;
    private String answers;
    private File file;
    private InputStream fin ;
    private List<String> questionsList = new ArrayList<>();
    public Question()  {



            readFronFile();

    }



    public int getNumQuestion() {
        return numQuestion;
    }

    public void setNumQuestion(int numQuestion) {
        this.numQuestion = numQuestion;
    }

    public String getAnswers() {
        return answers;
    }



   public void readFronFile()  {

       BufferedReader reader = null;
       try {
           reader = new BufferedReader(
                   new InputStreamReader(getAssets().open("questions.txt")));

           // do reading, usually loop until end of file reading
           String mLine;
           while ((mLine = reader.readLine()) != null) {
               questionsList.add(reader.readLine());

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




   }

       public String getQuestion(int index) {

        return questionsList.get(index);
    }


}

