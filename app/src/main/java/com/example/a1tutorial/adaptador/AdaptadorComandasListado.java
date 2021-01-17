package com.example.a1tutorial.adaptador;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.models.Bebidas;
import com.example.a1tutorial.models.Carta;
import com.example.a1tutorial.models.Comanda;
import com.example.a1tutorial.providers.ComandaDatabasePorvider;
import com.example.a1tutorial.providers.UserDatabaseProvider;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class AdaptadorComandasListado extends FirestoreRecyclerAdapter<Comanda, AdaptadorComandasListado.ViewComandaComida> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    ComandaDatabasePorvider comandaDatabasePorvider;
    UserDatabaseProvider userDatabaseProvider;

    public AdaptadorComandasListado(@NonNull FirestoreRecyclerOptions<Comanda> options) {
        super(options);

        comandaDatabasePorvider = new ComandaDatabasePorvider();

        userDatabaseProvider = new UserDatabaseProvider();
    }

    @Override
    protected void onBindViewHolder(@NonNull final AdaptadorComandasListado.ViewComandaComida holder, int position, @NonNull final Comanda model) {

        final String idDocument =getSnapshots().getSnapshot(position).getId();

        holder.txtNombreCamarero.setText(model.getNameCamarero());
        holder.txtNumeroMesa.setText(model.getMesa()+"");

        holder.listadoPlatos.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));

        holder.listadoBebidas.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));

        holder.btnDesplegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.desplegable.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.desplegable.setVisibility(View.VISIBLE);
                    holder.btnDesplegar.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);

                    cargarCardView(comandaDatabasePorvider.getComandasPlatos(idDocument), holder.listadoPlatos, idDocument);

                    cargarCardViewBebidas(comandaDatabasePorvider.getComandasBebidas(idDocument), holder.listadoBebidas, idDocument);
                }else{
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.desplegable.setVisibility(View.GONE);
                    holder.btnDesplegar.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });
    }

    private void cargarCardViewBebidas(Query comandasBebidas, RecyclerView listadoBebidas, String idDocument) {
        AdaptadorCamareroComandasBebidas adaptadorCamareroComandasBebidas;

        FirestoreRecyclerOptions<Bebidas> optionsBebidas = new FirestoreRecyclerOptions.Builder<Bebidas>().setQuery(comandasBebidas, Bebidas.class).build();
        adaptadorCamareroComandasBebidas = new AdaptadorCamareroComandasBebidas(optionsBebidas, idDocument);

        listadoBebidas.setAdapter(adaptadorCamareroComandasBebidas);
        adaptadorCamareroComandasBebidas.startListening();
    }

    public void cargarCardView(Query comandasPlatos, RecyclerView holder, String idDocument) {
        AdaptadorCamareroComandasPlatos adaptadorCamareroComandasPlatos;

        FirestoreRecyclerOptions<Carta> options = new FirestoreRecyclerOptions.Builder<Carta>().setQuery(comandasPlatos, Carta.class).build();
        adaptadorCamareroComandasPlatos = new AdaptadorCamareroComandasPlatos(options, idDocument);

        holder.setAdapter(adaptadorCamareroComandasPlatos);
        adaptadorCamareroComandasPlatos.startListening();


    }

    @NonNull
    @Override
    public AdaptadorComandasListado.ViewComandaComida onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camarero_comandas, parent, false);
        return new ViewComandaComida(vista);
    }

    public class ViewComandaComida extends RecyclerView.ViewHolder {
        TextView txtNumeroMesa, txtNombreCamarero;
        Button btnDesplegar;

        CoordinatorLayout desplegable;

        CardView cardView;

        RecyclerView listadoPlatos, listadoBebidas;
        public ViewComandaComida(View vista) {
            super(vista);
            txtNombreCamarero = vista.findViewById(R.id.txt_item_camarero_comandas_nombreCamarero);
            txtNumeroMesa = vista.findViewById(R.id.txt_item_camarero_comandas_mesa);

            btnDesplegar = vista.findViewById(R.id.btn_item_comandas_desplegar);

            desplegable = vista.findViewById(R.id.coordinator_item_camarero_comandas_desplegable);

            cardView = vista.findViewById(R.id.cardView_item_camarero_comandas);

            listadoPlatos = vista.findViewById(R.id.listadoCamareroComandasComidaDesplegable);

            listadoBebidas = vista.findViewById(R.id.listadoCamareroComandasBebidasDesplegable);
        }
    }
}
