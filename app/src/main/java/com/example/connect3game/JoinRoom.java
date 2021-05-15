package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JoinRoom extends AppCompatActivity {

     Checkandjoin cj ;
    String existingRoomname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_room);
        final Bundle bundlee= new Bundle();
        cj = new Checkandjoin();

        final TextView existingRoomName=findViewById(R.id.existingRoomName);
        Button button5=findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                existingRoomname = existingRoomName.getText().toString();
               // bundlee.putString("existingRoomname",existingRoomname);
                cj.joinRoom(new roomJoincallback() {
                    @Override
                    public String getTojoinRoomName(){
                        return existingRoomname;
                    }

                    @Override
                    public void startActivityy(String roomname) {
                        Intent intent = new Intent(getApplicationContext(),joinsection.class);
                        bundlee.putString("rname",roomname);
                        intent.putExtra("roomname",roomname);
                        intent.putExtras(bundlee);
                        intent.putExtra("activeplaya",false);
                        startActivity(intent);
                    }

                });
            }

        });

    }

}