package com.example.scannerapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {ScannedItemInfo.class}, version = 1)
public abstract class ScannerDatabase extends RoomDatabase {
        private static ScannerDatabase instance;

        public abstract ScannedItemDao scannedItemDao();

        public static synchronized ScannerDatabase getInstance(Context context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        ScannerDatabase.class, "scanner_db").fallbackToDestructiveMigration().build();
            }
            return instance;
        }
    }

