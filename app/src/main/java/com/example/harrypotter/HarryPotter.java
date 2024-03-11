package com.example.harrypotter;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HarryPotter extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //pentru a putea stoca useri offline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
        usersRef.keepSynced(true);}
}
