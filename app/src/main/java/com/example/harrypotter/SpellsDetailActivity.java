package com.example.harrypotter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SpellsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_spells_detail);

        if (savedInstanceState == null) {
            // Get character data from Intent
            String spellsId = getIntent().getStringExtra("id");
            String spellsName = getIntent().getStringExtra("name");
            String spellsImageUrl = getIntent().getStringExtra("img");
            String spellsIncantation = getIntent().getStringExtra("incantation");
            String spellsCategory = getIntent().getStringExtra("category");
            String spellsEffect = getIntent().getStringExtra("effect");
            String spellsCreator = getIntent().getStringExtra("creator");



            // Create a bundle to pass data to the fragment
            Bundle args = new Bundle();
            args.putString("id", spellsId);
            args.putString("name", spellsName);
            args.putString("img", spellsImageUrl);
            args.putString("incantation", spellsIncantation);
            args.putString("category", spellsCategory);
            args.putString("effect", spellsEffect);
            args.putString("creator", spellsCreator);


            // Create a fragment instance and set its arguments
            SpellsDetailFragment detailFragment = new SpellsDetailFragment();
            detailFragment.setArguments(args);

            // Display the fragment in the activity
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .commit();
        }
    }

    public void onBackButtonClick(View view) {
        Log.i("onBackPressed", "Back button pressed"); // Add this line
        // Check if there are fragments on the back stack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // Pop the top fragment from the back stack (which is the CharactersFragment)
            getSupportFragmentManager().popBackStack();
            Log.i("test a", "test a");
        } else {
            // If the back stack is empty, perform the default back button behavior
            super.onBackPressed();
            Log.i("test b", "test b");
        }
    }
}
