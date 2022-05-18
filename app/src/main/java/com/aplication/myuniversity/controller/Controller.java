package com.aplication.myuniversity.controller;

import com.aplication.myuniversity.MainActivity;
import com.aplication.myuniversity.repository.CityRepository;
import com.aplication.myuniversity.repository.StudentCityRepository;
import com.aplication.myuniversity.repository.StudentRepository;
import com.aplication.myuniversity.repository.StudentSubjectRepository;
import com.aplication.myuniversity.repository.SubjectRepository;
import com.aplication.myuniversity.repository.UniversityRepository;

public class Controller {
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;
    private CityRepository cityRepository;
    private StudentCityRepository studentCityRepository;
    private StudentSubjectRepository studentSubjectRepository;
    private UniversityRepository universityRepository;


    public Controller(MainActivity mainActivity) {
        studentRepository = new StudentRepository(mainActivity);
        subjectRepository = new SubjectRepository(mainActivity);
        cityRepository = new CityRepository(mainActivity);
        studentCityRepository = new StudentCityRepository(mainActivity);
        studentSubjectRepository = new StudentSubjectRepository(mainActivity);
        universityRepository = new UniversityRepository(mainActivity);
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public SubjectRepository getSubjectRepository() {
        return subjectRepository;
    }

    public CityRepository getCityRepository() {
        return cityRepository;
    }

    public StudentCityRepository getStudentCityRepository() {
        return studentCityRepository;
    }

    public StudentSubjectRepository getStudentSubjectRepository() {
        return studentSubjectRepository;
    }

    public UniversityRepository getUniversityRepository() {
        return universityRepository;
    }
}
