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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class AdaptadorCamareroComandaBebidas extends FirestoreRecyclerAdapter<Bebidas, AdaptadorCamareroComandaBebidas.ViewComandaBebida> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    ArrayList<Bebidas> mydrinksSelected;

    public ArrayList<Bebidas> getMydrinksSelected() {
        return mydrinksSelected;
    }


    public AdaptadorCamareroComandaBebidas(@NonNull FirestoreRecyclerOptions<Bebidas> options) {
        super(options);
        mydrinksSelected = new ArrayList<>();
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewComandaBebida holder, final int position, @NonNull final Bebidas model) {
        holder.txtNombre.setText(model.getNombre());
        holder.txtPrecio.setText(model.getPrecio()+"€");
        holder.txtStock.setText(""+ model.getStock());
        String idDocument=getSnapshots().getSnapshot(position).getId();

        mydrinksSelected.add(new Bebidas(idDocument,model.getNombre(),model.getPrecio(),model.getUrl(),model.getStock(),model.getUnidades(),model.getEstado(),model.isAlcoholicas()));

        holder.btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getStock()<= Integer.parseInt(holder.txtCantidad.getText().toString())){
                    System.out.println("no hay mas");
                }else{
                    int cantidad = Integer.parseInt(holder.txtCantidad.getText().toString());
                    cantidad++;
                    holder.txtCantidad.setText(cantidad+"");
                    mydrinksSelected.get(position).setUnidades(Integer.parseInt(holder.txtCantidad.getText().toString()));
                }
            }
        });
        holder.btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cantidad = Integer.parseInt(holder.txtCantidad.getText().toString());
                if (cantidad>0){
                    cantidad--;
                    holder.txtCantidad.setText(cantidad+"");
                    mydrinksSelected.get(position).setUnidades(Integer.parseInt(holder.txtCantidad.getText().toString()));
                }
            }
        });
    }

    @NonNull
    @Override
    public ViewComandaBebida onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camarero_comanda_bebidas, parent, false);

        return new ViewComandaBebida(vista);
    }

    public class ViewComandaBebida extends RecyclerView.ViewHolder {
        TextView txtNombre, txtPrecio, txtCantidad, txtStock;
        Button btnMas, btnMenos;
        public ViewComandaBebida(@NonNull View vista) {
            super(vista);

            txtPrecio = vista.findViewById(R.id.txt_item_comanda_bebidas_precio);
            txtCantidad = vista.findViewById(R.id.txt_item_comanda_bebidas_cantidad);
            txtNombre = vista.findViewById(R.id.txt_item_comanda_bebidas_nombre);
            txtStock = vista.findViewById(R.id.txt_item_comanda_bebidas_stock);
            btnMenos=vista.findViewById(R.id.btn_item_comanda_bebidas_menos);
            btnMas=vista.findViewById(R.id.btn_item_comanda_bebidas_mas);
        }
    }
}
