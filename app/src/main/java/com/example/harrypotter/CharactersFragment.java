package com.example.harrypotter;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharactersFragment extends Fragment {
    private TextView emptyView;
    private JSONArray arr;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<Character> characterList = new ArrayList<>(); // Declare and initialize the list

    private CharacterAdapter characterAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_characters, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.empty_view);

        searchView = view.findViewById(R.id.searchView);
        searchView.setVisibility(view.GONE);

        // Configurăm OnQueryTextListener pentru SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Tratăm cazul în care se efectuează o căutare
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Tratăm schimbările de text în căutare
                // Apelăm metoda performSearch cu textul căutat
                performSearch(newText);
                return true;
            }
        });


        // Setăm un LinearLayoutManager pentru RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        characterAdapter = new CharacterAdapter(new ArrayList<>(), requireContext());
        recyclerView.setAdapter(characterAdapter);
        // Apelăm metoda pentru a încărca și afișa personajele
        fetchAndDisplayCharacters();

        return view;
    }
    // Metoda pentru a efectua căutarea personajelor

    private void performSearch(String query) {
        // Obținem activitatea principală și apelăm metoda pentru a căuta personaje după nume

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.fetchAndDisplayCharactersByNameInFragment(query);
        }
    }
    // Metoda pentru a încărca și afișa personajele

    private void fetchAndDisplayCharacters() {
        new Thread(() -> {
            try {
                // Obținem datele JSON de la API

                String jsonData = ApiService.fetchData("https://api.potterdb.com/v1/characters");
                List<Character> characters = parseJsonData(jsonData);

                Activity activity = getActivity();
                if (isAdded() && activity != null) {
                requireActivity().runOnUiThread(() -> {
                    characterList.clear(); // Curățăm lista înainte de actualizare
                    characterList.addAll(characters); // Actualizăm lista cu datele primite
                    characterAdapter.notifyDataSetChanged(); // Notificăm adapterul că datele s-au schimbat
                });}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Metoda pentru a parsa datele JSON

    private List<Character> parseJsonData(String jsonData) {
        List<Character> characters = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray data = jsonObject.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject characterObject = data.getJSONObject(i);
                String id = characterObject.getString("id");
                String name = characterObject.getJSONObject("attributes").getString("name");
                String imageUrl = characterObject.getJSONObject("attributes").optString("image");
                String died = characterObject.getJSONObject("attributes").getString("died");
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


}