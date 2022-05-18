package com.aplication.myuniversity.model;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.aplication.myuniversity.entry.StudentSubjectEntry;

@Entity(
        tableName = StudentSubjectEntry.TABLE_NAME,
        /* foreignKeys = {
                 @ForeignKey(entity = Purpose.class,
                         parentColumns = BaseColumns._ID,
                         childColumns = NoteEntry.COLUMN_PARENT_ID,
                         onDelete = ForeignKey.CASCADE)
         },*/
        indices = { @Index(value = {BaseColumns._ID}, unique = true) }
)

public class StudentSubject {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    protected int id;
    @ColumnInfo(name = StudentSubjectEntry.PARENT_STUDENT)
    private Long parentStudent;
    @ColumnInfo(name = StudentSubjectEntry.SUBJECT_ID)
    private Long subjectId;
    @ColumnInfo(name = StudentSubjectEntry.SCORES)
    private Integer scores;

    public StudentSubject() {
    }

    @Ignore
    public StudentSubject(Long parentStudent, Long subjectId, int scores) {
        this.parentStudent = parentStudent;
        this.subjectId = subjectId;
        this.scores = scores;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getParentStudent() {
        return parentStudent;
    }

    public void setParentStudent(Long parentStudent) {
        this.parentStudent = parentStudent;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getScores() {
        return scores;
    }

    public void setScores(Integer scores) {
        this.scores = scores;
    }
}