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

public class AdaptadorCartaComida extends FirestoreRecyclerAdapter<Carta, AdaptadorCartaComida.ViewHolderSuscesos> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public AdaptadorCartaComida(@NonNull FirestoreRecyclerOptions<Carta> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderSuscesos holder, int position, @NonNull Carta model) {
        holder.txtNombre.setText(model.getNombre());
        holder.txtPrecio.setText(""+model.getPrecio());
        holder.txtTipo.setText(model.getTipo());
    }

    @NonNull
    @Override
    public ViewHolderSuscesos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carta, parent, false);
        return new ViewHolderSuscesos(vista);
    }


    public class ViewHolderSuscesos extends RecyclerView.ViewHolder {
        TextView txtNombre, txtPrecio, txtTipo;
        public ViewHolderSuscesos(@NonNull View itemView) {
            super(itemView);
            txtNombre= itemView.findViewById(R.id.item_carta_nombre);
            txtPrecio = itemView.findViewById(R.id.item_carta_precio);
            txtTipo = itemView.findViewById(R.id.item_carta_tipo);
        }
    }
}
