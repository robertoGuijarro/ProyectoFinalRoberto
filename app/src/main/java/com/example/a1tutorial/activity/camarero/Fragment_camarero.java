package com.example.a1tutorial.activity.camarero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.a1tutorial.R;
import com.example.a1tutorial.activity.camarero.fragments.Fragmen_carta;
import com.example.a1tutorial.activity.camarero.fragments.Fragment_comanda;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Fragment_camarero extends AppCompatActivity {

    Fragmen_carta cartaFragment = new Fragmen_carta();
    Fragment_comanda comandaFragment = new Fragment_comanda();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camarero_fragment);

        BottomNavigationView navigation = findViewById(R.id.botton_vavigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
            }
            return false;
        }
    };


    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}