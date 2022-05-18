package com.aplication.myuniversity.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aplication.myuniversity.dao.CityDao;
import com.aplication.myuniversity.dao.StudentCityDao;
import com.aplication.myuniversity.dao.StudentDao;
import com.aplication.myuniversity.dao.StudentSubjectDao;
import com.aplication.myuniversity.dao.SubjectDao;
import com.aplication.myuniversity.dao.UniversityDao;
import com.aplication.myuniversity.model.City;
import com.aplication.myuniversity.model.Student;
import com.aplication.myuniversity.model.StudentCity;
import com.aplication.myuniversity.model.StudentSubject;
import com.aplication.myuniversity.model.Subject;
import com.aplication.myuniversity.model.University;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Student.class, Subject.class, City.class, StudentCity.class, StudentSubject.class, University.class},
        exportSchema = false, version = 1)

public abstract class RoomDB extends RoomDatabase {
    public static final String DATABASE_NAME = "main_db";
    private static RoomDB INSTANCE = null;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService Executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RoomDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDB.class, DATABASE_NAME)
                            .createFromAsset("database/main_db.db")
                           // .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                    //.enableMultiInstanceInvalidation()
                }
            }
        }
        return INSTANCE;
    }

    public abstract StudentDao studentDao();

    public abstract SubjectDao subjectDao();

    public abstract CityDao cityDao();

    public abstract StudentCityDao studentCityDao();

    public abstract StudentSubjectDao studentSubjectDao();

    public abstract UniversityDao universityDao();
}
