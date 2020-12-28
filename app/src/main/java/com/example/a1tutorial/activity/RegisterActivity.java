package com.example.a1tutorial.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1tutorial.R;
import com.example.a1tutorial.activity.camarero.Fragment_camarero;
import com.example.a1tutorial.models.User;
import com.example.a1tutorial.providers.AuthProvider;
import com.example.a1tutorial.providers.UserDatabaseProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    TextView txtEmail, txtPassword, txtName;
    Spinner spinnerOficio;
    Button btnResgistrar;
    private UserDatabaseProvider mUserProvider;
    String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserProvider = new UserDatabaseProvider();

        txtEmail = findViewById(R.id.registerEmail);
        txtPassword = findViewById(R.id.registerPassword);
        txtName = findViewById(R.id.resgisterName);

        spinnerOficio = findViewById(R.id.registerOficio);

        btnResgistrar = findViewById(R.id.registerBtn);
        btnResgistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearUsuario();
            }
        });

    }

    private void crearUsuario() {
        final String name = txtName.getText().toString();
        final String pass = txtPassword.getText().toString();
        final String email = txtEmail.getText().toString();
        final String oficio = spinnerOficio.getSelectedItem().toString();



        final AuthProvider authFirebase = new AuthProvider();
        authFirebase.getAuth().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "se ha registrado correctamente ", Toast.LENGTH_SHORT).show();
                    idUser = task.getResult().getUser().getUid();
                    User userNew = new User(name, email, oficio, pass);

                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("nombre", userNew.getName());
                    userMap.put("password", userNew.getPass());
                    userMap.put("email", userNew.getEmail());
                    userMap.put("oficio", userNew.getOficio());

                    mUserProvider.getDatabase().document(authFirebase.idAuth()).set(userMap);

                    Intent intento = new Intent(RegisterActivity.this, Fragment_camarero.class);

                    startActivity(intento);
                }else{
                    Toast.makeText(RegisterActivity.this, "No se ha registrado correctamente ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
