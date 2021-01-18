package com.example.a1tutorial.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1tutorial.R;
import com.example.a1tutorial.activity.barra.Fragment_barra;
import com.example.a1tutorial.activity.camarero.Fragment_camarero;
import com.example.a1tutorial.activity.cocinero.Fragment_cocinero;
import com.example.a1tutorial.models.Bebidas;
import com.example.a1tutorial.models.Carta;
import com.example.a1tutorial.providers.UserDatabaseProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mauth;

    UserDatabaseProvider userDatabaseProvider;

    TextView txtEmail, txtpass;
    Button btnRegistrar, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mauth = FirebaseAuth.getInstance();
        userDatabaseProvider = new UserDatabaseProvider();

        txtEmail = findViewById(R.id.loginEmail);
        txtpass = findViewById(R.id.loginPassword);

        btnRegistrar = findViewById(R.id.loginRegistrerBtn);

        btnLogin = findViewById(R.id.loginButton);

        //rellenarCarta();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(AuthActivity.this, RegisterActivity.class);
                startActivity(intento);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mauth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtpass.getText().toString()).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            //rellenarCarta();

                            userDatabaseProvider.getOficio(txtEmail.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                                        System.out.println("Emails"+document.get("oficio"));

                                        if ("camarero".equals(document.getString("oficio"))){
                                            loginCamarero();
                                        }

                                        if ("cocinero".equals(document.getString("oficio"))){
                                            loginCocinero();
                                        }

                                        if ("barra".equals(document.getString("oficio"))){
                                            loginBarra();
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });


    }

    private void loginBarra() {
        Intent intentoBarra = new Intent(AuthActivity.this, Fragment_barra.class);
        startActivity(intentoBarra);
    }

    private void loginCocinero() {
        Intent intentoCocinero = new Intent(AuthActivity.this, Fragment_cocinero.class);
        startActivity(intentoCocinero);
    }

    private void loginCamarero() {
        Intent intentoCamarero = new Intent(AuthActivity.this, Fragment_camarero.class);
        startActivity(intentoCamarero);
    }

    private void rellenarCarta() {

        ArrayList<Carta> listaCarta = new ArrayList<>();
        listaCarta.add(new Carta("Lubina", 11, "pescado", "", 10));
        listaCarta.add(new Carta("Dorada", 9, "pescado", "", 14));
        listaCarta.add(new Carta("Salmon", 8, "pescado", "", 12));
        listaCarta.add(new Carta("Hamburguesa con beicon", 13, "carne", "", 67));
        listaCarta.add(new Carta("Hamburguesa vegana", 9, "carne", "", 12));
        listaCarta.add(new Carta("Filete con patatas", 7, "carne", "", 12));
        listaCarta.add(new Carta("Entrecot", 15, "carne", "", 12));
        listaCarta.add(new Carta("Lentejas", 7, "cocidos", "",12));
        listaCarta.add(new Carta("Judias", 10, "cocidos", "", 141));
        listaCarta.add(new Carta("Ibericos", 19, "entrantes","",14));
        listaCarta.add(new Carta("Croquetas", 5, "entrantes", "", 123));
        listaCarta.add(new Carta("Tostadas con jamon", 3, "entrantes", "",23));
        listaCarta.add(new Carta("Tarta de queso", 6, "postre", "",25));
        listaCarta.add(new Carta("Tarta de chocolate", 4, "postre", "", 26));

        for (Carta carta:listaCarta){
            Map<String, Object> cartas = new HashMap<>();
            cartas.put("nombre", carta.getNombre());
            cartas.put("precio", carta.getPrecio());
            cartas.put("tipo", carta.getTipo());
            cartas.put("url", carta.getUrl());
            cartas.put("stock", carta.getStock());

            FirebaseFirestore.getInstance().collection("Carta").add(cartas).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

        ArrayList<Bebidas> listaBebidas = new ArrayList<>();
        listaBebidas.add(new Bebidas("Cocacola", 1.5,"",40, false));
        listaBebidas.add(new Bebidas("Agua", 1,"",20, false));
        listaBebidas.add(new Bebidas("Fanta de Limon", 1.5,"",30, false));
        listaBebidas.add(new Bebidas("caña", 1.8,"",20, true));
        listaBebidas.add(new Bebidas("jarra", 3,"",400, true));
        listaBebidas.add(new Bebidas("Ron cocacola", 7,"",40, true));
        listaBebidas.add(new Bebidas("Fanta de naranja", 1.5,"",40, false));


        for (Bebidas bebida:listaBebidas){
            Map<String, Object> bebidas = new HashMap<>();
            bebidas.put("nombre", bebida.getNombre());
            bebidas.put("precio", bebida.getPrecio());
            bebidas.put("alcoholicas", bebida.isAlcoholicas());
            bebidas.put("url", bebida.getUrl());
            bebidas.put("stock", bebida.getStock());

            FirebaseFirestore.getInstance().collection("CartaBebidas").add(bebidas).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                }
            });
        }
    }



    @Override
    protected void onStart() {
        super.onStart();

        if (mauth.getCurrentUser()!=null){
            System.out.println(mauth.getCurrentUser().getEmail());
            userDatabaseProvider.getOficio(mauth.getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot document:queryDocumentSnapshots){
                        if ("camarero".equals(document.getString("oficio"))){
                            loginCamarero();
                        }
                        if ("cocinero".equals(document.getString("oficio"))){
                            loginCocinero();
                        }
                        if("barra".equals(document.getString("oficio"))){
                            loginBarra();
                        }
                    }
                }
            });
        }
    }

 
}