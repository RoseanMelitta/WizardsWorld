package com.example.harrypotter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class PotionsDetailFragment extends Fragment {

    private static final String ARG_POTIONS = "potions";

    private Potion potion;

    public static PotionsDetailFragment newInstance(Potion potion) {
        PotionsDetailFragment fragment = new PotionsDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_POTIONS, potion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            potion = (Potion) getArguments().getSerializable(ARG_POTIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_potions_detail, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String potionsId = args.getString("id");
            String potionsName = args.getString("name");
            String potionsImageUrl = args.getString("img");
            String potionsEffect = args.getString("effect");
            String potionsSideEffect = args.getString("side_effects");
            String potionsTime = args.getString("time");
            String potionsDifficulty = args.getString("difficulty");


            // Populate UI elements with character data

            TextView nameTextView = rootView.findViewById(R.id.nameSpells);
            ImageView imageView = rootView.findViewById(R.id.recipe_img);
            TextView incantationTextView = rootView.findViewById(R.id.effect);
            TextView spellsCategoryTextView = rootView.findViewById(R.id.sideEffect);
            TextView spellsEffectTextView = rootView.findViewById(R.id.time);
            TextView spellsCreatorTextView = rootView.findViewById(R.id.difficulty);


            nameTextView.setText(potionsId);
            Picasso.get().load(potionsImageUrl).into(imageView);
            incantationTextView.setText(potionsEffect);
            spellsCategoryTextView.setText(potionsSideEffect);
            spellsEffectTextView.setText(potionsTime);
            spellsCreatorTextView.setText(potionsDifficulty);

        }
            return rootView;
        }
    }
