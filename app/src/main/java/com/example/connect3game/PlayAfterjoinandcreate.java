package com.example.connect3game;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayAfterjoinandcreate extends AppCompatActivity {

    String ap;
    boolean activeplaya;
    boolean isxturn = true;
    int mymove = 0;
    boolean isoturn = false;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Double> xpos, opos;
    int i = 0, u = 0;
    String[] occupied = new String[9];
    boolean isgameover = false;
    GridLayout gridLayout2;
    String positionAndOccupiedBy[] = new String[9];
    String roomname;
    private ListenerRegistration notelistner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_afterjoinandcreate);

        roomname = getIntent().getStringExtra("room_name");
        activeplaya = getIntent().getExtras().getBoolean("activeplaya");
        for (int u = 0; u < occupied.length; u++) {
            occupied[u] = "z";
        }


        gridLayout2 = findViewById(R.id.gridLayout2);
        int childCount = gridLayout2.getChildCount();
        //gridLayout2.setEnabled(false);

    }

                public void dropin2(View view) {

                    ImageView counter = (ImageView) view;
                    counter.setTranslationY(-1500);
                    Log.i(" check", counter.getTag().toString());
                    final int i = Integer.parseInt(counter.getTag().toString());

                    if (occupied[i] == "z" && !isgameover) {

                        if (activeplaya == true && !isoturn ) {
                           // winningcondition();

                            Log.i(" check", "game over");
                            counter.setImageResource(R.drawable.cancel);
                            counter.animate().translationYBy(1500).setDuration(1000);
                            positionAndOccupiedBy[i] = "x";
                            occupied[i] = ap;

                            AddtoDB addtoDB = new AddtoDB();
                            addtoDB.uploadPositionmap(new AddPositionsToDb() {
                                @Override
                                public String getOccupiedBy() {
                                    return "x"; //positionAndOccupiedBy[i];
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
                                    isoturn = true;
                                    return isoturn;
                                }


                            }, roomname,new OnDataUploaded() {
                                @Override
                                public void datauploaded() {
                                    gridLayout2.setEnabled(false);
                                }
                            });
                            occupied[i] = "x";

                            view.setClickable(false);
                            winningcondition();


                        } else if (isoturn==true){
                            Toast.makeText(PlayAfterjoinandcreate.this, "Invalid move or already occupied", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else if (isgameover){
                        Log.i(" check", "game over");

                        Toast.makeText(getApplicationContext(), ("Game is over"), Toast.LENGTH_SHORT).show();

                    }
                }


    @Override
    protected void onStart() {
        super.onStart();
        try {
            gridLayout2 = findViewById(R.id.gridLayout2);


            notelistner = db.collection("rooms")
                    .document(roomname).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            if (documentSnapshot.exists()) {
                                try {

                                    isoturn = documentSnapshot.getBoolean("isOturn");
                                    mymove++;
                                    Log.i("isoturnbool", "onEvent: " + isoturn);
                                    if (!isoturn){
                                        gridLayout2.setEnabled(true);
                                    }
                                    //double xkapos = documentSnapshot.getDouble("x");
                                    Long okapos = documentSnapshot.getLong("o");
                                    Log.i("plaaa", "onEvent: " + okapos);
                                    int val1 = okapos.intValue();
                                    occupied[val1] = "y";
                                    View iv = gridLayout2.findViewWithTag(okapos.toString());
                                    Log.i("JoinSection", "is Imageview null" + (iv == null));
                                    iv.setBackgroundResource(R.drawable.ooo);
                                    iv.setClickable(false);
                                    winningcondition();


                                } catch (NullPointerException er) {
                                    Log.e("snapshotListener", "onEvent: " + er);
                                }


                            }
                        }
                    });
        } catch (NullPointerException err) {
            Log.e("snap", "onStart: " + err);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        notelistner.remove();
    }

    public void back2(View view) {
        onBackPressed();
    }


    interface GetActivePlayer {
        public Bundle getActive();

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


