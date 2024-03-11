package com.example.harrypotter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class CharacterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_character_detail);

        if (savedInstanceState == null) {
            String characterId = getIntent().getStringExtra("id");
            String characterName = getIntent().getStringExtra("name");
            String characterImageUrl = getIntent().getStringExtra("img");
            String characterBorn = getIntent().getStringExtra("born");
            String characterDied = getIntent().getStringExtra("died");
            String characterGender = getIntent().getStringExtra("gender");
            String characterSpecies = getIntent().getStringExtra("species");
            String characterNationality = getIntent().getStringExtra("nationality");
            String characterHouse = getIntent().getStringExtra("house");


            Bundle args = new Bundle();
            args.putString("id", characterId);
            args.putString("name", characterName);
            args.putString("img", characterImageUrl);
            args.putString("born", characterBorn);
            args.putString("died", characterDied);
            args.putString("gender", characterGender);
            args.putString("species", characterSpecies);
            args.putString("nationality", characterNationality);
            args.putString("house", characterHouse);


            CharacterDetailFragment detailFragment = new CharacterDetailFragment();
            detailFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .commit();
        }



    }
    // Metoda pentru gestionarea apăsării butonului de revenire înapoi in characters

    public void onBackButtonClick(View view) {
        Log.i("onBackPressed", "Back button pressed"); // Add this line
        // Verifică dacă există fragmente în stiva de înapoi, practic verifica daca are un fragment unde sa mearga back
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // Scoate fragmentul din vârful stivei de înapoi, ca intr-o piramida, de la varf in jos
            getSupportFragmentManager().popBackStack();
            Log.i("test a", "test a");
        } else {
            // Dacă stiva de înapoi este goală, efectuează comportamentul implicit al butonului de revenire
            super.onBackPressed();
            Log.i("test b", "test b");
        }
        //am adaugat si logs pentru a facilita atat testarea cat si dezvoltarea aplicatiei
    }




}
