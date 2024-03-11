package com.example.harrypotter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpellsFragment extends Fragment {
    private SearchView searchView;
    private SpellsAdapter spellsAdapter;
    private List<Spells> spellsList = new ArrayList<>(); // Declare and initialize the list
    private RecyclerView recyclerViewSpells;
    private TextView emptyView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_spells, container, false);
        recyclerViewSpells = rootView.findViewById(R.id.recyclerViewSpells);
        emptyView = rootView.findViewById(R.id.empty_view);


        searchView = rootView.findViewById(R.id.searchView);
        searchView.setVisibility(rootView.GONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search submission if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search text changes here
                performSearch(newText); // Update the method name and parameters as needed
                return true;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerViewSpells.setLayoutManager(layoutManager);

        spellsAdapter = new SpellsAdapter(new ArrayList<>(),requireContext());
        recyclerViewSpells.setAdapter(spellsAdapter);
        fetchAndDisplaySpells();


        return rootView;
    }
    private void performSearch(String query) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.fetchAndDisplaySpellsByNameInFragment(query);
        }
    }
    private void fetchAndDisplaySpells() {
        new Thread(() -> {
            try {
                String jsonData = ApiService.fetchData("https://api.potterdb.com/v1/spells");
                List<Spells> spells = parseJsonData(jsonData);
                Activity activity = getActivity();
                if (isAdded() && activity != null) {
                requireActivity().runOnUiThread(() -> {
                    spellsList.clear(); // Clear the list before updating
                    spellsList.addAll(spells); // Update with the fetched data
                    spellsAdapter.notifyDataSetChanged(); // Notify the adapter
                });}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private List<Spells> parseJsonData(String jsonData) {
        List<Spells> spells = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray data = jsonObject.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject potionObject = data.getJSONObject(i);
                String id = potionObject.getString("id");
                String name = potionObject.getJSONObject("attributes").getString("name");
                String imageUrl = potionObject.getJSONObject("attributes").optString("image");
                String incantation  = potionObject.getJSONObject("attributes").optString("incantation");
                String category = potionObject.getJSONObject("attributes").optString("category");
                String effect = potionObject.getJSONObject("attributes").optString("effect");
                String creator = potionObject.getJSONObject("attributes").optString("creator");


                spells.add(new Spells(id, name, imageUrl, incantation, category, effect, creator));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return spells;
    }
}
