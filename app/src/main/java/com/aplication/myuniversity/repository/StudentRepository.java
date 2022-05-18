package com.aplication.myuniversity.repository;


import android.content.Context;

import com.aplication.myuniversity.dao.StudentDao;
import com.aplication.myuniversity.model.Student;
import com.aplication.myuniversity.room.RoomDB;

import java.util.List;

public class StudentRepository {
    private final StudentDao studentDao;

    public StudentRepository(Context context) {
        RoomDB db = RoomDB.getInstance(context);
        this.studentDao = db.studentDao();
    }

    public Student getStudentByLogin(String login) {
        return studentDao.getStudentByLogin(login);
    }

    public List<Student> getStudents() {
        return studentDao.getStudents();
    }

    public long insert(Student student) {
        return studentDao.insert(student);
    }

    public void delete(Student student) {
        RoomDB.Executor.execute(() -> studentDao.delete(student));
    }

    public Student get(long id) {
        return studentDao.get(id);
    }

    public void update(Student student) {
        studentDao.update(student);
    }
}