package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PlayAfterjoinandcreate extends AppCompatActivity {

     String ap;
    int i = 0, u = 0;
    String[] occupied = new String[9];
    boolean isgameover = false;
    GridLayout gridLayout2;

     public void getactivee(final GetActivePlayer getactivePlayer){
        Bundle activep= getactivePlayer.getActive();
        ap=activep.getString("nowActivePlayer");
        Log.i("i", ap); //y is x ie.activeplayer is x.

    }
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_afterjoinandcreate);
        for (int u = 0; u < occupied.length; u++) {
            occupied[u] = "z";
        }

        gridLayout2 = findViewById(R.id.gridLayout2);
        int childCount = gridLayout2.getChildCount();

        for (int i= 0; i < childCount; i++){
            ImageView container = (ImageView) gridLayout2.getChildAt(i);
            container.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    ImageView counter = (ImageView) view;
                    counter.setTranslationY(-1500);
                    Log.i(" check", counter.getTag().toString());
                    final int i = Integer.parseInt(counter.getTag().toString());

                    if (occupied[i] == "z" && !isgameover) {
                        ap = "x";


                        if (ap == "x") {
                            Log.i(" check", "game over");
                            counter.setImageResource(R.drawable.cancel);
                            counter.animate().translationYBy(1500).setDuration(1000);


                            occupied[i] = ap;
                            AddtoDB addtoDB= new AddtoDB();
                            addtoDB.uploadPositionmap(new AddPositionsToDb() {
                                @Override
                                public String getOccupiedBy() {
                                    return occupied[i];
                                }

                                @Override
                                public int getGridTag() {
                                    return i;
                                }


                            });


                            ap = "y";
                            view.setClickable(false);


                        } else if (ap == "y") {

                            Log.i(" check", "game over");
                            counter.setImageResource(R.drawable.ooo);
                            counter.animate().translationYBy(1500).setDuration(1000);
                            occupied[i] = ap;

                            ap = "x";
                            view.setClickable(false);

                        } else {
                            Toast.makeText(getApplicationContext(), ("already occupied or invalid"), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });
        }

    }
    public void back2(View view){
            onBackPressed();
    }


interface GetActivePlayer{
        public Bundle getActive();

    }}

