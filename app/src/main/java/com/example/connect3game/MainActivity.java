package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        for (int u = 0; u < occupied.length; u++) {
            occupied[u] = 4;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(" check", "on resume");

    }

    int activeplayer = 0;
    int i = 0, u = 0;
    int[] occupied = new int[9];
    boolean isgameover = false;
    GridLayout gridLayout;


    public void dropin(View view) {

        ImageView counter = (ImageView) view;
        counter.setTranslationY(-1500);
        Log.i(" check", counter.getTag().toString());
        i = Integer.parseInt(counter.getTag().toString());

        if (occupied[i] == 4 && !isgameover) {


            if (activeplayer == 0) {
                Log.i(" check", "game over");
                counter.setImageResource(R.drawable.cancel);
                counter.animate().translationYBy(1500).setDuration(1000);
                Button vibrateButton = new Button(this);

                occupied[i] = activeplayer;
                activeplayer = 1;
                view.setClickable(false);

                winningcondition();

            } else if (activeplayer == 1) {

                Log.i(" check", "game over");
                counter.setImageResource(R.drawable.ooo);
                counter.animate().translationYBy(1500).setDuration(1000);
                occupied[i] = activeplayer;

                activeplayer = 0;
                view.setClickable(false);

                winningcondition();

            } else {
                Toast.makeText(getApplicationContext(), ("already occupied or invalid"), Toast.LENGTH_SHORT).show();
            }

            if (!isgameover) {

                isgameover = matchdraw();
            }

        } else {
            Log.i(" check", "game over");

            Toast.makeText(getApplicationContext(), ("Game is over"), Toast.LENGTH_SHORT).show();

        }
    }


    public void playagain(View view) {

        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void back(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = new Intent(getApplicationContext(), Mainmain.class);
        startActivity(intent);
    }


    public boolean matchdraw() {

        for (int q : occupied) {
            if (q == 4) {
                Log.i(" check", "false returned");

                return false;
            }
        }


        Toast.makeText(getApplicationContext(), ("Match Draw"), Toast.LENGTH_SHORT).show();
        Log.i(" check", "true returned");
        return true;

    }


    public void winningcondition() {
        if (occupied[0] == occupied[1] && occupied[1] == occupied[2] && occupied[2] == 1) {
            showToastCenter("o won");
            isgameover = true;

        } else if (occupied[3] == occupied[4] && occupied[4] == occupied[5] && occupied[5] == 1) {
            showToastCenter("o won");
            isgameover = true;
        } else if (occupied[6] == occupied[7] && occupied[7] == occupied[8] && occupied[8] == 1) {
            showToastCenter("o won");
            isgameover = true;
        } else if (occupied[0] == occupied[3] && occupied[3] == occupied[6] && occupied[6] == 1) {
            showToastCenter("o won");
            isgameover = true;

        } else if (occupied[1] == occupied[4] && occupied[4] == occupied[7] && occupied[7] == 1) {
            showToastCenter("o won");
            isgameover = true;

        } else if (occupied[2] == occupied[5] && occupied[5] == occupied[8] && occupied[8] == 1) {
            showToastCenter("o won");
            isgameover = true;

        } else if (occupied[0] == occupied[4] && occupied[4] == occupied[8] && occupied[8] == 1) {
            showToastCenter("o won");
            isgameover = true;

        } else if (occupied[2] == occupied[4] && occupied[4] == occupied[6] && occupied[6] == 1) {
            showToastCenter("o won");
            isgameover = true;

        } else if (occupied[0] == occupied[1] && occupied[1] == occupied[2] && occupied[2] == 0) {
            showToastCenter("x won");
            isgameover = true;
        } else if (occupied[3] == occupied[4] && occupied[4] == occupied[5] && occupied[5] == 0) {
            showToastCenter("x won");
            isgameover = true;
        } else if (occupied[6] == occupied[7] && occupied[7] == occupied[8] && occupied[8] == 0) {
            showToastCenter("x won");
            isgameover = true;
        } else if (occupied[0] == occupied[3] && occupied[3] == occupied[6] && occupied[6] == 0) {
            showToastCenter("x won");
            isgameover = true;

        } else if (occupied[1] == occupied[4] && occupied[4] == occupied[7] && occupied[7] == 0) {
            showToastCenter("x won");
            isgameover = true;

        } else if (occupied[2] == occupied[5] && occupied[5] == occupied[8] && occupied[8] == 0) {
            showToastCenter("x won");
            isgameover = true;

        } else if (occupied[0] == occupied[4] && occupied[4] == occupied[8] && occupied[8] == 0) {
            showToastCenter("x won");
            isgameover = true;

        } else if (occupied[2] == occupied[4] && occupied[4] == occupied[6] && occupied[6] == 0) {
            showToastCenter("x won");
            isgameover = true;

        }


    }

    void showToastCenter(String s) {
        Toast t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER, 0, 0);

        t.show();
    }


}