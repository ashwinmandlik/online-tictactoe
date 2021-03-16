package com.example.connect3game;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


class Checkandjoin{



    private static final String TAG = "Checkandjoin";

    void joinRoom(final roomJoincallback callbackback) {

        final Bundle activeplayer = new Bundle();
        final PlayAfterjoinandcreate playAfterjoinandcreate=new PlayAfterjoinandcreate();

        final String roomtoJoin = callbackback.getTojoinRoomName();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("rooms")
                .document(roomtoJoin)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                       if(documentSnapshot.exists()) {
                           callbackback.startActivityy();
                           db.collection("rooms")
                                   .document(roomtoJoin)
                                   .get()
                                   .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                       @Override
                                       public void onSuccess(DocumentSnapshot documentSnapshot) {

                                           //String h= documentSnapshot.get("activeplayer").toString();
                                           activeplayer.putString("nowActivePlayer","x");
                                           playAfterjoinandcreate.getactivee(new PlayAfterjoinandcreate.GetActivePlayer() {
                                               @Override
                                               public Bundle getActive() {
                                                   return activeplayer;
                                               }
                                           });
                                           Log.i("field", "working" );
                                       }
                                   });
                       }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}


class CreateRoomandUploadtoDB {

    static String roomname;

     String UploadRoomNameToDb(final RoomCallback callback){

         HashMap<String,Object> fs = new HashMap<>();

         fs.put("activeplayer","x");

        roomname = callback.getRoomName();
         FirebaseFirestore db = FirebaseFirestore.getInstance();
         db.collection("rooms")
                 .document(roomname)
                 .set(fs)
         .addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void aVoid) {
                 callback.startActivity();
             }
         }

         )

         ;
         Log.i("madarchod", "UploadRoomNameToDb: "+ roomname);
         return roomname;
     }

}



class AddtoDB{
    private static final String TAG = "ADDToDb";


    void uploadPositionmap(AddPositionsToDb addPositionAndOccupied){
       // Log.i("madarchoddddddddd", "uploadPositionmap: "+ CreateRoomandUploadtoDB.roomname);
       String addOccupiedBy = addPositionAndOccupied.getOccupiedBy();
        int tag= addPositionAndOccupied.getGridTag();


        HashMap<String,Integer> mapp= new HashMap<String, Integer>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.i(TAG, "uploadPositionmap: "+ CreateRoomandUploadtoDB.roomname);
        db.collection("rooms")
                .document("why")
                .set(mapp);


    }
}

interface RoomCallback{
    String getRoomName();
    void startActivity();
}


interface roomJoincallback{
    String getTojoinRoomName();
    void startActivityy();
}


interface AddPositionsToDb {
    String getOccupiedBy();
    int getGridTag();
}




