package com.example.harrypotter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PotionsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_potions_detail);

        if (savedInstanceState == null) {
            // Get character data from Intent
            String potionsId = getIntent().getStringExtra("id");
            String potionsName = getIntent().getStringExtra("name");
            String potionsImageUrl = getIntent().getStringExtra("img");
            String potionsEffect = getIntent().getStringExtra("effect");
            String potionsSideEffects = getIntent().getStringExtra("side_effects");
            String potionsTime = getIntent().getStringExtra("time");
            String potionsDifficulty = getIntent().getStringExtra("difficulty");



            // Create a bundle to pass data to the fragment
            Bundle args = new Bundle();
            args.putString("id", potionsId);
            args.putString("name", potionsName);
            args.putString("img", potionsImageUrl);
            args.putString("effect", potionsEffect);
            args.putString("time", potionsTime);
            args.putString("side_effects", potionsSideEffects);
            args.putString("difficulty", potionsDifficulty);


            // Create a fragment instance and set its arguments
            PotionsDetailFragment detailFragment = new PotionsDetailFragment();
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
