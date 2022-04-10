package com.example.recognitiontext;

import java.io.Serializable;

public class ItemNote implements Serializable {
    final String name;

    public String getName() {
        return name;
    }

    public ItemNote(String name) {
        this.name = name;
    }
}
