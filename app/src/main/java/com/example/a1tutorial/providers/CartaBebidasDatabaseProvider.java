package com.example.a1tutorial.providers;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CartaBebidasDatabaseProvider {

    CollectionReference mColection;

    public CartaBebidasDatabaseProvider(){
        mColection = FirebaseFirestore.getInstance().collection("CartaBebidas");
    }

    public CollectionReference getmColection() {
        return mColection;
    }

    public Query getAll(){
        return mColection;
    }
}
