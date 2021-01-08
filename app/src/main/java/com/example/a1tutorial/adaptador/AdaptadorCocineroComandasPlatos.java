package com.example.a1tutorial.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.models.Carta;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdaptadorCocineroComandasPlatos extends FirestoreRecyclerAdapter<Carta, AdaptadorCocineroComandasPlatos.ViewComandaPlatos> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdaptadorCocineroComandasPlatos(@NonNull FirestoreRecyclerOptions<Carta> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewComandaPlatos holder, int position, @NonNull Carta model) {
        holder.txtCantidad.setText(model.getUnidades()+"");
        holder.txtNombrePlatos.setText(model.getNombre());
    }

    @NonNull
    @Override
    public ViewComandaPlatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camarero_comandas_platos, parent, false);
        return new ViewComandaPlatos(vista);
    }

    public class ViewComandaPlatos extends RecyclerView.ViewHolder {
        TextView txtNombrePlatos, txtCantidad;
        public ViewComandaPlatos(@NonNull View vista) {
            super(vista);

            txtNombrePlatos = vista.findViewById(R.id.txt_item_cocinero_comandas_nombrePlato);
            txtCantidad = vista.findViewById(R.id.txt_item_cocinero_comandas_cantidad);
        }
    }
}
