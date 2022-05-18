package com.aplication.myuniversity.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.aplication.myuniversity.entry.StudentEntry;
import com.aplication.myuniversity.model.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("Select * from " + StudentEntry.TABLE_NAME)
    List<Student> getStudents();
    /*
    @Query("Select * from " + StudentEntry.TABLE_NAME
            + " where " + StudentEntry.COLUMN_PARENT_ID + " = :parentId")
    LiveData<List<Student>> getLiveStudents(long parentId);*/

    @Query("Select * from " + StudentEntry.TABLE_NAME + " where " + StudentEntry._ID + " = :id")
    Student get(long id); // 0

    @Insert
    long insert(Student student);

    @Update(entity = Student.class, onConflict = OnConflictStrategy.REPLACE)
    void update(Student student);

    @Delete
    void delete(Student student);

    @Query("Select * from " + StudentEntry.TABLE_NAME + " where " + StudentEntry.LOGIN + " like :login")
    Student getStudentByLogin(String login);

    /*@Query("Delete from " + StudentEntry.TABLE_NAME + " where " + StudentEntry.COLUMN_PARENT_ID + " = :parentId")
    void delete(long parentId);*/

}

