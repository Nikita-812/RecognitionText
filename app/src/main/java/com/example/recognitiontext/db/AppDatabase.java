package com.example.recognitiontext.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.recognitiontext.db.TextDao;
import com.example.recognitiontext.db.TextDb;

@Database(entities = {TextDb.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TextDao textDao();
}
