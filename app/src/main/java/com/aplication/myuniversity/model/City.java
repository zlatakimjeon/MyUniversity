package com.aplication.myuniversity.model;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.aplication.myuniversity.entry.CityEntry;

@Entity(
        tableName = CityEntry.TABLE_NAME,
        /* foreignKeys = {
                 @ForeignKey(entity = Purpose.class,
                         parentColumns = BaseColumns._ID,
                         childColumns = NoteEntry.COLUMN_PARENT_ID,
                         onDelete = ForeignKey.CASCADE)
         },*/
        indices = { @Index(value = {BaseColumns._ID}, unique = true) }
)

public class City {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    protected int id;
    @ColumnInfo(name = CityEntry.TITLE)
    private String title; // name

    public City(String title) {
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
