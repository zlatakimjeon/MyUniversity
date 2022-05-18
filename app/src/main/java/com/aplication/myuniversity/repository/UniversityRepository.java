package com.aplication.myuniversity.repository;

import android.content.Context;

import com.aplication.myuniversity.dao.UniversityDao;
import com.aplication.myuniversity.model.University;
import com.aplication.myuniversity.room.RoomDB;

import java.util.List;

public class UniversityRepository {
    private final UniversityDao universityDao;

    public UniversityRepository(Context context) {
        RoomDB db = RoomDB.getInstance(context);
        this.universityDao = db.universityDao();
    }

    public List<University> getUniversities() {
        return universityDao.getUniversities();
    }

    public List<University> getUniversities(int cityId, int scores) {
        return universityDao.getUniversities(cityId, scores);
    }

    public long insert(University note) {
        return universityDao.insert(note);
    }

    public void delete(University note) {
        RoomDB.Executor.execute(() -> universityDao.delete(note));
    }

    public University get(long id) {
        return universityDao.get(id);
    }

    public void update(University university) {
        universityDao.update(university);
    }
}
