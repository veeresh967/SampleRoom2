package xyz.net7.sampleroom.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface DataDAO {

    //Insert one item
    @Insert(onConflict = IGNORE)
    void insertItem(DataItem item);

    // Delete one item
    @Delete
    void deleteItem(DataItem person);

    //Delete one item by id
    @Query("DELETE FROM dataitem WHERE id = :itemId")
    void deleteByItemId(long itemId);


    //Delete one item by id
    @Update
    void updateByItemId(long itemId);

    //Get all items
    @Query("SELECT * FROM DataItem")
    LiveData<List<DataItem>> getAllData();

    //Delete All
    @Query("DELETE FROM DataItem")
    void deleteAll();
}
