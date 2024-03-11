package com.example.harrypotter;

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

public class PotionsFragment extends Fragment {
    private SearchView searchView;
    private TextView emptyView;
    private PotionAdapter potionAdapter;
    private List<Potion> potionList = new ArrayList<>(); // Declare and initialize the list
    private RecyclerView recyclerViewPotions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_potions, container, false);
        recyclerViewPotions = rootView.findViewById(R.id.recyclerViewPotions);

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
        recyclerViewPotions.setLayoutManager(layoutManager);

        potionAdapter = new PotionAdapter(new ArrayList<>(),requireContext());
        recyclerViewPotions.setAdapter(potionAdapter);
        fetchAndDisplayPotions();


        return rootView;
    }
    private void performSearch(String query) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.fetchAndDisplayPotionsByNameInFragment(query);
        }
    }
    private void fetchAndDisplayPotions() {
        new Thread(() -> {
            try {
                String jsonData = ApiService.fetchData("https://api.potterdb.com/v1/potions");
                List<Potion> potions = parseJsonData(jsonData);

                requireActivity().runOnUiThread(() -> {
                    potionList.clear(); // Clear the list before updating
                    potionList.addAll(potions); // Update with the fetched data
                    potionAdapter.notifyDataSetChanged(); // Notify the adapter
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private List<Potion> parseJsonData(String jsonData) {
        List<Potion> potions = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray data = jsonObject.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject potionObject = data.getJSONObject(i);
                String id = potionObject.getString("id");
                String name = potionObject.getJSONObject("attributes").getString("name");
                String imageUrl = potionObject.getJSONObject("attributes").optString("image");
                String effect = potionObject.getJSONObject("attributes").optString("effect");
                String side_effect = potionObject.getJSONObject("attributes").optString("side_effects");
                String time = potionObject.getJSONObject("attributes").optString("time");
                String difficulty = potionObject.getJSONObject("attributes").optString("difficulty");


                potions.add(new Potion(id, name, imageUrl, effect, side_effect, time, difficulty));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return potions;
    }
}
