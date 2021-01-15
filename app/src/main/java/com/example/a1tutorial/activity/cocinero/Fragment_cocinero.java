package com.example.a1tutorial.activity.cocinero;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.a1tutorial.R;
import com.example.a1tutorial.activity.AuthActivity;
import com.example.a1tutorial.activity.camarero.Fragment_camarero;
import com.example.a1tutorial.activity.cocinero.Fragments_cocinero.Fragment_cocinero_comandas;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Fragment_cocinero extends AppCompatActivity {

Fragment_cocinero_comandas comandasFragment = new Fragment_cocinero_comandas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocinero_fragment);

        BottomNavigationView navigation = findViewById(R.id.botton_vavigation_cocinero);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(comandasFragment);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_cocinero_comandas:
                    loadFragment(comandasFragment);
                    return true;
            }
            return false;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cerrar_sesion, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu_cerrarSesion){
            FirebaseAuth.getInstance().signOut();

            Intent intento = new Intent(Fragment_cocinero.this, AuthActivity.class);
            startActivity(intento);
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_cocinero, fragment);
        transaction.commit();
    }
}
