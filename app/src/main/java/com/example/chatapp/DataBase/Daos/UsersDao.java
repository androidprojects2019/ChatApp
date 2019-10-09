package com.example.chatapp.DataBase.Daos;

import com.example.chatapp.DataBase.Model.User;
import com.example.chatapp.DataBase.MyDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class UsersDao {

    public static void addUser(User user, OnCompleteListener onCompleteListener, OnFailureListener onFailureListener) {
        MyDataBase.getUsersReference()
                .document(user.getId())
                .set(user)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    public static void getUser(String userId, OnCompleteListener onCompleteListener, OnFailureListener onFailureListener) {

        MyDataBase.getUsersReference().whereEqualTo("id",userId)
                .get()
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }
}
