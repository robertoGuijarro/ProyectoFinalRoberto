package com.example.a1tutorial.activity.camarero.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.activity.RegisterActivity;
import com.example.a1tutorial.activity.camarero.Fragment_camarero;
import com.example.a1tutorial.adaptador.AdaptadorCartaComida;
import com.example.a1tutorial.adaptador.AdaptadorComandaComida;
import com.example.a1tutorial.models.Carta;
import com.example.a1tutorial.models.Comanda;
import com.example.a1tutorial.providers.AuthProvider;
import com.example.a1tutorial.providers.CartaComidaDatabaseProvider;
import com.example.a1tutorial.providers.ComandaDatabasePorvider;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_comanda extends Fragment implements View.OnClickListener{

    View mView;

    CartaComidaDatabaseProvider cartaDatabase;
    ComandaDatabasePorvider comandaDatabase;

    RecyclerView listadoCartaComida;
    Button btnCarne, btnPescado, btnCocidos, btnEntrantes, btnPostre, btnAñadir;
    TextView txtNumeroMesa;
    private AdaptadorComandaComida adaptadorComandaComida;

    AuthProvider auth;


    List<Carta> listaPlatos= null;
    public Fragment_comanda(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_camarero_comanda, container, false);

        cartaDatabase = new CartaComidaDatabaseProvider();
        comandaDatabase = new ComandaDatabasePorvider();
        auth = new AuthProvider();

        listadoCartaComida = mView.findViewById(R.id.listado_carta_comanda);
        listadoCartaComida.setLayoutManager(new LinearLayoutManager(getContext()));

        txtNumeroMesa = mView.findViewById(R.id.txt_comanda_mesas);

        btnCarne = mView.findViewById(R.id.btn_comanda_carne);
        btnCarne.setOnClickListener(this);
        btnPescado = mView.findViewById(R.id.btn_comanda_pescados);
        btnPescado.setOnClickListener(this);
        btnCocidos = mView.findViewById(R.id.btn_comanda_cocidos);
        btnCocidos.setOnClickListener(this);
        btnEntrantes = mView.findViewById(R.id.btn_comanda_entrante);
        btnEntrantes.setOnClickListener(this);
        btnPostre = mView.findViewById(R.id.btn_comanda_postre);
        btnPostre.setOnClickListener(this);
        btnAñadir = mView.findViewById(R.id.btn_comanda_crear);
        btnAñadir.setOnClickListener(this);

        cargarCardView(cartaDatabase.getAll());
        return mView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnCarne.getId()){
            cargarCardView(cartaDatabase.getCarne());
        }
        if (v.getId() == btnCocidos.getId()){
            cargarCardView(cartaDatabase.getCocidos());
        }
        if (v.getId() == btnEntrantes.getId()){
            cargarCardView(cartaDatabase.getEntrantes());
        }
        if (v.getId() == btnPescado.getId()){
            cargarCardView(cartaDatabase.getPescado());
        }
        if (v.getId() == btnPostre.getId()) {
            cargarCardView(cartaDatabase.getPostre());
        }
        if (v.getId() == btnAñadir.getId()) {
            System.out.println("entraa");
            double precioTotal = 0;
            listaPlatos = new ArrayList<>();
            for (Carta i : adaptadorComandaComida.getMyFoodSelected()) {
                if (i.getUnidades()!=0){
                    Carta plato = new Carta(i.getIdComida(), i.getNombre(), i.getPrecio(), i.getUnidades());
                    listaPlatos.add(plato);
                    precioTotal = precioTotal + plato.getUnidades()*plato.getPrecio();
                }
            }
            añadirComanda(listaPlatos, precioTotal);

        }
    }

    private void añadirComanda(final List<Carta> listaPlatos, double precioTotal) {
        String idCamarero = auth.idAuth();
        int mesa = Integer.parseInt(txtNumeroMesa.getText().toString());
        Comanda comandaCrear = new Comanda(idCamarero,mesa,listaPlatos,precioTotal,false);
        Map<String, Object> comanda = new HashMap<>();
        comanda.put("idCamarero", comandaCrear.getIdCamarero());
        comanda.put("mesa", comandaCrear.getMesa());
        comanda.put("Platos", comandaCrear.getPlatos());
        comanda.put("precio", comandaCrear.getPrecioTotal());
        comanda.put("servido", comandaCrear.isServido());

        comandaDatabase.getmColection().document().set(comanda).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                for (Carta carta: listaPlatos){

                }
            }
        });
    }

    private void cargarCardView(Query query) {
        FirestoreRecyclerOptions<Carta>options = new FirestoreRecyclerOptions.Builder<Carta>().setQuery(query, Carta.class).build();
        adaptadorComandaComida = new AdaptadorComandaComida(options);
        listadoCartaComida.setAdapter(adaptadorComandaComida);
        adaptadorComandaComida.startListening();
    }
}
