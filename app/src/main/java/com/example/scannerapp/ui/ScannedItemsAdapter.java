package com.example.scannerapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scannerapp.R;
import com.example.scannerapp.data.ScannedItemInfo;
import com.example.scannerapp.data.ScannerDatabase;

import java.util.List;

public class ScannedItemsAdapter extends RecyclerView.Adapter<ScannedItemsAdapter.ViewHolder> {
        private final List<ScannedItemInfo> scannedItems;

        public ScannedItemsAdapter(List<ScannedItemInfo> scannedItems) {
            this.scannedItems = scannedItems;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scanned_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ScannedItemInfo item = scannedItems.get(position);
            holder.textView.setText(scannedItems.get(position).getName());
            holder.ivFavorite.setImageResource(item.isFavorite() ? R.drawable.ic_favorite : R.drawable.baseline_favorite_border_24);
            holder.ivFavorite.setOnClickListener(v -> {
                boolean newFavoriteState = !item.isFavorite();
                item.setFavorite(newFavoriteState);
                holder.ivFavorite.setImageResource(newFavoriteState ? R.drawable.ic_favorite : R.drawable.baseline_favorite_border_24);
                new Thread(() -> ScannerDatabase.getInstance(holder.itemView.getContext()).scannedItemDao().update(item)).start();
            });


        }

        @Override
        public int getItemCount() {
            return scannedItems.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            ImageView ivFavorite;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.name);
                ivFavorite = itemView.findViewById(R.id.ivFavorite);
            }
        }
    }


