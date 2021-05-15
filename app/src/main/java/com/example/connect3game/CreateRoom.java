package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateRoom extends AppCompatActivity {

    String newRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        Button b = findViewById(R.id.createroomText);
        final Bundle bundle = new Bundle();
        final EditText roomNameEditText=findViewById(R.id.existingRoomName);

        final CreateRoomandUploadtoDB cb = new CreateRoomandUploadtoDB();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String room_name = roomNameEditText.getText().toString();
                bundle.putString("room_name",room_name);
                cb.UploadRoomNameToDb(new RoomCallback() {
                    @Override
                    public String getRoomName() {
                        return room_name;
                    }

                    @Override
                    public void startActivity() {
                        Intent intent=new Intent(getApplicationContext(), PlayAfterjoinandcreate.class);
                        intent.putExtra("activeplaya",true);
                        intent.putExtra("room_name",room_name);
                        CreateRoom.this.startActivity(intent);
                    }


                },roomNameEditText.getText().toString());
            }
        });




    }


}