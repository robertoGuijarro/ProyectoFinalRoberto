package com.example.a1tutorial.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.models.Bebidas;
import com.example.a1tutorial.providers.ComandaDatabasePorvider;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class AdaptadorCamareroComandasBebidas extends FirestoreRecyclerAdapter<Bebidas, AdaptadorCamareroComandasBebidas.ViewComandaBebida> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    String idDocumentComanda;
    ComandaDatabasePorvider comandaDatabasePorvider;
    public AdaptadorCamareroComandasBebidas(@NonNull FirestoreRecyclerOptions<Bebidas> options, String idDocument) {
        super(options);
        comandaDatabasePorvider = new ComandaDatabasePorvider();
        idDocumentComanda = idDocument;
    }

    @Override
    protected void onBindViewHolder(@NonNull final AdaptadorCamareroComandasBebidas.ViewComandaBebida holder, int position, @NonNull final Bebidas model) {
        System.out.println(model);
        holder.txtNombreBebida.setText(model.getNombre());
        holder.txtCantidadBebida.setText("Unidades:"+ model.getUnidades());

        final String idDocumentBebida = getSnapshots().getSnapshot(position).getId();

        if (model.getEstado().equals("rojo")){
            holder.linearLayout.setBackgroundResource(R.color.comidaEnProceso);
        }
        if (model.getEstado().equals("naranja")){
            holder.linearLayout.setBackgroundResource(R.color.comidaHecha);
        }
        if (model.getEstado().equals("verde")){
            holder.linearLayout.setBackgroundResource(R.color.comidaEntregada);
        }
        holder.btnServidrBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getEstado().equals("naranja")){
                    comandaDatabasePorvider.entregarBebida(idDocumentComanda, idDocumentBebida).update("estado", "verde").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                }else{
                    Toast.makeText(holder.itemView.getContext(), "La entrega todavai no esta lista", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @NonNull
    @Override
    public AdaptadorCamareroComandasBebidas.ViewComandaBebida onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camarero_comandas_bebidas, parent, false);
        return new ViewComandaBebida(vista);
    }

    public class ViewComandaBebida extends RecyclerView.ViewHolder {
        TextView txtNombreBebida, txtCantidadBebida;
        Button btnServidrBebida;
        LinearLayout linearLayout;
        public ViewComandaBebida(@NonNull View vista) {
            super(vista);
            txtNombreBebida = vista.findViewById(R.id.txt_item_camarero_comandas_nombreBebida);
            txtCantidadBebida = vista.findViewById(R.id.txt_item_camarero_comandas_cantidadBebida);

            btnServidrBebida = vista.findViewById(R.id.btn_item_camarero_comandas_entregarBebida);

            linearLayout = vista.findViewById(R.id.linearLayout_item_camarero_comandas_bebidas);
        }
    }
}
