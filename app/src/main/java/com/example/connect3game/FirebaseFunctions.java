//package com.example.connect3game;
//
//import android.util.Log;
//
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class FirebaseFunctions {
//
//    public final String TAG = "FirebaseFunctions";
//
//    FirebaseFirestore fb = FirebaseFirestore.getInstance();
//
//    void uploadArray(){
//
//        Map<String, ArrayList<String>> moves = new HashMap<>();
//        ArrayList<String> movess = new ArrayList<>();
//        movess.add("first");
//        movess.add("sec");
//        movess.add("third");
//        movess.add("fourth");
//
//        moves.put("moves_x",movess);
//
//        fb.collection("rooms")
//                .document("arrays")
//                .set(moves)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.i(TAG, "Array Uploaded successfully");
//                    }
//                });
//    }
//
//}
