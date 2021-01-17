package com.example.a1tutorial.activity.barra.Fragments_barra;

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
import com.example.a1tutorial.adaptador.AdaptadorBarraComandas;
import com.example.a1tutorial.models.Comanda;
import com.example.a1tutorial.providers.ComandaDatabasePorvider;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class Fragment_barra_comandas extends Fragment {

    View mView;

    RecyclerView listadoDeComandas;

    AdaptadorBarraComandas adaptadorBarraComandas;

    ComandaDatabasePorvider comandaDatabasePorvider;
    public Fragment_barra_comandas(){
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_barra_comandas, container,false);

        comandaDatabasePorvider = new ComandaDatabasePorvider();

        listadoDeComandas = mView.findViewById(R.id.listadoBarraComandas);
        listadoDeComandas.setLayoutManager(new LinearLayoutManager(getContext()));

        cargarCardView(comandaDatabasePorvider.getComandaBebidas());

        return mView;
    }

    private void cargarCardView(Query comandaBebidas) {
        FirestoreRecyclerOptions<Comanda> options = new FirestoreRecyclerOptions.Builder<Comanda>().setQuery(comandaBebidas, Comanda.class).build();
        adaptadorBarraComandas = new AdaptadorBarraComandas(options);

        listadoDeComandas.setAdapter(adaptadorBarraComandas);
        adaptadorBarraComandas.startListening();
    }
}
