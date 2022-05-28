package com.example.recognitiontext.db;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class TextDb implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public final String text;

    public TextDb(Long id,@NonNull String text) {
        this.id = id;
        this.text = text;

    }

    @Ignore
    public TextDb(String text) {
        this(null, text);
    }
}

