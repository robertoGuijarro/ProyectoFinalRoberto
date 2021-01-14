package com.example.a1tutorial.activity.cocinero;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.a1tutorial.R;
import com.example.a1tutorial.activity.cocinero.Fragments_cocinero.Fragment_cocinero_comandas;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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


    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_cocinero, fragment);
        transaction.commit();
    }
}
