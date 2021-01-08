package com.example.a1tutorial.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a1tutorial.R;
import com.example.a1tutorial.activity.camarero.Fragment_camarero;
import com.example.a1tutorial.activity.cocinero.Fragment_cocinero;
import com.example.a1tutorial.models.Carta;
import com.example.a1tutorial.providers.UserDatabaseProvider;
import com.google.android.gms.tasks.OnCompleteListener;
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
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            userDatabaseProvider.getOficio(txtEmail.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                                        switch (document.getString("oficio")){
                                            case "camarero":
                                                System.out.println("Estoy en camarero"+document.getString("oficio"));

                                                FirebaseUser userCamarero = mauth.getCurrentUser();
                                                System.out.println(userCamarero.getDisplayName());
                                                Intent intentoCamarero = new Intent(AuthActivity.this, Fragment_camarero.class);
                                                startActivity(intentoCamarero);

                                            case "cocinero":

                                                System.out.println("Estoy en cocinero"+document.getString("oficio"));

                                                FirebaseUser userCocinero = mauth.getCurrentUser();
                                                System.out.println(userCocinero.getDisplayName());
                                                Intent intentoCocinero = new Intent(AuthActivity.this, Fragment_cocinero.class);
                                                startActivity(intentoCocinero);
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
            });
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mauth.getCurrentUser()!=null){
            //startActivity(new Intent(this, Fragment_camarero.class));
        }
    }

 
}