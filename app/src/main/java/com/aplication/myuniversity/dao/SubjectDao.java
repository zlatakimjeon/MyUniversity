package com.aplication.myuniversity.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.aplication.myuniversity.entry.SubjectEntry;
import com.aplication.myuniversity.model.Subject;

import java.util.List;

@Dao
public interface SubjectDao {
    @Query("Select * from " + SubjectEntry.TABLE_NAME)
    List<Subject> getSubjects();
    /*
    @Query("Select * from " + SubjectEntry.TABLE_NAME
            + " where " + SubjectEntry.COLUMN_PARENT_ID + " = :parentId")
    LiveData<List<Subject>> getLiveSubjects(long parentId);*/

    @Query("Select * from " + SubjectEntry.TABLE_NAME + " where " + SubjectEntry._ID + " = :id")
    Subject get(long id); // 0

    @Insert
    long insert(Subject subject);

    @Update(entity = Subject.class, onConflict = OnConflictStrategy.REPLACE)
    void update(Subject subject);

    @Delete
    void delete(Subject subject);

    /*@Query("Delete from " + SubjectEntry.TABLE_NAME + " where " + SubjectEntry.COLUMN_PARENT_ID + " = :parentId")
    void delete(long parentId);*/

}

