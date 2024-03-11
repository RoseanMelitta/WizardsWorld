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

public class SpellsDetailFragment extends Fragment {

    private static final String ARG_SPELLS = "spells";

    private Spells spells;

    public static SpellsDetailFragment newInstance(Spells spells) {
        SpellsDetailFragment fragment = new SpellsDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SPELLS, spells);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            spells = (Spells) getArguments().getSerializable(ARG_SPELLS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_spells_detail, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String spellsId = args.getString("id");
            String spellsName = args.getString("name");
            String spellsImageUrl = args.getString("img");
            String spellsIncantation = args.getString("incantation");
            String spellsCategory = args.getString("category");
            String spellsEffect = args.getString("effect");
            String spellsCreator = args.getString("creator");


            // Populate UI elements with character data

            TextView nameTextView = rootView.findViewById(R.id.nameSpells);
            ImageView imageView = rootView.findViewById(R.id.recipe_img);
            TextView incantationTextView = rootView.findViewById(R.id.incantation);
            TextView spellsCategoryTextView = rootView.findViewById(R.id.category);
            TextView spellsEffectTextView = rootView.findViewById(R.id.effect);
            TextView spellsCreatorTextView = rootView.findViewById(R.id.creator);


            nameTextView.setText(spellsId);
            Picasso.get().load(spellsImageUrl).into(imageView);
            incantationTextView.setText(spellsIncantation);
            spellsCategoryTextView.setText(spellsCategory);
            spellsEffectTextView.setText(spellsEffect);
            spellsCreatorTextView.setText(spellsCreator);

        }
            return rootView;
        }
    }
