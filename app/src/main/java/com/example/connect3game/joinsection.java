package com.example.connect3game;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;

public class joinsection extends AppCompatActivity {

    String ap;
    boolean activeplaya;
    boolean isxturn = true;
    boolean isoturn = Boolean.parseBoolean(null);
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Double> xpos, opos;
    int i = 0, u = 0;
    int mymove = 0;
    String[] occupied = new String[9];
    boolean isgameover = false;
    GridLayout gridLayout3;
    String positionAndOccupiedBy[] = new String[9];
    String roomname;
    private ListenerRegistration notelistner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinsection);
        //roomname = getIntent().getStringExtra("roomname");
        roomname = getIntent().getStringExtra("rname");
        activeplaya = getIntent().getExtras().getBoolean("activeplaya");
        for (int u = 0; u < occupied.length; u++) {
            occupied[u] = "z";
        }


        gridLayout3 = findViewById(R.id.gridLayout3);
        gridLayout3.setEnabled(false);
        int childCount = gridLayout3.getChildCount();
    }

    //for (int i= 0; i < childCount; i++) {
    //ImageView container = (ImageView) gridLayout3.getChildAt(i);
    //container.setOnClickListener(new View.OnClickListener() {
    public void dropin2(View view) {
        Log.i("clicks", "onClick: " + isoturn);

        ImageView counter = (ImageView) view;
        counter.setTranslationY(-1500);
        Log.i(" check", counter.getTag().toString());
        final int i = Integer.parseInt(counter.getTag().toString());

        if (occupied[i] == "z" && !isgameover) {


            if (activeplaya == false && isoturn == true) {


                counter.setImageResource(R.drawable.ooo);
                counter.animate().translationYBy(1500).setDuration(1000);
                positionAndOccupiedBy[i] = "y";
                occupied[i] = "o";

                AddtoDB addtoDB = new AddtoDB();
                addtoDB.uploadPositionmap(new AddPositionsToDb() {
                    @Override
                    public String getOccupiedBy() {
                        return "o";
                    }

                    @Override
                    public int getGridTag() {
                        return i;
                    }

                    @Override
                    public String rn() {
                        return roomname;
                    }

                    @Override
                    public Boolean getWhoseTurn() {
                        isoturn = false;
                        return isoturn;
                    }


                }, roomname, new OnDataUploaded() {
                    @Override
                    public void datauploaded() {
                        gridLayout3.setEnabled(false);
                    }
                });
                occupied[i] = "y";
                ap = "x";
                view.setClickable(false);
                winningcondition();

            } else if (isoturn==false){
                Toast.makeText(getApplicationContext(), ("already occupied or invalid"), Toast.LENGTH_SHORT).show();
            }
        }
        else if(isgameover==true) {
            Log.i(" check", "game over");
            Toast.makeText(joinsection.this, ("Game is over"), Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    protected void onStart() {

        super.onStart();
        try {


            notelistner = db.collection("rooms")
                    .document(roomname).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            if (documentSnapshot.exists()) {
                                try {

                                    isoturn = documentSnapshot.getBoolean("isOturn");
                                    Log.i("isoturnbool", "onEvent: " + isoturn);

                                    mymove++;

                                    gridLayout3 = findViewById(R.id.gridLayout3);
                                    if (isoturn){
                                        gridLayout3.setEnabled(true);
                                    }
                                    Long xkapos = documentSnapshot.getLong("x");
                                    Log.i("plaaa", "onEvent: "+xkapos);
                                    int val1 = xkapos.intValue();
                                    occupied[val1]="x";
                                    View iv = gridLayout3.findViewWithTag(xkapos.toString());

                                    Log.i("JoinSection", "is Imageview null"+(iv==null));
                                    iv.setBackgroundResource(R.drawable.cancel);
                                    iv.setClickable(false);
                                    winningcondition();

                                }
                                catch (NullPointerException er) {

                                    Log.e("snapshotListener", "onEvent: " + er);

                                }
                            }
                        }
                    });
        }catch (NullPointerException err){
            Log.e("snap", "onStart: "+err );
        }
    }

    public void winningcondition() {
        if (occupied[0] == occupied[1] && occupied[1] == occupied[2] && occupied[2] == "y") {
            showToastCenter("o won");
            isgameover = true;

        } else if (occupied[3] == occupied[4] && occupied[4] == occupied[5] && occupied[5] == "y") {
            showToastCenter("o won");
            isgameover = true;
        } else if (occupied[6] == occupied[7] && occupied[7] == occupied[8] && occupied[8] == "y") {
            showToastCenter("o won");
            isgameover = true;
        } else if (occupied[0] == occupied[3] && occupied[3] == occupied[6] && occupied[6] == "y") {
            showToastCenter("o won");
            isgameover = true;

        } else if (occupied[1] == occupied[4] && occupied[4] == occupied[7] && occupied[7] == "y") {
            showToastCenter("o won");
            isgameover = true;

        } else if (occupied[2] == occupied[5] && occupied[5] == occupied[8] && occupied[8] == "y") {
            showToastCenter("o won");
            isgameover = true;

        } else if (occupied[0] == occupied[4] && occupied[4] == occupied[8] && occupied[8] == "y") {
            showToastCenter("o won");
            isgameover = true;

        } else if (occupied[2] == occupied[4] && occupied[4] == occupied[6] && occupied[6] == "y") {
            showToastCenter("o won");
            isgameover = true;

        } else if (occupied[0] == occupied[1] && occupied[1] == occupied[2] && occupied[2] == "x") {
            showToastCenter("x won");
            isgameover = true;
        } else if (occupied[3] == occupied[4] && occupied[4] == occupied[5] && occupied[5] == "x") {
            showToastCenter("x won");
            isgameover = true;
        } else if (occupied[6] == occupied[7] && occupied[7] == occupied[8] && occupied[8] == "x") {
            showToastCenter("x won");
            isgameover = true;
        } else if (occupied[0] == occupied[3] && occupied[3] == occupied[6] && occupied[6] == "x") {
            showToastCenter("x won");
            isgameover = true;

        } else if (occupied[1] == occupied[4] && occupied[4] == occupied[7] && occupied[7] == "x") {
            showToastCenter("x won");
            isgameover = true;

        } else if (occupied[2] == occupied[5] && occupied[5] == occupied[8] && occupied[8] == "x") {
            showToastCenter("x won");
            isgameover = true;

        } else if (occupied[0] == occupied[4] && occupied[4] == occupied[8] && occupied[8] == "x") {
            showToastCenter("x won");
            isgameover = true;

        } else if (occupied[2] == occupied[4] && occupied[4] == occupied[6] && occupied[6] == "x") {
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