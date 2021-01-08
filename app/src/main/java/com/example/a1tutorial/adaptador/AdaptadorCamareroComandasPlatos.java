package com.example.a1tutorial.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.models.Carta;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdaptadorCamareroComandasPlatos extends FirestoreRecyclerAdapter<Carta, AdaptadorCamareroComandasPlatos.ViewComandaPlatos> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdaptadorCamareroComandasPlatos(@NonNull FirestoreRecyclerOptions<Carta> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdaptadorCamareroComandasPlatos.ViewComandaPlatos holder, int position, @NonNull Carta model) {
        System.out.println(model);
        holder.txtNombrePlato.setText(model.getNombre());
        holder.txtCantidadPlato.setText("Unidades"+model.getUnidades());
    }

    @NonNull
    @Override
    public AdaptadorCamareroComandasPlatos.ViewComandaPlatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camarero_comandas_platos, parent, false);
        return new ViewComandaPlatos(vista);
    }

    public class ViewComandaPlatos extends RecyclerView.ViewHolder {
        TextView txtNombrePlato, txtCantidadPlato;
        Button btnServido;
        public ViewComandaPlatos(@NonNull View vista) {
            super(vista);
            txtNombrePlato = vista.findViewById(R.id.txt_item_camarero_comandas_nombrePlato);
            txtCantidadPlato = vista.findViewById(R.id.txt_item_camarero_comandas_cantidad);

            btnServido = vista.findViewById(R.id.btn_item_comandas_platos);
        }
    }
}
