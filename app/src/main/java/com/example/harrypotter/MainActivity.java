package com.example.harrypotter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
private SearchView searchView;
    private RecyclerView recyclerView;
    private FrameLayout frameLayout;
    private CharacterAdapter characterAdapter;
    private PotionAdapter potionAdapter;
    private SpellsAdapter spellsAdapter;
    SettingsFragment settingsFragment = new SettingsFragment();
    CharactersFragment charactersFragment = new CharactersFragment();
    SpellsFragment spellsFragment = new SpellsFragment();
    PotionsFragment potionsFragment = new PotionsFragment();

    private SharedPreferences sharedPreferences = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("night_mode", true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        //region Bara de sus cu logo
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.ic_logo);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.hide();
        }
        // Customize the color of the app bar
        //  int appbarColor = ResourcesCompat.getColor(getResources(), R.color.dark_red, null);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(appbarColor));
        //endregion




        // Initialize adapters

        //region character adapter
        characterAdapter = new CharacterAdapter(new ArrayList<>(),this);
        characterAdapter.setOnItemClickListener(new CharacterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                    //  item click here(pentru pagina de detalii)
                Character mData = characterAdapter.getItem(position);
                // Cream o noua instanta a CharacterDetailFragment
                CharacterDetailFragment detailFragment = new CharacterDetailFragment();

                // Vom pasa datele folosind argumente
                Bundle args = new Bundle();
                args.putString("id", mData.getId());
                args.putString("name", mData.getName());
                args.putString("img", mData.getImageUrl());
                args.putString("died", mData.getDied());
                args.putString("born", mData.getBorn());
                args.putString("nationality", mData.getNationality());
                args.putString("species", mData.getSpecies());
                args.putString("house", mData.getHouse());
                args.putString("gender", mData.getGender());

                detailFragment.setArguments(args);

                // Aici de face inlocuirea fragmentului cu CharactersDetailFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recyclerView, detailFragment)
                        .addToBackStack(null) // Adaugam la stiva pentru back
                        .commit();

            }
        });
        //endregion


        //region potions adapter
        potionAdapter = new PotionAdapter(new ArrayList<>(),this);
        potionAdapter.setOnItemClickListener(new PotionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Potion mData = potionAdapter.getItem(position);

                PotionsDetailFragment detailFragment = new PotionsDetailFragment();

                Bundle args = new Bundle();
                args.putString("id", mData.getId());
                args.putString("name", mData.getName());
                args.putString("img", mData.getImageUrl());
                args.putString("effect", mData.getEffect());
                args.putString("side_effects", mData.getSide_effects());
                args.putString("time", mData.getTime());
                args.putString("difficulty", mData.getDifficulty());

                detailFragment.setArguments(args);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recyclerView, detailFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
        //endregion


        //region spells
        spellsAdapter = new SpellsAdapter(new ArrayList<>(),this);
        spellsAdapter.setOnItemClickListener(new SpellsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Spells mData = spellsAdapter.getItem(position);
                SpellsDetailFragment detailFragment = new SpellsDetailFragment();

                Bundle args = new Bundle();
                args.putString("id", mData.getId());
                args.putString("name", mData.getName());
                args.putString("img", mData.getImageUrl());
                args.putString("incantation", mData.getIncantation());
                args.putString("category", mData.getCategory());
                args.putString("effect", mData.getEffect());
                args.putString("creator", mData.getCreator());

                detailFragment.setArguments(args);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recyclerView, detailFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
        //endregion

        mAuth = FirebaseAuth.getInstance();
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, charactersFragment).commit();

        frameLayout = findViewById(R.id.container);


        //foarte important, pentru a aparea n MainActivity Personajele vom apela functia care afiseaza characters(jn nav_bar avem deja setat ca sa fie by default characters)
        fetchAndDisplayCharacters();

        BottomNavigationView bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_characters:
                    searchView.setVisibility(View.VISIBLE);
                    frameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(characterAdapter);

                    fetchAndDisplayCharacters();
                    //acest searchView va fi initializat pentru fiecare caz de navigatie, deoarece in functie de categoria aleasa, api ul de schimba

                    //region search initializat characters
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {

                            fetchAndDisplayCharactersByName(newText); // Update the method name and parameters as needed
                            return true;
                        }
                    });
                    //endregion

                    return true;
                case R.id.nav_potions:
                    frameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(potionAdapter);
                    fetchAndDisplayPotions();

                    //region search initializat potions
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            fetchAndDisplayPotionsByName(newText); // Update the method name and parameters as needed
                            return true;
                        }
                    });
                    //endregion

                    return true;
                case R.id.nav_spells:

                    frameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(spellsAdapter);
                    fetchAndDisplaySpells();
                    //region search initializat spells
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            fetchAndDisplaySpellsByName(newText); // Update the method name and parameters as needed
                            return true;
                        }
                    });
                    //endregion
                    return true;
                case R.id.nav_settings:
                    frameLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
                return true;
                default:
                    return false;
            }
        });
    }
    //region search Characters
    //functionalitatea cautarii character in searchView
    private void fetchAndDisplayCharactersByName(String query) {
        new Thread(() -> {
            try {
                String jsonData = ApiService.fetchData("https://api.potterdb.com/v1/characters?filter[name_cont_any]=" + query);
                List<Character> characters = parseJsonData(jsonData);

                runOnUiThread(() -> {
                    characterAdapter.updateData(characters);
                    recyclerView.setAdapter(characterAdapter);

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    //facem o incapsulare a metodei de mai sus pentru a nu bloca interfata de utilizator in timpul cererii catre API
    public void fetchAndDisplayCharactersByNameInFragment(String query) {
        fetchAndDisplayCharactersByName(query);
    }
    //end search Characters
    //endregion

    //region search Spells
    private void fetchAndDisplaySpellsByName(String query) {
        new Thread(() -> {
            try {
                String jsonData = ApiService.fetchData("https://api.potterdb.com/v1/spells?filter[name_cont_any]=" + query);
                List<Spells> spells = parseSpellsJsonData(jsonData);

                runOnUiThread(() -> {
                    spellsAdapter.updateDataSpells(spells);
                    recyclerView.setAdapter(spellsAdapter);

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void fetchAndDisplaySpellsByNameInFragment(String query) {
        fetchAndDisplaySpellsByName(query);
    }
    //endregion search Spells

    //region start search Potions
    private void fetchAndDisplayPotionsByName(String query) {
        new Thread(() -> {
            try {
                String jsonData = ApiService.fetchData("https://api.potterdb.com/v1/potions?filter[name_cont_any]=" + query);
                List<Potion> potions = parsePotionJsonData(jsonData);

                runOnUiThread(() -> {
                    potionAdapter.updateDataPotions(potions);
                    recyclerView.setAdapter(potionAdapter);

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void fetchAndDisplayPotionsByNameInFragment(String query) {
        fetchAndDisplayPotionsByName(query);
    }
//endregion search function for Potions


    //region start app
    //vom gestiona ciclul de viata al acvitatii
    //vom verifica data un utilizator e conectat sau nu in prezent, in cazul in care nu exista se apeleaza urm functie
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendToLogin();
        }
    }
    //vom redirectiona utilizatorul la aceasta fereastra daca nu este logat
    private void sendToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    //endregion

    //region fetchAndDisplaySpells
    //aceasta functie ia datele din api si le afiseaza in recyclerview
    private void fetchAndDisplaySpells() {
        new Thread(() -> {
            try {
                String jsonData = ApiService.fetchData("https://api.potterdb.com/v1/spells");
                List<Spells> spells = parseSpellsJsonData(jsonData);

                runOnUiThread(() -> {
                    spellsAdapter = new SpellsAdapter(spells,MainActivity.this);
                    recyclerView.setAdapter(spellsAdapter);

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    //endregion

    //region fetchAndDisplayPotions
    private void fetchAndDisplayPotions() {
        new Thread(() -> {
            try {
                String jsonData = ApiService.fetchData("https://api.potterdb.com/v1/potions");
                List<Potion> potions = parsePotionJsonData(jsonData);

                runOnUiThread(() -> {
                    potionAdapter = new PotionAdapter(potions,MainActivity.this);
                    recyclerView.setAdapter(potionAdapter);

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    //endregion

    //region fetchAndDisplayCharacters
    private void fetchAndDisplayCharacters() {
        new Thread(() -> {
            try {
                String jsonData = ApiService.fetchData("https://api.potterdb.com/v1/characters");
                List<Character> characters = parseJsonData(jsonData);

                runOnUiThread(() -> {
                    characterAdapter = new CharacterAdapter( characters,MainActivity.this);
                    recyclerView.setAdapter(characterAdapter);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    //endregion

    //region parseSpellsJsonData
    //vom parsa sirul jsonData si vom crea o lista de obiecte Spells care va fi mai apoi vor fi afisate in alta functie
    private List<Spells> parseSpellsJsonData(String jsonData) {
        List<Spells> spells = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray data = jsonObject.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject spellsObject = data.getJSONObject(i);
                String id = spellsObject.getString("id");
                String name = spellsObject.getJSONObject("attributes").getString("name");
                String imageUrl = spellsObject.getJSONObject("attributes").optString("image");  // Extract image URL
                String incantation = spellsObject.getJSONObject("attributes").optString("incantation");  // Extract image URL
                String category = spellsObject.getJSONObject("attributes").optString("category");  // Extract image URL
                String effect = spellsObject.getJSONObject("attributes").optString("effect");  // Extract image URL
                String creator = spellsObject.getJSONObject("attributes").optString("creator");  // Extract image URL

                spells.add(new Spells(id, name, imageUrl, incantation, category, effect, creator));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return spells;
    }
    //endregion

    //region parsePotionJsonData
    private List<Potion> parsePotionJsonData(String jsonData) {
        List<Potion> potions = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray data = jsonObject.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject potionObject = data.getJSONObject(i);
                String id = potionObject.getString("id");
                String name = potionObject.getJSONObject("attributes").getString("name");
                String imageUrl = potionObject.getJSONObject("attributes").optString("image");  // Extract image URL
                String effect = potionObject.getJSONObject("attributes").optString("effect");  // Extract image URL
                String side_effect = potionObject.getJSONObject("attributes").optString("side_effects");  // Extract image URL
                String time = potionObject.getJSONObject("attributes").optString("time");  // Extract image URL
                String difficulty = potionObject.getJSONObject("attributes").optString("difficulty");  // Extract image URL

                potions.add(new Potion(id, name, imageUrl, effect, side_effect, time, difficulty));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return potions;
    }
//endregion

    //region parseJsonData
    private List<Character> parseJsonData(String jsonData) {
        List<Character> characters = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray data = jsonObject.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject characterObject = data.getJSONObject(i);
                String id = characterObject.getString("id");
                String name = characterObject.getJSONObject("attributes").getString("name");
                String imageUrl = characterObject.getJSONObject("attributes").optString("image");  // Extract image URL
                String died = characterObject.getJSONObject("attributes").optString("died");  // Extract image URL
                String born = characterObject.getJSONObject("attributes").getString("born");
                String species = characterObject.getJSONObject("attributes").getString("species");
                String nationality = characterObject.getJSONObject("attributes").getString("nationality");
                String house = characterObject.getJSONObject("attributes").getString("house");
                String gender = characterObject.getJSONObject("attributes").getString("gender");

                characters.add(new Character(id, name, imageUrl, died, born, species, nationality, house,  gender));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return characters;
    }
    //endregion



}
