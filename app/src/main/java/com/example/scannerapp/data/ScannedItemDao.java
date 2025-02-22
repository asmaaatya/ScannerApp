package com.example.scannerapp.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ScannedItemDao {
        @Insert
        void insert(ScannedItemInfo item);

        @Query("SELECT * FROM scanned_items ORDER BY id DESC")
        List<ScannedItemInfo> getAllScannedItems();

        @Query("SELECT * FROM scanned_items WHERE isFavorite = 1")
        List<ScannedItemInfo> getFavorites();

        @Update
        void update(ScannedItemInfo item);
}
