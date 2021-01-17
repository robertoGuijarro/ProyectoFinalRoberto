package com.example.a1tutorial.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.models.Bebidas;
import com.example.a1tutorial.providers.ComandaDatabasePorvider;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class AdaptadorBarraComandasBebidas extends FirestoreRecyclerAdapter<Bebidas, AdaptadorBarraComandasBebidas.ViewComandaBebida> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    ComandaDatabasePorvider comandaDatabasePorvider;
    String documentoComanda;
    public AdaptadorBarraComandasBebidas(@NonNull FirestoreRecyclerOptions<Bebidas> options, String idDocumento) {
        super(options);

        comandaDatabasePorvider = new ComandaDatabasePorvider();

        documentoComanda = idDocumento;
    }

    @Override
    protected void onBindViewHolder(@NonNull AdaptadorBarraComandasBebidas.ViewComandaBebida holder, int position, @NonNull Bebidas model) {
        holder.txtNombrePlato.setText(model.getNombre());
        holder.txtCantidad.setText(model.getUnidades()+"");

        final String idDocument =getSnapshots().getSnapshot(position).getId();

        holder.btnEntregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comandaDatabasePorvider.updateBebidaBarra(documentoComanda, idDocument).update("estado", "naranja").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("BebidaActualizada");
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public AdaptadorBarraComandasBebidas.ViewComandaBebida onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barra_comandas_bebidas, parent, false);
        return new ViewComandaBebida(vista);
    }

    public class ViewComandaBebida extends RecyclerView.ViewHolder {
        TextView txtNombrePlato, txtCantidad;
        Button btnEntregar;
        public ViewComandaBebida(@NonNull View vista) {
            super(vista);

            txtNombrePlato = vista.findViewById(R.id.txt_item_barra_comandas_nombreBebida);
            txtCantidad = vista.findViewById(R.id.txt_item_barra_comanda_cantidad);

            btnEntregar = vista.findViewById(R.id.btn_item_barra_prepararBebida);
        }
    }
}
