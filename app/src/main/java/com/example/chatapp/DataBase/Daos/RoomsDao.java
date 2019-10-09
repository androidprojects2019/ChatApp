package com.example.chatapp.DataBase.Daos;

import com.example.chatapp.DataBase.Model.Room;
import com.example.chatapp.DataBase.MyDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

public class RoomsDao {
    public static void addRoom(Room room, OnSuccessListener onSuccessListener,
                               OnFailureListener onFailureListener){
        DocumentReference roomRef = MyDataBase.getRoomsReference().document();
        room.setId(roomRef.getId());
        roomRef.set(room)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
    public static void getAllRooms(OnCompleteListener onCompleteListener, OnFailureListener onFailureListener){
        CollectionReference rooms = MyDataBase.getRoomsReference();
        rooms.get()
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

}
