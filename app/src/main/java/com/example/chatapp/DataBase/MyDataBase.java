package com.example.chatapp.DataBase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyDataBase {
    public static final String ROOMS_REF = "rooms";
    public static final String USERS_REF = "users";
    public static final String MESSAGES_REF = "messages";
    private static CollectionReference getCollection(String collectionName){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        return firebaseFirestore.collection(collectionName);

    }
    public static CollectionReference getRoomsReference(){
        return getCollection(ROOMS_REF);
    }
    public static CollectionReference getUsersReference(){
        return getCollection(USERS_REF);
    }
    public static CollectionReference getMessagesReference(){
        return getCollection(MESSAGES_REF);
    }
}
