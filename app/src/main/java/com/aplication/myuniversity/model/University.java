package com.aplication.myuniversity.model;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.aplication.myuniversity.entry.StudentCityEntry;
import com.aplication.myuniversity.entry.UniversityEntry;

@Entity(
        tableName = UniversityEntry.TABLE_NAME,
        indices = { @Index(value = {BaseColumns._ID}, unique = true) }
)

public class University {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    protected int id;
    @ColumnInfo(name = UniversityEntry.TITLE)
    private String title;
    @ColumnInfo(name = UniversityEntry.CITY_ID)
    private Integer cityId;
    @ColumnInfo(name = UniversityEntry.MINUMUM_SCORES)
    private Integer minimumScores;
    @ColumnInfo(name = UniversityEntry.URL)
    private String URL;


    public University() {
       // this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getMinimumScores() {
        return minimumScores;
    }

    public void setMinimumScores(Integer minimumScores) {
        this.minimumScores = minimumScores;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
