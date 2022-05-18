package com.aplication.myuniversity.model;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.aplication.myuniversity.entry.StudentEntry;
import com.aplication.myuniversity.entry.StudentCityEntry;

@Entity(
        tableName = StudentCityEntry.TABLE_NAME,
        /* foreignKeys = {
                 @ForeignKey(entity = Purpose.class,
                         parentColumns = BaseColumns._ID,
                         childColumns = NoteEntry.COLUMN_PARENT_ID,
                         onDelete = ForeignKey.CASCADE)
         },*/
        indices = { @Index(value = {BaseColumns._ID}, unique = true) }
)

public class StudentCity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    protected int id;
    @ColumnInfo(name = StudentCityEntry.PARENT_STUDENT)
    private Integer parentStudent;
    @ColumnInfo(name = StudentCityEntry.CITY_ID)
    private Integer cityId;

    public StudentCity() {
    }

    public int getId() {
        return id;
    }

    public Integer getParentStudent() {
        return parentStudent;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParentStudent(Integer parentStudent) {
        this.parentStudent = parentStudent;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
}

