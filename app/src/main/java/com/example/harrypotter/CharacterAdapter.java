package com.example.harrypotter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private Context mContext;

    private List<Character> characters;

    public void updateData(List<Character> newData) {

        characters.clear();
        characters.addAll(newData);
        notifyDataSetChanged();
    }
    public CharacterAdapter(List<Character> characters,Context context) {
        this.characters = new ArrayList<>(characters);

        this.mContext = context;

    }
    //region interfata gestionare click
    // Interfață pentru gestionarea evenimentelor de clic pe elementele RecyclerView

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        // Obține informații despre un personaj de la poziția dată


        Character character = characters.get(position);

        // Setează numele personajului în TextView

        holder.characterNameTextView.setText(character.getName());

        // Folosește biblioteca Glide pentru a încărca imaginea personajului în ImageView

        Glide.with(holder.itemView.getContext())
                .load(character.getImageUrl())
                .placeholder(R.drawable.ic_no_picture)
                .into(holder.characterImageView);

        Character mData = characters.get(holder.getAdapterPosition());

        // Setează un click listener pe elementul RecyclerView

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CharacterAdapter", "Item clicked at position: " + holder.getAdapterPosition());


                Intent intent = new Intent(mContext, CharacterDetailActivity.class);
                intent.putExtra("id", mData.getId());
                intent.putExtra("name", mData.getName());
                intent.putExtra("img", mData.getImageUrl());
                intent.putExtra("died", mData.getDied());
                intent.putExtra("born", mData.getBorn());
                intent.putExtra("species", mData.getSpecies());
                intent.putExtra("nationality", mData.getNationality());
                intent.putExtra("house", mData.getHouse());
                intent.putExtra("gender", mData.getGender());


                mContext.startActivity(intent);
                // Apelul metodei onItemClick dacă un listener este setat
                if (mListener != null) {
                    mListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }
    // Returnează numărul de elemente din listă

    @Override
    public int getItemCount() {
        return characters.size();
    }
    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creează un nou ViewHolder pentru elementul din listă

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character, parent, false);

        return new CharacterViewHolder(view, mListener);

    }
    public Character getItem(int position) {
        if (position >= 0 && position < characters.size()) {
            return characters.get(position);
        }
        return null;
    }
    //endregion
    static class CharacterViewHolder extends RecyclerView.ViewHolder {
        TextView characterNameTextView;
        ImageView characterImageView;

        CharacterViewHolder(@NonNull View itemView, OnItemClickListener mListener) {
            super(itemView);
            characterNameTextView = itemView.findViewById(R.id.characterNameTextView);
            characterImageView = itemView.findViewById(R.id.characterImageView);
        }

    }
}
