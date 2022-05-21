package com.example.recognitiontext;

import androidx.room.ColumnInfo;

import java.io.Serializable;

public class ItemNote implements Serializable {
    @ColumnInfo(name = "text")
    final String name;

    public String getName() {
        if (name != null) return name;
        else return "no text on this picture";
    }

    public ItemNote(String name) {
        this.name = name;
    }

    public TextDb toTextdb(){
        return new TextDb(name);
    }
}
