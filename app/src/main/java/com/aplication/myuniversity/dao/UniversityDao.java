package com.aplication.myuniversity.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.aplication.myuniversity.entry.UniversityEntry;
import com.aplication.myuniversity.model.University;

import java.util.List;

@Dao
public interface UniversityDao {
    @Query("Select * from " + UniversityEntry.TABLE_NAME)
    List<University> getUniversities();

    @Query("Select * from " + UniversityEntry.TABLE_NAME + " where " + UniversityEntry._ID + " = :id")
    University get(long id);

    @Insert
    long insert(University university);

    @Update(entity = University.class, onConflict = OnConflictStrategy.REPLACE)
    void update(University university);

    @Delete
    void delete(University university);

    @Query("Select * from " + UniversityEntry.TABLE_NAME
            + " where " + UniversityEntry.CITY_ID + " = :cityId and " + UniversityEntry.MINUMUM_SCORES + " < :scores")
    List<University> getUniversities(int cityId, int scores);
}
