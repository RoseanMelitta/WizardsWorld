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

public class PotionAdapter extends RecyclerView.Adapter<PotionAdapter.PotionViewHolder> {
    private Context mContext;
    private List<Potion> potions;

    public void updateDataPotions(List<Potion> newData) {
        potions.clear();
        potions.addAll(newData);
        notifyDataSetChanged();
    }

    public PotionAdapter(List<Potion> potions,Context context) {
        this.potions = new ArrayList<>(potions);
        this.mContext = context;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener mListener;

    public void setOnItemClickListener(PotionAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull PotionViewHolder holder, int position) {
        Potion potion = potions.get(position);
        holder.potionNameTextView.setText(potion.getId());

        Glide.with(holder.itemView.getContext())
                .load(potion.getImageUrl())
                .placeholder(R.drawable.ic_no_picture) // Placeholder image while loading
                .error(R.drawable.ic_no_picture) // Error image to show if loading fails
                .into(holder.potionImageView);

        Potion mData = potions.get(holder.getAdapterPosition());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PotionAdapter", "Potion clicked at position: " + holder.getAdapterPosition());


                Intent intent = new Intent(mContext, PotionsDetailActivity.class);
                intent.putExtra("id", mData.getId());
                intent.putExtra("name", mData.getName());
                intent.putExtra("img", mData.getImageUrl());
                intent.putExtra("effect", mData.getEffect());
                intent.putExtra("side_effects", mData.getSide_effects());
                intent.putExtra("time", mData.getTime());
                intent.putExtra("difficulty", mData.getDifficulty());


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
        return potions.size();
    }

    @NonNull
    @Override
    public PotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potions, parent, false);
        return new PotionViewHolder(view, mListener);
    }
    public Potion getItem(int position) {
        if (position >= 0 && position < potions.size()) {
            return potions.get(position);
        }
        return null;
    }




    static class PotionViewHolder extends RecyclerView.ViewHolder {
        TextView potionNameTextView;
        ImageView potionImageView;

         PotionViewHolder(@NonNull View itemView, OnItemClickListener mListener) {
            super(itemView);
            potionNameTextView = itemView.findViewById(R.id.potionNameTextView);
            potionImageView = itemView.findViewById(R.id.potionImageView);
        }
    }
}
