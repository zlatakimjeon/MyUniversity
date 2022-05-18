package com.aplication.myuniversity.model;

import android.net.Uri;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.aplication.myuniversity.converters.URIConverter;
import com.aplication.myuniversity.entry.StudentEntry;

@Entity(
        tableName = StudentEntry.TABLE_NAME,
        indices = { @Index(value = {BaseColumns._ID}, unique = true) }
)
public class Student {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = BaseColumns._ID)
        protected int id;
        @ColumnInfo(name = StudentEntry.NAME)
        private String name; // name
        @ColumnInfo(name = StudentEntry.SURNAME)
        private String surname;  // surname
        @ColumnInfo(name = StudentEntry.LOGIN)
        private String login;
        @ColumnInfo(name = StudentEntry.PASSWORD)
        private String password;
        @TypeConverters(URIConverter.class)
        @ColumnInfo(name = StudentEntry.URI)
        private Uri uri;
        @ColumnInfo(name = StudentEntry.CITY_ID)
        private Integer cityId;
        @ColumnInfo(name = StudentEntry.SCORES)
        private Integer scores;

        public Student() {
                this.name = "";
                this.surname = "";
                this.login = "";
                this.password = "";
                this.uri = Uri.parse("");
                this.cityId = 0;
                this.scores = 300;
        }

        public Student(String name, String surname, String login, String password, Uri uri, int cityId, int scores) {
                this.name = name;
                this.surname = surname;
                this.login = login;
                this.password = password;
                this.uri = uri;
                this.cityId = cityId;
                this.scores = scores;
        }

        @Ignore
        public Student(int id, String name, String surname, String login, String password) {
                this.id = id;
                this.name = name;
                this.surname = surname;
                this.login = login;
                this.password = password;
        }

        @Ignore
        public Student(Student student) {
                this.id = student.id;
                this.name = student.name;
                this.surname = student.surname;
                this.login = student.login;
                this.password = student.password;
                this.uri = student.uri;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getSurname() {
                return surname;
        }

        public void setSurname(String surname) {
                this.surname = surname;
        }

        public String getLogin() {
                return login;
        }

        public void setLogin(String login) {
                this.login = login;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public Uri getUri() {
                return uri;
        }

        public void setUri(Uri uri) {
                this.uri = uri;
        }

        public Integer getCityId() {
                return cityId;
        }

        public void setCityId(Integer cityId) {
                this.cityId = cityId;
        }

        public Integer getScores() {
                return scores;
        }

        public void setScores(Integer scores) {
                this.scores = scores;
        }
}
