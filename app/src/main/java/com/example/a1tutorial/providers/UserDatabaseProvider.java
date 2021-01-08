package com.example.a1tutorial.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class UserDatabaseProvider {


    CollectionReference database;

    public UserDatabaseProvider(){
        database = FirebaseFirestore.getInstance().collection("Users");

    }

    public CollectionReference getDatabase() {
        return database;
    }

    public Task<DocumentSnapshot> getNameCamarero(String idCamarero){
        return database.document(idCamarero).get();
    }

    public Query getOficio (String email){
        return database.whereEqualTo("email", email);
    }
}
