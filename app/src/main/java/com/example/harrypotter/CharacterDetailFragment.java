package com.example.harrypotter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class CharacterDetailFragment extends Fragment {

    private static final String ARG_CHARACTER = "character";

    private Character character;

    // Metodă statică pentru a crea o instanță a fragmentului cu un personaj specificat

    public static CharacterDetailFragment newInstance(Character character) {
        CharacterDetailFragment fragment = new CharacterDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHARACTER, character);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obține personajul din argumentele fragmentului

        if (getArguments() != null) {
            character = (Character) getArguments().getSerializable(ARG_CHARACTER);
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout-ul pentru fragment

        View rootView = inflater.inflate(R.layout.fragment_character_detail, container, false);
        // Obține argumentele pentru personaj

        Bundle args = getArguments();
        if (args != null) {
            // Extrage datele personajului din argumente

            String characterId = args.getString("id");
            String characterName = args.getString("name");
            String characterImageUrl = args.getString("img");
            String characterDied = args.getString("died");
            String characterBorn = args.getString("born");
            String characterNationality = args.getString("nationality");
            String characterGender = args.getString("gender");
            String characterHouse = args.getString("house");
            String characterSpecies = args.getString("species");


            // Populează elementele UI cu datele personajului
            TextView nameTextView = rootView.findViewById(R.id.name);
            ImageView imageView = rootView.findViewById(R.id.recipe_img);
            TextView diedTextView = rootView.findViewById(R.id.died);
            TextView characterBornTextView = rootView.findViewById(R.id.born);
            TextView characterNationalityTextView = rootView.findViewById(R.id.nationality);
            TextView characterGenderTextView = rootView.findViewById(R.id.gender);
            TextView characterHouseTextView = rootView.findViewById(R.id.housee);
            TextView characterSpeciesTextView = rootView.findViewById(R.id.species);


            nameTextView.setText(characterName);
            Picasso.get().load(characterImageUrl).into(imageView);
            diedTextView.setText(characterDied);
            characterBornTextView.setText(characterBorn);
            characterNationalityTextView.setText(characterNationality);
            characterGenderTextView.setText(characterGender);
            characterHouseTextView.setText(characterHouse);
            characterSpeciesTextView.setText(characterSpecies);
        }

        return rootView;
    }
}
