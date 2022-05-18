package com.aplication.myuniversity.repository;

import android.content.Context;

import com.aplication.myuniversity.dao.SubjectDao;
import com.aplication.myuniversity.model.Subject;
import com.aplication.myuniversity.room.RoomDB;

import java.util.List;

public class SubjectRepository {
    private final SubjectDao subjectDao;

    public SubjectRepository(Context context) {
        RoomDB db = RoomDB.getInstance(context);
        this.subjectDao = db.subjectDao();
    }

    public List<Subject> getSubjects() {
        return subjectDao.getSubjects();
    }

   /* public LiveData<List<Subject>> getLiveSubjects(long parentId) {
        return subjectDao.getLiveSubjects(parentId);
    }*/

    public long insert(Subject note) {
        return subjectDao.insert(note);
    }

    public void delete(Subject note) {
        RoomDB.Executor.execute(() -> subjectDao.delete(note));
    }

   /* public void delete(long parentId) {
        RoomDB.Executor.execute(() -> subjectDao.delete(parentId));
    }*/

    /* public long getCount(long parentId) {
         return subjectDao.getCount(parentId);
     }
 
     public LiveData<Long> getLiveCount(long parentId) {
         return subjectDao.getLiveCount(parentId);
     }
 */
    public Subject get(long id) {
        return subjectDao.get(id);
    }

    public void update(Subject subject) {
        subjectDao.update(subject);
    }
}
