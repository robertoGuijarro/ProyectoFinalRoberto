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

import java.util.ArrayList;

public class AdaptadorComandaComida extends FirestoreRecyclerAdapter<Carta, AdaptadorComandaComida.ViewComandaComida> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    ArrayList<Carta> myFoodSelected;

    public ArrayList<Carta> getMyFoodSelected() {
        return myFoodSelected;
    }

    public AdaptadorComandaComida(@NonNull FirestoreRecyclerOptions<Carta> options) {
        super(options);
        myFoodSelected=new ArrayList<>();
    }

    @Override
    protected void onBindViewHolder(@NonNull final AdaptadorComandaComida.ViewComandaComida holder, final int position, @NonNull final Carta model) {
        holder.txtNombre.setText(model.getNombre());
        holder.txtPrecio.setText(model.getPrecio()+"€");
        holder.txtStock.setText(""+ model.getStock());
        String idDocument=getSnapshots().getSnapshot(position).getId();

        myFoodSelected.add(new Carta(idDocument,model.getNombre(), model.getPrecio(),0, model.getStock(), "rojo", "comida"));

        holder.btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getStock()<= Integer.parseInt(holder.txtCantidad.getText().toString())){
                    System.out.println("no hay mas");
                }else{
                    int cantidad = Integer.parseInt(holder.txtCantidad.getText().toString());
                    cantidad++;
                    holder.txtCantidad.setText(cantidad+"");
                    myFoodSelected.get(position).setUnidades(Integer.parseInt(holder.txtCantidad.getText().toString()));
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
                    myFoodSelected.get(position).setUnidades(Integer.parseInt(holder.txtCantidad.getText().toString()));
                }
            }
        });
    }


    @NonNull
    @Override
    public AdaptadorComandaComida.ViewComandaComida onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comanda_comida, parent, false);
        return new ViewComandaComida(vista);
    }

    public class ViewComandaComida extends RecyclerView.ViewHolder {

        TextView txtNombre, txtPrecio, txtCantidad, txtStock;
        Button btnMas, btnMenos;
        public ViewComandaComida(View vista) {
            super(vista);
            txtPrecio = vista.findViewById(R.id.txt_item_comanda_precio);
            txtCantidad = vista.findViewById(R.id.txt_item_comanda_cantidad);
            txtNombre = vista.findViewById(R.id.txt_item_comanda_nombre);
            txtStock = vista.findViewById(R.id.txt_item_comanda_stock);
            btnMenos=vista.findViewById(R.id.btn_item_comanda_menos);
            btnMas=vista.findViewById(R.id.btn_item_comanda_mas);
        }
    }
}
