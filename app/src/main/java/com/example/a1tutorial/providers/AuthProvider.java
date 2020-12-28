package com.example.a1tutorial.providers;

import com.google.firebase.auth.FirebaseAuth;

public class AuthProvider {
    FirebaseAuth auth;

    public AuthProvider() {
        auth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public String idAuth(){
        return auth.getUid();
    }
}
