package com.example.a1tutorial.activity.camarero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.a1tutorial.R;
import com.example.a1tutorial.activity.AuthActivity;
import com.example.a1tutorial.activity.camarero.fragments_camarero.Fragmen_carta;
import com.example.a1tutorial.activity.camarero.fragments_camarero.Fragment_comanda;
import com.example.a1tutorial.activity.camarero.fragments_camarero.Fragment_comandas;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Fragment_camarero extends AppCompatActivity {

    Fragmen_carta cartaFragment = new Fragmen_carta();
    Fragment_comanda comandaFragment = new Fragment_comanda();
    Fragment_comandas comandasFragment = new Fragment_comandas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camarero_fragment);

        BottomNavigationView navigation = findViewById(R.id.botton_vavigation_camarero);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(comandaFragment);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_camarero_carta:
                    loadFragment(cartaFragment);
                    return true;
                case R.id.menu_camarero_comanda:
                    loadFragment(comandaFragment);
                    return true;
                case R.id.menu_camarero_comandas:
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

            Intent intento = new Intent(Fragment_camarero.this, AuthActivity.class);
            startActivity(intento);
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}