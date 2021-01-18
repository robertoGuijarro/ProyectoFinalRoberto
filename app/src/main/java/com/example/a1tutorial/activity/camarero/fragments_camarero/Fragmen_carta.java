package com.example.a1tutorial.activity.camarero.fragments_camarero;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.adaptador.AdaptadorCartaBebidas;
import com.example.a1tutorial.adaptador.AdaptadorCartaComida;
import com.example.a1tutorial.models.Bebidas;
import com.example.a1tutorial.models.Carta;
import com.example.a1tutorial.providers.CartaBebidasDatabaseProvider;
import com.example.a1tutorial.providers.CartaComidaDatabaseProvider;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragmen_carta extends Fragment implements View.OnClickListener {

    RecyclerView listadoCartaComidaCarme, listadoCartaComidaPescado, listadoCartaComidaEntrantes, listadoCartaComidaCocidos, listadoCartaComidaPostre, listadoCartaComidaBebidas;
    CartaComidaDatabaseProvider cartaDatabase;
    CartaBebidasDatabaseProvider cartaBebidasDatabaseProvider;
    View mView;
    Button btnCarne, btnPescado, btnEntrante, btnCocidos, btnPostre, btnBebidas;

    CoordinatorLayout coordinatorLayout, coordinatorLayoutCartaCarne, coordinatorLayoutCartaPescado, coordinatorLayoutCartaEntrantes, coordinatorLayoutCartaCocidos, coordinatorLayoutCartaPostre, coordinatorLayoutCartaBebidas;
    public Fragmen_carta (){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_camarero_carta, container, false);

        listadoCartaComidaCarme = mView.findViewById(R.id.listadoCamareroCartaDesplegableCarne);
        listadoCartaComidaCarme.setLayoutManager(new LinearLayoutManager(getContext()));

        listadoCartaComidaPescado = mView.findViewById(R.id.listadoCamareroCartaDesplegablePescado);
        listadoCartaComidaPescado.setLayoutManager(new LinearLayoutManager(getContext()));

        listadoCartaComidaEntrantes = mView.findViewById(R.id.listadoCamareroCartaDesplegable_entrantes);
        listadoCartaComidaEntrantes.setLayoutManager(new LinearLayoutManager(getContext()));

        listadoCartaComidaCocidos = mView.findViewById(R.id.listadoCamareroCartaDesplegableCocidos);
        listadoCartaComidaCocidos.setLayoutManager(new LinearLayoutManager(getContext()));

        listadoCartaComidaPostre = mView.findViewById(R.id.listadoCamareroCartaDesplegablePostre);
        listadoCartaComidaPostre.setLayoutManager(new LinearLayoutManager(getContext()));

        listadoCartaComidaCocidos = mView.findViewById(R.id.listadoCamareroCartaDesplegableCocidos);
        listadoCartaComidaCocidos.setLayoutManager(new LinearLayoutManager(getContext()));

        listadoCartaComidaBebidas = mView.findViewById(R.id.listadoCamareroCartaDesplegableBebidas);
        listadoCartaComidaBebidas.setLayoutManager(new LinearLayoutManager(getContext()));

        btnCarne = mView.findViewById(R.id.btn_camarero_carta_Carne);
        btnCarne.setOnClickListener(this);

        btnCocidos = mView.findViewById(R.id.btn_camarero_carta_cocidos);
        btnCocidos.setOnClickListener(this);

        btnEntrante = mView.findViewById(R.id.btn_camarero_carta_entrantes);
        btnEntrante.setOnClickListener(this);

        btnPescado = mView.findViewById(R.id.btn_camarero_carta_pescado);
        btnPescado.setOnClickListener(this);

        btnPostre = mView.findViewById(R.id.btn_camarero_carta_postre);
        btnPostre.setOnClickListener(this);

        btnBebidas = mView.findViewById(R.id.btn_camarero_carta_bebidas);
        btnBebidas.setOnClickListener(this);

        coordinatorLayout = mView.findViewById(R.id.coordinatorLayoutCarne);
        coordinatorLayoutCartaCarne = mView.findViewById(R.id.coordinator_camarero_carta_carne);

        coordinatorLayoutCartaCocidos = mView.findViewById(R.id.coordinator_camarero_carta_cocidos);

        coordinatorLayoutCartaEntrantes = mView.findViewById(R.id.coordinator_camarero_carta_entrantes);

        coordinatorLayoutCartaPescado = mView.findViewById(R.id.coordinator_camarero_carta_pescado);

        coordinatorLayoutCartaPostre = mView.findViewById(R.id.coordinator_camarero_carta_postre);

        coordinatorLayoutCartaBebidas = mView.findViewById(R.id.coordinator_camarero_carta_bebidas);

        cartaDatabase = new CartaComidaDatabaseProvider();
        cartaBebidasDatabaseProvider = new CartaBebidasDatabaseProvider();


        return mView;
    }


    public void cargarCardView(Query query, RecyclerView listado){
        FirestoreRecyclerOptions<Carta> options = new FirestoreRecyclerOptions.Builder<Carta>().setQuery(query, Carta.class).build();
        AdaptadorCartaComida adaptador = new AdaptadorCartaComida(options);

        listado.setAdapter(adaptador);
        adaptador.startListening();
    }

    public void cargarCardViewBebidas (Query query, RecyclerView listado){
        FirestoreRecyclerOptions<Bebidas> options = new FirestoreRecyclerOptions.Builder<Bebidas>().setQuery(query, Bebidas.class).build();
        AdaptadorCartaBebidas adaptadorCartaBebidas = new AdaptadorCartaBebidas(options);

        listado.setAdapter(adaptadorCartaBebidas);
        adaptadorCartaBebidas.startListening();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==btnCarne.getId()){
            if (coordinatorLayoutCartaCarne.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(coordinatorLayout, new AutoTransition());
                coordinatorLayoutCartaCarne.setVisibility(View.VISIBLE);
                btnCarne.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                cargarCardView(cartaDatabase.getCarne(), listadoCartaComidaCarme);
            }else{
                TransitionManager.beginDelayedTransition(coordinatorLayout, new AutoTransition());
                coordinatorLayoutCartaCarne.setVisibility(View.GONE);
                btnCarne.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            }
        }

        if (v.getId()==btnCocidos.getId()){
            if (coordinatorLayoutCartaCocidos.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(coordinatorLayout, new AutoTransition());
                coordinatorLayoutCartaCocidos .setVisibility(View.VISIBLE);
                btnCocidos.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                cargarCardView(cartaDatabase.getCocidos(), listadoCartaComidaCocidos);
            }else{
                TransitionManager.beginDelayedTransition(coordinatorLayout, new AutoTransition());
                coordinatorLayoutCartaCocidos.setVisibility(View.GONE);
                btnCocidos.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            }
        }

        if (v.getId()==btnEntrante.getId()){
            if (coordinatorLayoutCartaEntrantes.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(coordinatorLayout, new AutoTransition());
                coordinatorLayoutCartaEntrantes.setVisibility(View.VISIBLE);
                btnEntrante.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                cargarCardView(cartaDatabase.getEntrantes(), listadoCartaComidaEntrantes);
            }else{
                TransitionManager.beginDelayedTransition(coordinatorLayout, new AutoTransition());
                coordinatorLayoutCartaEntrantes.setVisibility(View.GONE);
                btnEntrante.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            }
        }

        if (v.getId()==btnPescado.getId()){
            if (coordinatorLayoutCartaPescado.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(coordinatorLayout, new AutoTransition());
                coordinatorLayoutCartaPescado.setVisibility(View.VISIBLE);
                btnPescado.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                cargarCardView(cartaDatabase.getPescado(), listadoCartaComidaPescado);
            }else{
                TransitionManager.beginDelayedTransition(coordinatorLayout, new AutoTransition());
                coordinatorLayoutCartaPescado.setVisibility(View.GONE);
                btnPescado.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            }
        }

        if (v.getId()==btnPostre.getId()){
            if (coordinatorLayoutCartaPostre.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(coordinatorLayout, new AutoTransition());
                coordinatorLayoutCartaPostre.setVisibility(View.VISIBLE);
                btnPostre.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                cargarCardView(cartaDatabase.getPostre(), listadoCartaComidaPostre);
            }else{
                TransitionManager.beginDelayedTransition(coordinatorLayout, new AutoTransition());
                coordinatorLayoutCartaPostre.setVisibility(View.GONE);
                btnPostre.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            }
        }

        if (v.getId()==btnBebidas.getId()){
            if (coordinatorLayoutCartaBebidas.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(coordinatorLayout, new AutoTransition());
                coordinatorLayoutCartaBebidas.setVisibility(View.VISIBLE);
                btnBebidas.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                cargarCardViewBebidas(cartaBebidasDatabaseProvider.getAll(), listadoCartaComidaBebidas);
            }else{
                TransitionManager.beginDelayedTransition(coordinatorLayout, new AutoTransition());
                coordinatorLayoutCartaBebidas.setVisibility(View.GONE);
                btnBebidas.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            }
        }
    }
}
