package com.example.a1tutorial.providers;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDatabaseProvider {


    CollectionReference database;

    public UserDatabaseProvider(){
        database = FirebaseFirestore.getInstance().collection("Users");

    }

    public CollectionReference getDatabase() {
        return database;
    }


}
