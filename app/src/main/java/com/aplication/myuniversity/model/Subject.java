package com.aplication.myuniversity.model;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.aplication.myuniversity.entry.StudentEntry;
import com.aplication.myuniversity.entry.SubjectEntry;

@Entity(
        tableName = SubjectEntry.TABLE_NAME,
        indices = { @Index(value = {BaseColumns._ID}, unique = true) }
)

public class Subject {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    protected int id;
    @ColumnInfo(name = SubjectEntry.TITLE)
    private String title; // name

    public Subject(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
