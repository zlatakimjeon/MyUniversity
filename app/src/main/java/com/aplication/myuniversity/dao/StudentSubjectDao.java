package com.aplication.myuniversity.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.aplication.myuniversity.entry.StudentSubjectEntry;
import com.aplication.myuniversity.model.StudentSubject;

import java.util.List;

@Dao
public interface StudentSubjectDao {
    @Query("Select * from " + StudentSubjectEntry.TABLE_NAME)
    List<StudentSubject> getStudentSubjects();
    /*
    @Query("Select * from " + StudentSubjectEntry.TABLE_NAME
            + " where " + StudentSubjectEntry.COLUMN_PARENT_ID + " = :parentId")
    LiveData<List<StudentSubject>> getLiveStudentSubjects(long parentId);*/

    @Query("Select * from " + StudentSubjectEntry.TABLE_NAME + " where " + StudentSubjectEntry._ID + " = :id")
    StudentSubject get(long id);

    @Insert
    long insert(StudentSubject studentSubject);

    @Update(entity = StudentSubject.class, onConflict = OnConflictStrategy.REPLACE)
    void update(StudentSubject studentSubject);

    @Delete
    void delete(StudentSubject studentSubject);

    @Query("Select * from " + StudentSubjectEntry.TABLE_NAME + " where " + StudentSubjectEntry.PARENT_STUDENT + " = :parentStudent" + " limit 1")
    StudentSubject getFirstSubject(long parentStudent);

    @Query("Select * from " + StudentSubjectEntry.TABLE_NAME + " where " + StudentSubjectEntry.PARENT_STUDENT + " = :parentStudent" + " limit 1 offset " + ":offset")
    StudentSubject getSubject(long parentStudent, long offset);
}
