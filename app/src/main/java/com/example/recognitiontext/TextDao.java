package com.example.recognitiontext;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TextDao {
    @Query("SELECT * FROM TextDb")
    LiveData<List<TextDb>> getAllToItemNote();

    @Query("SELECT * FROM TextDb WHERE id == :id")
    LiveData<TextDb> getStringForNote(long id);// здесь должен был быть запрос строки по id


    @Insert
    long insert(TextDb textDb);

    @Update
    void update(TextDb textDb);

    @Delete
    void delete(TextDb textDb);

}
