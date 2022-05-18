package com.aplication.myuniversity.repository;

import android.content.Context;

import com.aplication.myuniversity.dao.CityDao;
import com.aplication.myuniversity.model.City;
import com.aplication.myuniversity.room.RoomDB;

import java.util.List;

public class CityRepository {
    private final CityDao cityDao;

    public CityRepository(Context context) {
        RoomDB db = RoomDB.getInstance(context);
        this.cityDao = db.cityDao();
    }

    public List<City> getCities() {
        return cityDao.getCities();
    }

    public long insert(City note) {
        return cityDao.insert(note);
    }

    public void delete(City note) {
        RoomDB.Executor.execute(() -> cityDao.delete(note));
    }

    public City get(long id) {
        return cityDao.get(id);
    }

    public void update(City city) {
        cityDao.update(city);
    }
}
