package com.example.a1tutorial.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.models.Bebidas;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdaptadorCartaBebidas extends FirestoreRecyclerAdapter<Bebidas, AdaptadorCartaBebidas.ViewCartaBebidas> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdaptadorCartaBebidas(@NonNull FirestoreRecyclerOptions<Bebidas> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdaptadorCartaBebidas.ViewCartaBebidas holder, int position, @NonNull Bebidas model) {
        holder.txtNombre.setText(model.getNombre());
        holder.txtPrecio.setText("Precio/uni"+ model.getPrecio()+"€");
        if (model.isAlcoholicas()){
            holder.txtAlcoholica.setText("Bebida Alcoholica");
        }
        holder.txtStock.setText("Stock:"+ model.getStock());
    }

    @NonNull
    @Override
    public AdaptadorCartaBebidas.ViewCartaBebidas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carta_bebidas, parent, false);
        return new ViewCartaBebidas(vista);
    }

    public class ViewCartaBebidas extends RecyclerView.ViewHolder {
        TextView txtNombre, txtPrecio, txtAlcoholica, txtStock;

        public ViewCartaBebidas(@NonNull View vista) {
            super(vista);

            txtNombre = vista.findViewById(R.id.item_carta_bebidas_nombre);
            txtPrecio = vista.findViewById(R.id.item_carta_bebidas_precio);

            txtAlcoholica = vista.findViewById(R.id.item_carta_bebidas_alcoholica);
            txtStock = vista.findViewById(R.id.item_carta_bebidas_stock);
        }
    }
}
