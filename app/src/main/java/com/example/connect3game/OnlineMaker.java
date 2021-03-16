package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OnlineMaker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_maker2);
    }

    public void joinroom(View view){
        Intent intent = new Intent(getApplicationContext(),JoinRoom.class);
        startActivity(intent);
    }
    public void  createroom(View view){
        Intent intent = new Intent(getApplicationContext(),CreateRoom.class);
        startActivity(intent);
    }

}