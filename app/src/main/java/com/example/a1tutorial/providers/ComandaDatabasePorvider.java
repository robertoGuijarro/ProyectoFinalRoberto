package com.example.a1tutorial.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Query;

public class ComandaDatabasePorvider {
    CollectionReference mColection;

    public ComandaDatabasePorvider (){
        mColection = FirebaseFirestore.getInstance().collection("Comandas");
    }

    public CollectionReference getmColection() {
        return mColection;
    }

    public Task<QuerySnapshot> getAllDocument(){
        return mColection.get();
    }

    public Query getComandasCamarero(String idCamarero){
        return mColection.whereEqualTo("idCamarero", idCamarero).orderBy("mesa");
    }

    public Query getComandasPlatos(String document){
        return mColection.document(document).collection("platos");
    }

    public Query getComandasBebidas(String document){
        return mColection.document(document).collection("bebidas");
    }

    public Query getComandaPlatosCocinero(String document){
        return mColection.document(document).collection("platos").whereEqualTo("estado", "rojo");
    }

    public Query getComandasSinCocinar(){
        return mColection.whereEqualTo("servido", false).orderBy("nameCamarero");
    }

    public DocumentReference updatePlatoCocinero(String documentoComanda, String documentPlato){
        return mColection.document(documentoComanda).collection("platos").document(documentPlato);
    }

    public DocumentReference entregarComida (String documentoComanda, String documentPlato){
        return mColection.document(documentoComanda).collection("platos").document(documentPlato);
    }


    public DocumentReference entregarBebida (String documetoComanda, String documentoBebida){
        return mColection.document(documetoComanda).collection("bebidas").document(documentoBebida);
    }
    public Query getComandaBebidasBarra(String document){
        return mColection.document(document).collection("bebidas").whereEqualTo("estado", "rojo");
    }
    public DocumentReference updateBebidaBarra (String documentComanda, String documentBebida){
        return mColection.document(documentComanda).collection("bebidas").document(documentBebida);
    }

    public Query getComandaBebidas(){
        return mColection.whereEqualTo("servido", false).orderBy("nameCamarero");
    }
}
 