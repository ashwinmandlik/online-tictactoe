package com.example.connect3game;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class IsItMyTurn{
    void whoseturn(final Ismyturn ismyturn,String roomname){

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("rooms")
//                .document(roomname)
//                .set(isOsturn).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.i("working", "onSuccess: ");
//            }
//        });
    }
}


class Checkandjoin{
     String roomtoJoin;


    private static final String TAG = "Checkandjoin";

    void joinRoom(final roomJoincallback callbackback) {

        final Bundle activeplayer = new Bundle();
        final PlayAfterjoinandcreate playAfterjoinandcreate=new PlayAfterjoinandcreate();

        roomtoJoin = callbackback.getTojoinRoomName();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("rooms")
                .document(roomtoJoin)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                       if(documentSnapshot.exists()) {
                           callbackback.startActivityy(roomtoJoin);
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

class GetRoomNameAfterJoin extends Checkandjoin{
    String GetRoomNameAfterJoin(){
     String s = roomtoJoin;
        return s;
    }

}




class CreateRoomandUploadtoDB {

     String UploadRoomNameToDb(final RoomCallback callback,String roomname){

         HashMap<String,Object> fs = new HashMap<>();

         fs.put("activeplayer","x");


         FirebaseFirestore db = FirebaseFirestore.getInstance();

         db.collection("rooms")
                 .document(roomname)
                 .set(fs)
         .addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void aVoid) {
                     callback.startActivity();
                 }
         });
         Log.i("madarchod", "UploadRoomNameToDb: "+ roomname);
         return "roomname";
     }


}


class AddtoDB{
    private static final String TAG = "ADDToDb";
    GetRoomNameAfterJoin getRoomNameAfterJoin= new GetRoomNameAfterJoin();


    void uploadPositionmap(AddPositionsToDb addPositionAndOccupied, String roomname, final OnDataUploaded onDataUploaded){
       String addOccupiedBy = addPositionAndOccupied.getOccupiedBy();
        int tag= addPositionAndOccupied.getGridTag();
        Map<String,Object> moves = new HashMap<>();
        ArrayList<Integer> movess = new ArrayList<>();
        movess.add(tag);
       // moves.put("moves",movess);
        moves.put(addOccupiedBy,tag);
        boolean isOturn= addPositionAndOccupied.getWhoseTurn();
        moves.put("isOturn",isOturn);
        Log.i(TAG, "uploadPositionmap: "+ moves);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Singleton s = Singleton.getInstance();
        Log.i(TAG, "uploadPositionmap: "+ roomname);
        db.collection("rooms")
                .document(roomname)
                .set(moves)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "onSuccess: working");
                        onDataUploaded.datauploaded();


                    }
                });



    }
}
interface OnDataUploaded{
    void datauploaded();
}

interface RoomCallback{

    String getRoomName();
    void startActivity();

}


interface roomJoincallback{
    String getTojoinRoomName();
    void startActivityy(String roomname);
}


interface AddPositionsToDb {
    String getOccupiedBy();
    int getGridTag();
    String rn();
    Boolean getWhoseTurn();
}

interface Ismyturn {

    Boolean getWhoseTurn();
}






