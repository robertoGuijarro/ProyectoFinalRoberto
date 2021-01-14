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
import com.example.a1tutorial.providers.ComandaDatabasePorvider;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class AdaptadorCocineroComandasPlatos extends FirestoreRecyclerAdapter<Carta, AdaptadorCocineroComandasPlatos.ViewComandaPlatos> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    ComandaDatabasePorvider comandaDatabasePorvider;
    String documentoComanda;
    public AdaptadorCocineroComandasPlatos(@NonNull FirestoreRecyclerOptions<Carta> options, String idDocumento) {
        super(options);

        comandaDatabasePorvider = new ComandaDatabasePorvider();

        documentoComanda = idDocumento;
    }

    @Override
    protected void onBindViewHolder(@NonNull AdaptadorCocineroComandasPlatos.ViewComandaPlatos holder, int position, @NonNull Carta model) {
        holder.txtNombrePlato.setText(model.getNombre());
        holder.txtCantidad.setText(model.getUnidades()+"");

        final String idDocument =getSnapshots().getSnapshot(position).getId();

        System.out.println("Documentossssss"+idDocument);
        holder.btnEntregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               comandaDatabasePorvider.updatePlatoCocinero(documentoComanda, idDocument).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        System.out.println("HHHHHHHHHHHHHHHOOOOOOOOOOOOOLLLLLLLLLLLLLAAAAAAAAA"+ documentSnapshot.getString("nombre"));
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public AdaptadorCocineroComandasPlatos.ViewComandaPlatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cocinero_comandas_platos, parent, false);
        return new ViewComandaPlatos(vista);
    }

    public class ViewComandaPlatos extends RecyclerView.ViewHolder {
        TextView txtNombrePlato, txtCantidad;
        Button btnEntregar;
        public ViewComandaPlatos(@NonNull View vista) {
            super(vista);

            txtNombrePlato = vista.findViewById(R.id.txt_item_cocinero_comandas_nombrePlato);
            txtCantidad = vista.findViewById(R.id.txt_item_cocinero_comandas_cantidad);

            btnEntregar = vista.findViewById(R.id.btn_item_cocinero_cocinarPlato);
        }
    }
}
