package com.example.a1tutorial.activity.cocinero.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.adaptador.AdaptadorCocineroComandas;
import com.example.a1tutorial.models.Comanda;
import com.example.a1tutorial.providers.ComandaDatabasePorvider;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class Fragment_comandas extends Fragment {

    View mView;

    ComandaDatabasePorvider comandaDatabase;

    RecyclerView listadoDeComandas;

    AdaptadorCocineroComandas adaptadorCocineroComandas;

    public Fragment_comandas(){
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_cocinero_comandas, container,false);

        listadoDeComandas = mView.findViewById(R.id.listado_comandas_cocinero);
        listadoDeComandas.setLayoutManager(new LinearLayoutManager(getContext()));

        comandaDatabase = new ComandaDatabasePorvider();

        cargarCardView(comandaDatabase.getComandasSinCocinar());

        return mView;
    }

    private void cargarCardView(Query query) {
        FirestoreRecyclerOptions<Comanda> options = new FirestoreRecyclerOptions.Builder<Comanda>().setQuery(query, Comanda.class).build();
        adaptadorCocineroComandas = new AdaptadorCocineroComandas(options);
        listadoDeComandas.setAdapter(adaptadorCocineroComandas);
        adaptadorCocineroComandas.startListening();
    }

}
