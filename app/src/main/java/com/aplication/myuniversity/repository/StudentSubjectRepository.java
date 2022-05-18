package com.aplication.myuniversity.repository;


import android.content.Context;

import com.aplication.myuniversity.dao.CityDao;
import com.aplication.myuniversity.dao.StudentSubjectDao;
import com.aplication.myuniversity.model.City;
import com.aplication.myuniversity.model.StudentSubject;
import com.aplication.myuniversity.room.RoomDB;

import java.util.List;

public class StudentSubjectRepository{

   private final StudentSubjectDao studentSubjectDao;

    public StudentSubjectRepository(Context context) {
        RoomDB db = RoomDB.getInstance(context);
        this.studentSubjectDao = (StudentSubjectDao) db.studentSubjectDao();
    }

    public List<StudentSubject> getStudentSubjects() { return studentSubjectDao.getStudentSubjects(); }

    public long insert(StudentSubject studentSubject) {
        return studentSubjectDao.insert(studentSubject);
    }

    public void delete(StudentSubject studentSubject) {
        studentSubjectDao.delete(studentSubject);
    }

    public StudentSubject get(long id) {
        return studentSubjectDao.get(id);
    }


    public StudentSubject getFirstSubject(long parentStudent) {
        return studentSubjectDao.getFirstSubject(parentStudent);
    }

    public StudentSubject getSubject(long parentStudent, long offset) {
        return studentSubjectDao.getSubject(parentStudent, offset);
    }

    public void update(StudentSubject studentSubject) {
        studentSubjectDao.update(studentSubject);
    }
}

