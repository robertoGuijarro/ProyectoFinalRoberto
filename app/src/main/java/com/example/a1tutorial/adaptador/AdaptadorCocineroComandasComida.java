package com.example.a1tutorial.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.models.Carta;
import com.example.a1tutorial.models.Comanda;
import com.example.a1tutorial.providers.ComandaDatabasePorvider;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class AdaptadorCocineroComandasComida extends FirestoreRecyclerAdapter<Comanda, AdaptadorCocineroComandasComida.ViewComandaComida> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    ComandaDatabasePorvider comandaDatabasePorvider;
    public AdaptadorCocineroComandasComida(@NonNull FirestoreRecyclerOptions<Comanda> options) {
        super(options);
        comandaDatabasePorvider = new ComandaDatabasePorvider();
    }

    @Override
    protected void onBindViewHolder(@NonNull AdaptadorCocineroComandasComida.ViewComandaComida holder, int position, @NonNull Comanda model) {
        final String idDocument =getSnapshots().getSnapshot(position).getId();

        holder.txtNombreCamarero.setText(model.getNameCamarero());
        holder.txtNumeroMesa.setText(model.getMesa()+"");

        holder.listadoComanda.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));

        cargarCardView(comandaDatabasePorvider.getComandaPlatosCocinero(idDocument), holder.listadoComanda, idDocument);
    }

    private void cargarCardView(Query comandaPlatosCocinero, RecyclerView listadoComanda, String idDocument) {
        AdaptadorCocineroComandasPlatos adaptadorCocineroComandasPlatos;

        FirestoreRecyclerOptions<Carta> options = new FirestoreRecyclerOptions.Builder<Carta>().setQuery(comandaPlatosCocinero, Carta.class).build();

        adaptadorCocineroComandasPlatos = new AdaptadorCocineroComandasPlatos(options, idDocument);
        listadoComanda.setAdapter(adaptadorCocineroComandasPlatos);
        adaptadorCocineroComandasPlatos.startListening();
    }

    @NonNull
    @Override
    public AdaptadorCocineroComandasComida.ViewComandaComida onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cocinero_comandas, parent,false);
        return new ViewComandaComida(vista);
    }

    public class ViewComandaComida extends RecyclerView.ViewHolder {
        TextView txtNumeroMesa, txtNombreCamarero;

        RecyclerView listadoComanda;
        public ViewComandaComida(@NonNull View vista) {
            super(vista);
            txtNumeroMesa = vista.findViewById(R.id.txt_item_cocinero_numeroMesa);
            txtNombreCamarero = vista.findViewById(R.id.txt_item_cocinero_nombreCamarero);

            listadoComanda = vista.findViewById(R.id.item_listadoCocineroComandas);
        }
    }
}
