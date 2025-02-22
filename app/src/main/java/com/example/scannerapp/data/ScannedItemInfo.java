package com.example.scannerapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "scanned_items")
public class ScannedItemInfo {
        @PrimaryKey(autoGenerate = true)
        private int id;
        private String name;
        private boolean isFavorite;

        public ScannedItemInfo(String name, boolean isFavorite) {
            this.name = name;
            this.isFavorite = isFavorite;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public boolean isFavorite() { return isFavorite; }
        public void setFavorite(boolean favorite) { isFavorite = favorite; }


}
