package com.example.a1tutorial.activity.cocinero.Fragments_cocinero;

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
import com.example.a1tutorial.adaptador.AdaptadorCocineroComandasComida;
import com.example.a1tutorial.models.Comanda;
import com.example.a1tutorial.providers.ComandaDatabasePorvider;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class Fragment_cocinero_comandas extends Fragment {

    View mView;

    RecyclerView listadoDeComandas;

    AdaptadorCocineroComandasComida adaptadorCocineroComandasComida;

    ComandaDatabasePorvider comandaDatabasePorvider;
    public Fragment_cocinero_comandas(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_cocinero_comandas,container,false);

        comandaDatabasePorvider = new ComandaDatabasePorvider();

        listadoDeComandas = mView.findViewById(R.id.listadoCocineroComandas);
        listadoDeComandas.setLayoutManager(new LinearLayoutManager(getContext()));

        cargarCardView(comandaDatabasePorvider.getComandasSinCocinar());

        return mView;
    }

    private void cargarCardView(Query comandasSinCocinar) {
        FirestoreRecyclerOptions<Comanda> options = new FirestoreRecyclerOptions.Builder<Comanda>().setQuery(comandasSinCocinar, Comanda.class).build();
        adaptadorCocineroComandasComida = new AdaptadorCocineroComandasComida(options);

        listadoDeComandas.setAdapter(adaptadorCocineroComandasComida);
        adaptadorCocineroComandasComida.startListening();
    }
}
