package com.example.a1tutorial.adaptador;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.a1tutorial.models.Carta;
import com.example.a1tutorial.providers.ComandaDatabasePorvider;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class AdaptadorCamareroComandasPlatos extends FirestoreRecyclerAdapter<Carta, AdaptadorCamareroComandasPlatos.ViewComandaPlatos> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    String idDocumentComanda;
    ComandaDatabasePorvider comandaDatabasePorvider;
    public AdaptadorCamareroComandasPlatos(@NonNull FirestoreRecyclerOptions<Carta> options, String idDocument){
        super(options);
        idDocumentComanda = idDocument;
        comandaDatabasePorvider = new ComandaDatabasePorvider();
    }

    @Override
    protected void onBindViewHolder(@NonNull final AdaptadorCamareroComandasPlatos.ViewComandaPlatos holder, int position, @NonNull final Carta model) {
        System.out.println(model);
        holder.txtNombrePlato.setText(model.getNombre());
        holder.txtCantidadPlato.setText("Unidades"+model.getUnidades());

        final String idDocumentPlato = getSnapshots().getSnapshot(position).getId();

        if (model.getEstado().equals("rojo")){
            holder.linearLayout.setBackgroundResource(R.color.comidaEnProceso);
        }
        if (model.getEstado().equals("naranja")){
            holder.linearLayout.setBackgroundResource(R.color.comidaHecha);
        }
        if (model.getEstado().equals("verde")){
            holder.linearLayout.setBackgroundResource(R.color.comidaEntregada);
        }

        holder.btnServido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getEstado().equals("naranja")){
                    comandaDatabasePorvider.entregarComida(idDocumentComanda, idDocumentPlato).update("estado", "verde").addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public AdaptadorCamareroComandasPlatos.ViewComandaPlatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camarero_comandas_platos, parent, false);
        return new ViewComandaPlatos(vista);
    }

    public class ViewComandaPlatos extends RecyclerView.ViewHolder {
        TextView txtNombrePlato, txtCantidadPlato;
        Button btnServido;
        LinearLayout linearLayout;
        public ViewComandaPlatos(@NonNull View vista) {
            super(vista);
            txtNombrePlato = vista.findViewById(R.id.txt_item_camarero_comandas_nombrePlato);
            txtCantidadPlato = vista.findViewById(R.id.txt_item_camarero_comandas_cantidad);

            btnServido = vista.findViewById(R.id.btn_item_comandas_platos);

            linearLayout = vista.findViewById(R.id.linearLayout_item_camarero_comandas_plato);
        }
    }
}
