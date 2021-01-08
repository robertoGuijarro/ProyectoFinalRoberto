package com.example.a1tutorial.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.models.Carta;
import com.example.a1tutorial.models.Comanda;
import com.example.a1tutorial.providers.ComandaDatabasePorvider;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class AdaptadorCocineroComandas extends FirestoreRecyclerAdapter<Comanda, AdaptadorCocineroComandas.ViewCocineroComandas> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    ComandaDatabasePorvider comandaDatabase;
    public AdaptadorCocineroComandas(@NonNull FirestoreRecyclerOptions<Comanda> options) {
        super(options);

        comandaDatabase = new ComandaDatabasePorvider();
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewCocineroComandas holder, int position, @NonNull Comanda model) {
        String idDocument = getSnapshots().getSnapshot(position).getId();

        holder.txtNombreCamarero.setText(model.getNameCamarero());
        holder.txtNumeroMesa.setText(model.getMesa()+"");

        holder.listadoPlatos.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));

        cargarCardView(comandaDatabase.getComandasPlatos(idDocument), holder.listadoPlatos);

    }

    private void cargarCardView(Query comandasPlatos, RecyclerView listadoPlatos) {
        AdaptadorCocineroComandasPlatos adaptadorCocineroComandasPlatos;

        FirestoreRecyclerOptions<Carta> options = new FirestoreRecyclerOptions.Builder<Carta>().setQuery(comandasPlatos, Carta.class).build();
        adaptadorCocineroComandasPlatos = new AdaptadorCocineroComandasPlatos(options);

        listadoPlatos.setAdapter(adaptadorCocineroComandasPlatos);
        adaptadorCocineroComandasPlatos.startListening();
    }

    @NonNull
    @Override
    public ViewCocineroComandas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cocinero_comandas, parent,false);
        return new ViewCocineroComandas(vista);
    }

    public class ViewCocineroComandas extends RecyclerView.ViewHolder {
        TextView txtNombreCamarero, txtNumeroMesa;
        CoordinatorLayout vistaPlatos;

        CardView cardView;

        RecyclerView listadoPlatos;
        public ViewCocineroComandas(@NonNull View vista) {
            super(vista);
            txtNombreCamarero = vista.findViewById(R.id.txt_item_cocinero_comandas_nombreCamarero);
            txtNumeroMesa = vista.findViewById(R.id.txt_item_cocinero_comandas_mesa);

            vistaPlatos = vista.findViewById(R.id.coordinator_item_cocinero_comandas);

            cardView = vista.findViewById(R.id.cardView_item_cocinero_comandas);

            listadoPlatos = vista.findViewById(R.id.listadoCocineroComandasDesplegable);
        }
    }
}
