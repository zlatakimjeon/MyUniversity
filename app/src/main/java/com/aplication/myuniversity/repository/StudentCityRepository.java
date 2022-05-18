package com.aplication.myuniversity.repository;

import android.content.Context;

import com.aplication.myuniversity.dao.StudentCityDao;
import com.aplication.myuniversity.model.StudentCity;
import com.aplication.myuniversity.room.RoomDB;

import java.util.List;

public class StudentCityRepository {
    private final StudentCityDao studentCityDao;

    public StudentCityRepository(Context context) {
        RoomDB db = RoomDB.getInstance(context);
        this.studentCityDao = (StudentCityDao) db.studentCityDao();
    }
    public List<StudentCity> getStudentCitys() {
        return studentCityDao.getStudentCities();
    }

   /* public LiveData<List<StudentCity>> getLiveStudentCitys(long parentId) {
        return studentcityDao.getLiveStudentCitys(parentId);
    }*/

    public long insert(StudentCity note) {
        return studentCityDao.insert(note);
    }

    public void delete(StudentCity note) {
        RoomDB.Executor.execute(() -> studentCityDao.delete(note));
    }

   /* public void delete(long parentId) {
        RoomDB.Executor.execute(() -> studentcityDao.delete(parentId));
    }*/

    /* public long getCount(long parentId) {
         return studentcityDao.getCount(parentId);
     }
 
     public LiveData<Long> getLiveCount(long parentId) {
         return studentcityDao.getLiveCount(parentId);
     }
 */
    public StudentCity get(long id) {
        return studentCityDao.get(id);
    }

    public void update(StudentCity studentcity) {
        studentCityDao.update(studentcity);
    }
}
