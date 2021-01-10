package com.example.a1tutorial.activity.camarero.fragments_camarero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.models.Comanda;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import com.example.a1tutorial.R;
import com.example.a1tutorial.adaptador.AdaptadorComandasListado;
import com.example.a1tutorial.providers.AuthProvider;
import com.example.a1tutorial.providers.ComandaDatabasePorvider;

public class Fragment_comandas extends Fragment {
    
    View mView;
    
    ComandaDatabasePorvider comandaDatabase;
    
    RecyclerView listadoDeComandas;
    
    AdaptadorComandasListado adaptadorComandasListado;
    AuthProvider auth;
    
    public Fragment_comandas(){
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_camarero_comandas, container, false);

        comandaDatabase = new ComandaDatabasePorvider();
        auth = new AuthProvider();
        
        listadoDeComandas = mView.findViewById(R.id.listadoCamareroComandas);
        listadoDeComandas.setLayoutManager(new LinearLayoutManager(getContext()));

        cargarCardView(comandaDatabase.getComandasCamarero(auth.idAuth()));
        return mView;
    }

    private void cargarCardView(Query query) {
        FirestoreRecyclerOptions<Comanda>options = new FirestoreRecyclerOptions.Builder<Comanda>().setQuery(query, Comanda.class).build();
        adaptadorComandasListado = new AdaptadorComandasListado(options);
        listadoDeComandas.setAdapter(adaptadorComandasListado);
        adaptadorComandasListado.startListening();
    }
}
