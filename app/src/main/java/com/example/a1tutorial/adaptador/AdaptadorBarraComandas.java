package com.example.a1tutorial.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.models.Bebidas;
import com.example.a1tutorial.models.Comanda;
import com.example.a1tutorial.providers.ComandaDatabasePorvider;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class AdaptadorBarraComandas extends FirestoreRecyclerAdapter<Comanda, AdaptadorBarraComandas.ViewComandaBebida> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    ComandaDatabasePorvider comandaDatabasePorvider;

    public AdaptadorBarraComandas(@NonNull FirestoreRecyclerOptions<Comanda> options) {
        super(options);
        comandaDatabasePorvider = new ComandaDatabasePorvider();
    }

    @Override
    protected void onBindViewHolder(@NonNull AdaptadorBarraComandas.ViewComandaBebida holder, int position, @NonNull Comanda model) {
        final String idDocument =getSnapshots().getSnapshot(position).getId();

        holder.txtNombreCamarero.setText(model.getNameCamarero());
        holder.txtNumeroMesa.setText("Nº mesa:"+model.getMesa());

        holder.listadoComanda.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));

        cargarCardView(comandaDatabasePorvider.getComandaBebidasBarra(idDocument), holder.listadoComanda, idDocument);
    }

    private void cargarCardView(Query comandaPlatosCocinero, RecyclerView listadoComanda, String idDocument) {
        AdaptadorBarraComandasBebidas adaptadorBarraComandasBebidas;

        FirestoreRecyclerOptions<Bebidas> options = new FirestoreRecyclerOptions.Builder<Bebidas>().setQuery(comandaPlatosCocinero, Bebidas.class).build();

        adaptadorBarraComandasBebidas = new AdaptadorBarraComandasBebidas(options, idDocument);
        listadoComanda.setAdapter(adaptadorBarraComandasBebidas);
        adaptadorBarraComandasBebidas.startListening();
    }

    @NonNull
    @Override
    public AdaptadorBarraComandas.ViewComandaBebida onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barra_comandas, parent,false);
        return new ViewComandaBebida(vista);
    }

    public class ViewComandaBebida extends RecyclerView.ViewHolder {
        TextView txtNumeroMesa, txtNombreCamarero;

        RecyclerView listadoComanda;
        public ViewComandaBebida(@NonNull View vista) {
            super(vista);

            txtNumeroMesa = vista.findViewById(R.id.txt_item_barra_numeroMesa);
            txtNombreCamarero = vista.findViewById(R.id.txt_item_barra_nombreCamarero);

            listadoComanda = vista.findViewById(R.id.item_listadobarraComandas);
        }
    }
}
