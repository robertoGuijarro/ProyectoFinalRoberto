
package com.example.a1tutorial.activity.camarero.fragments_camarero;

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
import com.example.a1tutorial.adaptador.AdaptadorCamareroComandaBebidas;
import com.example.a1tutorial.adaptador.AdaptadorComandaComida;
import com.example.a1tutorial.models.Bebidas;
import com.example.a1tutorial.models.Carta;
import com.example.a1tutorial.models.Comanda;
import com.example.a1tutorial.providers.AuthProvider;
import com.example.a1tutorial.providers.CartaBebidasDatabaseProvider;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_comanda extends Fragment implements View.OnClickListener, TextWatcher {

    View mView;

    CartaComidaDatabaseProvider cartaDatabase;
    ComandaDatabasePorvider comandaDatabase;
    UserDatabaseProvider userDatabase;
    CartaBebidasDatabaseProvider cartaBebidasDatabaseProvider;

    RecyclerView listadoCartaComida;
    Button btnAñadir, btnComidas, btnBebidas;
    TextView txtNumeroMesa;

    private AdaptadorComandaComida adaptadorComandaComida;
    private AdaptadorCamareroComandaBebidas adaptadorCamareroComandaBebidas;

    AuthProvider auth;

    boolean mesaUsandose;
    String documentoMesaUsandose;

    List<Carta> listaPlatos= null;
    List<Bebidas> listaBebidas = null;
    String nameCamarero = "";

    boolean cargadoComida=true;
    public Fragment_comanda(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_camarero_comanda, container, false);

        cartaDatabase = new CartaComidaDatabaseProvider();
        comandaDatabase = new ComandaDatabasePorvider();
        userDatabase = new UserDatabaseProvider();
        auth = new AuthProvider();
        cartaBebidasDatabaseProvider = new CartaBebidasDatabaseProvider();

        listadoCartaComida = mView.findViewById(R.id.listado_carta_comanda);
        listadoCartaComida.setLayoutManager(new LinearLayoutManager(getContext()));

        txtNumeroMesa = mView.findViewById(R.id.txt_comanda_mesas);
        txtNumeroMesa.addTextChangedListener(this);

        btnAñadir = mView.findViewById(R.id.btn_comanda_crear);
        btnAñadir.setOnClickListener(this);

        btnBebidas = mView.findViewById(R.id.btn_camarero_comandas_bebidas);
        btnBebidas.setOnClickListener(this);

        btnComidas = mView.findViewById(R.id.btn_camarero_comanda_comidas);
        btnComidas.setOnClickListener(this);
        cargarCardView(cartaDatabase.getAll());
        sacarNameCamarero();

        mesaUsandose = true;
        documentoMesaUsandose = "";
        return mView;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == btnComidas.getId()){
            cargadoComida= true;
            cargarCardView(cartaDatabase.getAll());
        }

        if (v.getId()== btnBebidas.getId()){
            cargadoComida = false;
            cargarCardViewBebidas(cartaBebidasDatabaseProvider.getAll());
        }
        if (v.getId() == btnAñadir.getId()) {
            //if para añador bebida o comida
            if (cargadoComida){
                double precioTotal = 0;
                listaPlatos = new ArrayList<>();
                for (Carta i : adaptadorComandaComida.getMyFoodSelected()) {
                    if (i.getUnidades()!=0){
                        Carta plato = new Carta(i.getIdComida(), i.getNombre(), i.getPrecio(), i.getUnidades(), i.getStock(),i.getEstado(), i.getTipo());
                        listaPlatos.add(plato);
                        precioTotal = precioTotal + plato.getUnidades()*plato.getPrecio();
                    }
                }
                DecimalFormat format = new DecimalFormat("#.00");
                format.format(precioTotal);
                añadirComanda(listaPlatos, precioTotal, documentoMesaUsandose);
            }else{
                double precioTotal=0;
                listaBebidas = new ArrayList<>();
                for (Bebidas i: adaptadorCamareroComandaBebidas.getMydrinksSelected()){
                    if(i.getUnidades()!=0){
                        Bebidas bebida = new Bebidas(i.getIdBebidas(), i.getNombre(), i.getPrecio(), i.getUrl(), i.getStock(),i.getUnidades(), i.getEstado(), i.isAlcoholicas());
                        listaBebidas.add(bebida);
                        precioTotal = precioTotal + bebida.getUnidades()*bebida.getPrecio();
                    }
                }

                DecimalFormat format = new DecimalFormat("#.00");
                format.format(precioTotal);

                añadirComandaBebidas(listaBebidas, precioTotal, documentoMesaUsandose);
            }
        }
    }

    private void añadirComandaBebidas(List<Bebidas> listaBebidas, final double precioTotal, final String documentoMesaUsandoseAux) {

        if (!mesaUsandose){
            int mesa = Integer.parseInt(txtNumeroMesa.getText().toString());

            actualizarStockBebidas(listaBebidas);

            String idCamarero = auth.idAuth();

            Comanda comandaCrear = new Comanda(idCamarero, nameCamarero, precioTotal, mesa, listaBebidas, false);

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

            for(Bebidas bebida: listaBebidas){
                comandaDatabase.getmColection().document(documento.getId()).collection("bebidas").document().set(bebida).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }

            mesaUsandose = true;

        }else{
            Toast.makeText(getContext(), "La mesa ya esta en uso", Toast.LENGTH_LONG).show();

            actualizarStockBebidas(listaBebidas);

            comandaDatabase.getmColection().document(documentoMesaUsandoseAux).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    double precioActual = documentSnapshot.getDouble("precio");

                    double precioActualizado = precioActual+precioTotal;

                    DecimalFormat format = new DecimalFormat("#.00");
                    format.format(precioActualizado);

                    comandaDatabase.getmColection().document(documentoMesaUsandoseAux).update("precio", precioActualizado).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e);
                }
            });

            for (Bebidas bebida: listaBebidas){
                comandaDatabase.getmColection().document(documentoMesaUsandose).collection("bebidas").document().set(bebida).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("fallo platos");
                    }
                });
            }
        }
    }

    private void actualizarStockBebidas(List<Bebidas> listaBebidass) {
        for (final Bebidas bebida: listaBebidass){
            int stockActual = bebida.getStock()-bebida.getUnidades();
            cartaBebidasDatabaseProvider.getmColection().document(bebida.getIdBebidas()).update("stock", stockActual).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });
            refrescarFragment();
        }
    }


    private void añadirComanda(final List<Carta> listaPlatos, final double precioTotal, final String documentoMesaUsandoseAux) {

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

            comandaDatabase.getmColection().document(documentoMesaUsandose).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    double precioActual = documentSnapshot.getDouble("precio");

                    double precioActualizado = precioActual+precioTotal;

                    DecimalFormat format = new DecimalFormat("#.00");
                    format.format(precioActualizado);

                    comandaDatabase.getmColection().document(documentoMesaUsandoseAux).update("precio", precioActualizado).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e);
                }
            });

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

    private void cargarCardViewBebidas(Query query) {
        FirestoreRecyclerOptions<Bebidas> options = new FirestoreRecyclerOptions.Builder<Bebidas>().setQuery(query, Bebidas.class).build();
        adaptadorCamareroComandaBebidas = new AdaptadorCamareroComandaBebidas(options);
        listadoCartaComida.setAdapter(adaptadorCamareroComandaBebidas);
        adaptadorCamareroComandaBebidas.startListening();
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
