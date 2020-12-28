package com.example.a1tutorial.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1tutorial.R;
//import com.example.a1tutorial.databinding.ActivityHomeBinding;
import com.example.a1tutorial.providers.UserDatabaseProvider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    UserDatabaseProvider dbUser;
    //ActivityHomeBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*binding=ActivityHomeBinding.inflate(getLayoutInflater());
        View vista=binding.getRoot();
        setContentView(vista);
*/
        mAuth = FirebaseAuth.getInstance();
        dbUser = new UserDatabaseProvider();

        Map<String, Object> user = new HashMap<>();

        user.put("nombre", "roberto");
        user.put("casa", "bonita");
        user.put("tonto", "casa");

        dbUser.getDatabase().add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference){
                Toast.makeText(HomeActivity.this, "Se ha añadio ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}