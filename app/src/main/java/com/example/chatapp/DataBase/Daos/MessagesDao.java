package com.example.chatapp.DataBase.Daos;

import com.example.chatapp.DataBase.Model.Message;
import com.example.chatapp.DataBase.MyDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentReference;

public class MessagesDao {
    public static void addMessage(Message message, OnCompleteListener onSuccessListener,
                                  OnFailureListener onFailureListener){
        DocumentReference newMessageRef = MyDataBase.getMessagesReference()
                .document();
        message.setId(newMessageRef.getId());
        newMessageRef.set(message)
                .addOnCompleteListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

}
