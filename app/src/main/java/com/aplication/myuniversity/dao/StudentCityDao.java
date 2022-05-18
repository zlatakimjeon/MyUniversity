package com.aplication.myuniversity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.aplication.myuniversity.entry.StudentCityEntry;
import com.aplication.myuniversity.model.StudentCity;

import java.util.List;

@Dao
public interface StudentCityDao {
    @Query("Select * from " + StudentCityEntry.TABLE_NAME)
    List<StudentCity> getStudentCities();
    /*
    @Query("Select * from " + StudentCityEntry.TABLE_NAME
            + " where " + StudentCityEntry.COLUMN_PARENT_ID + " = :parentId")
    LiveData<List<StudentCity>> getLiveStudentCitys(long parentId);*/

    @Query("Select * from " + StudentCityEntry.TABLE_NAME + " where " + StudentCityEntry._ID + " = :id")
    StudentCity get(long id);

    @Insert
    long insert(StudentCity studentcity);

    @Update(entity = StudentCity.class, onConflict = OnConflictStrategy.REPLACE)
    void update(StudentCity studentcity);

    @Delete
    void delete(StudentCity studentcity);
}
