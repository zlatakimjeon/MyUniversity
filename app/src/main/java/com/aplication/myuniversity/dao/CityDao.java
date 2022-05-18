package com.aplication.myuniversity.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.aplication.myuniversity.entry.CityEntry;
import com.aplication.myuniversity.model.City;

import java.util.List;

@Dao
public interface CityDao {
    @Query("Select * from " + CityEntry.TABLE_NAME)
    List<City> getCities();
    /*
    @Query("Select * from " + CityEntry.TABLE_NAME
            + " where " + CityEntry.COLUMN_PARENT_ID + " = :parentId")
    LiveData<List<City>> getLiveCitys(long parentId);*/

    @Query("Select * from " + CityEntry.TABLE_NAME + " where " + CityEntry._ID + " = :id")
    City get(long id);

    @Insert
    long insert(City city);

    @Update(entity = City.class, onConflict = OnConflictStrategy.REPLACE)
    void update(City city);

    @Delete
    void delete(City city);
}

