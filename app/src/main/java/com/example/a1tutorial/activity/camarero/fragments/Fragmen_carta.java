package com.example.a1tutorial.activity.camarero.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.adaptador.AdaptadorCartaComida;
import com.example.a1tutorial.models.Carta;
import com.example.a1tutorial.providers.CartaComidaDatabaseProvider;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class Fragmen_carta extends Fragment implements View.OnClickListener {

    RecyclerView listadoCartaComida;
    CartaComidaDatabaseProvider cartaDatabase;
    View mView;
    Button btnCarne, btnPescado, btnCocido, btnPostre, btnEntrante;
    public Fragmen_carta (){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_camarero_carta, container, false);

        listadoCartaComida = mView.findViewById(R.id.listadorCartaComida);
        listadoCartaComida.setLayoutManager(new LinearLayoutManager(getContext()));

        btnCarne = mView.findViewById(R.id.btn_carta_carne);
        btnCarne.setOnClickListener(this);
        btnPescado = mView.findViewById(R.id.btn_carta_pescado);
        btnPescado.setOnClickListener(this);
        btnCocido = mView.findViewById(R.id.btn_carta_cocido);
        btnCocido.setOnClickListener(this);
        btnPostre = mView.findViewById(R.id.btn_carta_postre);
        btnPostre.setOnClickListener(this);
        btnEntrante = mView.findViewById(R.id.btn_carta_entrante);
        btnEntrante.setOnClickListener(this);

        cartaDatabase = new CartaComidaDatabaseProvider();

        cargarCardView(cartaDatabase.getAll());
        return mView;
    }


    public void cargarCardView(Query query){
        FirestoreRecyclerOptions<Carta> options = new FirestoreRecyclerOptions.Builder<Carta>().setQuery(query, Carta.class).build();
        AdaptadorCartaComida adaptador = new AdaptadorCartaComida(options);

        listadoCartaComida.setAdapter(adaptador);
        adaptador.startListening();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnCarne.getId()){
            cargarCardView(cartaDatabase.getCarne());
        }

        if (v.getId() == btnCocido.getId()){
            cargarCardView(cartaDatabase.getCocidos());
        }

        if (v.getId() == btnPescado.getId()){
            cargarCardView(cartaDatabase.getPescado());
        }

        if (v.getId() == btnPostre.getId()){
            cargarCardView(cartaDatabase.getPostre());
        }
        if (v.getId() == btnEntrante.getId()){
            cargarCardView(cartaDatabase.getEntrantes());
        }
    }
}
