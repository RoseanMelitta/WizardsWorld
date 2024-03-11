package com.example.harrypotter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SpellsAdapter extends RecyclerView.Adapter<SpellsAdapter.SpellsViewHolder> {
    private Context mContext;

    private List<Spells> spells;
    public void updateDataSpells(List<Spells> newData) {

        spells.clear();
        spells.addAll(newData);
        notifyDataSetChanged();
    }
    public SpellsAdapter(List<Spells> spells,Context context) {
        this.spells = new ArrayList<>(spells);
        this.mContext = context;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener mListener;

    public void setOnItemClickListener(SpellsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }



    @Override
    public void onBindViewHolder(@NonNull SpellsViewHolder holder, int position) {
        Spells spell = spells.get(position);
        holder.spellsNameTextView.setText(spell.getId());

        Glide.with(holder.itemView.getContext())
                .load(spell.getImageUrl())
                .placeholder(R.drawable.ic_no_picture)
                .into(holder.spellsImageView);

Spells mData = spells.get(holder.getAdapterPosition());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SpellsAdapter", "Spell clicked at position: " + holder.getAdapterPosition());


                Intent intent = new Intent(mContext, SpellsDetailActivity.class);
                intent.putExtra("id", mData.getId());
                intent.putExtra("name", mData.getName());
                intent.putExtra("img", mData.getImageUrl());
                intent.putExtra("incantation", mData.getIncantation());
                intent.putExtra("category", mData.getCategory());
                intent.putExtra("effect", mData.getEffect());
                intent.putExtra("creator", mData.getCreator());


                mContext.startActivity(intent);
                // Call onItemClick with the clicked position
                if (mListener != null) {
                    mListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return spells.size();
    }
    @NonNull
    @Override
    public SpellsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spells, parent, false);
        return new SpellsViewHolder(view, mListener);
    }
    public Spells getItem(int position) {
        if (position >= 0 && position < spells.size()) {
            return spells.get(position);
        }
        return null;
    }
    static class SpellsViewHolder extends RecyclerView.ViewHolder {
        TextView spellsNameTextView;
        ImageView spellsImageView;

        SpellsViewHolder(@NonNull View itemView, OnItemClickListener mListener) {
            super(itemView);
            spellsNameTextView = itemView.findViewById(R.id.spellsNameTextView);
            spellsImageView = itemView.findViewById(R.id.spellsImageView);
        }
    }

}
