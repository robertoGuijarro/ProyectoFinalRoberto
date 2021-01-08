package com.example.a1tutorial.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CartaComidaDatabaseProvider {

    CollectionReference mColection;

    public CartaComidaDatabaseProvider (){
        mColection = FirebaseFirestore.getInstance().collection("Carta");
    }

    public Query getAll(){
        return mColection;
    }
    public Query getCarne(){
        return mColection.whereEqualTo("tipo", "carne");
    }
    public Query getPescado(){
        return mColection.whereEqualTo("tipo", "pescado");
    }
    public Query getPostre(){
        return mColection.whereEqualTo("tipo", "postre");
    }
    public Query getEntrantes(){
        return mColection.whereEqualTo("tipo", "entrantes");
    }
    public Query getCocidos(){
        return mColection.whereEqualTo("tipo", "cocidos");
    }
    public Task<DocumentSnapshot> getDocument(String document){
        return mColection.document(document).get();
    }

    public CollectionReference getmColection() {
        return mColection;
    }
}
