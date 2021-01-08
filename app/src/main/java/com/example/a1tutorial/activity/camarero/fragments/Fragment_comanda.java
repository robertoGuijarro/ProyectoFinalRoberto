
package com.example.a1tutorial.activity.camarero.fragments;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.adaptador.AdaptadorComandaComida;
import com.example.a1tutorial.models.Carta;
import com.example.a1tutorial.models.Comanda;
import com.example.a1tutorial.providers.AuthProvider;
import com.example.a1tutorial.providers.CartaComidaDatabaseProvider;
import com.example.a1tutorial.providers.ComandaDatabasePorvider;
import com.example.a1tutorial.providers.UserDatabaseProvider;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_comanda extends Fragment implements View.OnClickListener, TextWatcher {

    View mView;

    CartaComidaDatabaseProvider cartaDatabase;
    ComandaDatabasePorvider comandaDatabase;
    UserDatabaseProvider userDatabase;

    RecyclerView listadoCartaComida;
    Button btnCarne, btnPescado, btnCocidos, btnEntrantes, btnPostre, btnAñadir;
    TextView txtNumeroMesa;
    private AdaptadorComandaComida adaptadorComandaComida;

    AuthProvider auth;

    boolean mesaUsandose;
    String documentoMesaUsandose;

    List<Carta> listaPlatos= null;
    String nameCamarero = "";
    public Fragment_comanda(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_camarero_comanda, container, false);

        cartaDatabase = new CartaComidaDatabaseProvider();
        comandaDatabase = new ComandaDatabasePorvider();
        userDatabase = new UserDatabaseProvider();
        auth = new AuthProvider();

        listadoCartaComida = mView.findViewById(R.id.listado_carta_comanda);
        listadoCartaComida.setLayoutManager(new LinearLayoutManager(getContext()));

        txtNumeroMesa = mView.findViewById(R.id.txt_comanda_mesas);
        txtNumeroMesa.addTextChangedListener(this);

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
        sacarNameCamarero();

        mesaUsandose = true;
        documentoMesaUsandose = "";
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
                    Carta plato = new Carta(i.getIdComida(), i.getNombre(), i.getPrecio(), i.getUnidades(), i.getStock(),i.getEstado(), i.getTipo());
                    listaPlatos.add(plato);
                    precioTotal = precioTotal + plato.getUnidades()*plato.getPrecio();
                }
            }
            añadirComanda(listaPlatos, precioTotal);
        }
    }


    private void añadirComanda(final List<Carta> listaPlatos, double precioTotal) {

        if (!mesaUsandose){
            int mesa = Integer.parseInt(txtNumeroMesa.getText().toString());

            actualizarStock(listaPlatos);

            String idCamarero = auth.idAuth();

            Comanda comandaCrear = new Comanda(idCamarero, nameCamarero, mesa,listaPlatos,precioTotal,false);
            Map<String, Object> comanda = new HashMap<>();
            comanda.put("idCamarero", comandaCrear.getIdCamarero());
            comanda.put("nameCamarero", nameCamarero);
            comanda.put("mesa", comandaCrear.getMesa());
            comanda.put("precio", comandaCrear.getPrecioTotal());
            comanda.put("servido", comandaCrear.isServido());

            DocumentReference documento = comandaDatabase.getmColection().document();
            documento.set(comanda).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            });

            for (Carta platos: listaPlatos){
                comandaDatabase.getmColection().document(documento.getId()).collection("platos").document().set(platos).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("fallo platos");
                    }
                });
            }

            mesaUsandose = true;
        }else{
            Toast.makeText(getContext(), "La mesa ya esta en uso", Toast.LENGTH_LONG).show();

            actualizarStock(listaPlatos);

            for (Carta platos: listaPlatos){
                comandaDatabase.getmColection().document(documentoMesaUsandose).collection("platos").document().set(platos).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("fallo platos");
                    }
                });
            }
        }
    }

    private void actualizarStock(List<Carta> listaPlatos) {
        for(final Carta i:listaPlatos){
            int stockActualizada = i.getStock()-i.getUnidades();
            cartaDatabase.getmColection().document(i.getIdComida()).update("stock", stockActualizada).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    System.out.println("Stock actualizada");
                }
            });
        }
        refrescarFragment();
    }


    private void refrescarFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    private void cargarCardView(Query query) {
        FirestoreRecyclerOptions<Carta>options = new FirestoreRecyclerOptions.Builder<Carta>().setQuery(query, Carta.class).build();
        adaptadorComandaComida = new AdaptadorComandaComida(options);
        listadoCartaComida.setAdapter(adaptadorComandaComida);
        adaptadorComandaComida.startListening();
    }

    //Antes de cambiar texto
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    //Mientras se cambia
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    //Al finalizar el cambio de texto
    @Override
    public void afterTextChanged(final Editable editable) {
        mesaUsandose = false;
        if (!editable.toString().equals("")){
            comandaDatabase.getAllDocument().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot document:queryDocumentSnapshots){
                        if (Integer.parseInt(editable.toString())==document.getLong("mesa")){
                            mesaUsandose = true;
                            Toast.makeText(getContext(), "Mesa usabdose", Toast.LENGTH_LONG).show();
                            documentoMesaUsandose = document.getId();
                        }
                    }
                }
            });
        }else{
            Toast.makeText(getContext(), "No puede ser nulo", Toast.LENGTH_LONG).show();
            mesaUsandose = true;
        }
    }
    private void sacarNameCamarero() {
        userDatabase.getNameCamarero(auth.idAuth()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                nameCamarero = documentSnapshot.getString("nombre");
            }
        });
    }
}
