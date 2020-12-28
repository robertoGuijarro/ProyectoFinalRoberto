package com.example.a1tutorial.providers;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ComandaDatabasePorvider {
    CollectionReference mColection;

    public ComandaDatabasePorvider (){
        mColection = FirebaseFirestore.getInstance().collection("Comandas");
    }

    public CollectionReference getmColection() {
        return mColection;
    }
}
 