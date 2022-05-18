package com.aplication.myuniversity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.aplication.myuniversity.databinding.ActivityLoginBinding;
import com.aplication.myuniversity.helpers.Preferences;
import com.aplication.myuniversity.model.Student;
import com.aplication.myuniversity.model.StudentSubject;
import com.aplication.myuniversity.repository.StudentRepository;
import com.aplication.myuniversity.repository.StudentSubjectRepository;
import com.aplication.myuniversity.room.Const;
import com.aplication.myuniversity.utils.MD5;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private Preferences preferences;
    private StudentRepository studentRepository;
    private StudentSubjectRepository studentSubjectRepository;
    private ActivityLoginBinding binding;
    private boolean isLoginLayout = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new Preferences(this);
        if (preferences.getActiveStudentId() > 0) { // если найден активный пользователь
            startMainActivity(); // запускаем главную активность
        } else {
            binding = ActivityLoginBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            init(); // инициализация переменных
            updateViews(); // насройка визуальных компонентов
        }
    }

    private void updateViews() {
        binding.textCreateAccount.setOnClickListener(view -> {
            changeLayout(); // смена формы
        });
        binding.textSignIn.setOnClickListener(view -> {
            changeLayout(); // смена формы
        });
        binding.buttonCreateAccount.setOnClickListener(view -> {
            switch (createStudent()) {
                case Const.ERR_EMPTY_STRING:
                    // отображаем сообщение об ошибке
                    Toast.makeText(this, R.string.registration_error, Toast.LENGTH_LONG).show();
                    break;
                case Const.ERR_LOGIN_EXIST:
                    // отображаем сообщение об ошибке
                    Toast.makeText(this, R.string.login_exist, Toast.LENGTH_LONG).show();
                    break;
                case Const.REGISTRATION_SUCCESS:
                    startMainActivity(); // переход на главную активность
                    break;
                default:
                    break;
            }
        });
        binding.buttonSignIn.setOnClickListener(view -> {
            switch (signIn()) {
                case Const.ERR_EMPTY_STRING:
                    // отображаем сообщение об ошибке
                    Toast.makeText(this, R.string.registration_error, Toast.LENGTH_LONG).show();
                    break;
                case Const.ERR_PASSWORD:
                    // отображаем сообщение об ошибке
                    Toast.makeText(this, R.string.password_error, Toast.LENGTH_LONG).show();
                    break;
                case Const.ERR_LOGIN:
                    // отображаем сообщение об ошибке
                    Toast.makeText(this, R.string.login_not_found_error, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        });
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private int signIn() {
        String login = Objects.requireNonNull(binding.editLogin.getEditableText().toString());
        if (login.isEmpty())
            return Const.ERR_EMPTY_STRING;

        String password = Objects.requireNonNull(binding.editPassword.getEditableText().toString());
        if (password.isEmpty())
            return Const.ERR_EMPTY_STRING;

        Student student = studentRepository.getStudentByLogin(login);
        if (student == null)  {
            return Const.ERR_LOGIN;
        } else {
            if (student.getPassword().equals(MD5.get(password))) { // пароль совпадает для данного логина
                preferences.updateActiveStudentId(student.getId()); // обновляем активного пользователя
                startMainActivity();
            } else {
                return Const.ERR_PASSWORD;
            }
        }
        return Const.LOGIN_SUCCESS;
    }

    private void changeLayout() {
        if (isLoginLayout) {
            binding.layoutLogin.setVisibility(View.INVISIBLE); // скрываем форму логина
            binding.layoutCreateAccount.setVisibility(View.VISIBLE); // отображаем форму регистрации
            isLoginLayout = false; // меняем на регистрацию
        } else {
            binding.layoutLogin.setVisibility(View.VISIBLE); // отображаем форму логина
            binding.layoutCreateAccount.setVisibility(View.INVISIBLE); // скрываем форму регистрации
            isLoginLayout = true; // меняем на логин
        }
    }

    private void init() {
        studentRepository = new StudentRepository(this);
        studentSubjectRepository = new StudentSubjectRepository(this);
    }

    private int createStudent() {
        Student student = new Student();

        String surname = Objects.requireNonNull(binding.editSurname.getEditableText().toString());
        if (surname.isEmpty()) // проверка на пустое поле
            return Const.ERR_EMPTY_STRING;
        student.setSurname(surname);

        String name = Objects.requireNonNull(binding.editName.getEditableText().toString());
        if (name.isEmpty()) // проверка на пустое поле
            return Const.ERR_EMPTY_STRING;
        student.setName(name);

        String login = Objects.requireNonNull(binding.editRegLogin.getEditableText().toString());
        if (login.isEmpty()) // проверка на пустое поле
            return Const.ERR_EMPTY_STRING;
        student.setLogin(login);

        String password = Objects.requireNonNull(binding.editRegPassword.getEditableText().toString());
        if (password.isEmpty()) // проверка на пустое поле
            return Const.ERR_EMPTY_STRING;
        student.setPassword(MD5.get(password));

        Student verifyStudent = studentRepository.getStudentByLogin(login);
        if (verifyStudent == null) { // если студент с таким логином не найден
            long id = studentRepository.insert(student); // добавляем нового студента в базу
            preferences.updateActiveStudentId((int) id); // обновляем активного пользователя
            insertStudentSubjects(id); // добавляем предметы для созданного студента
        } else {
            return Const.ERR_LOGIN_EXIST;
        }

        return Const.REGISTRATION_SUCCESS;
    }

    private void insertStudentSubjects(long id) { // добавляем предметы для созданного студента
        StudentSubject studentSubjectMath = new StudentSubject();
        studentSubjectMath.setParentStudent(id);
        studentSubjectMath.setSubjectId(Const.MATH_ID);
        studentSubjectMath.setScores(Const.DEFAUlT_SCORES);
        studentSubjectRepository.insert(studentSubjectMath);

        StudentSubject studentSubjectRus = new StudentSubject();
        studentSubjectRus.setParentStudent(id);
        studentSubjectRus.setSubjectId(Const.RUS_ID);
        studentSubjectRus.setScores(Const.DEFAUlT_SCORES);
        studentSubjectRepository.insert(studentSubjectRus);

        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setParentStudent(id);
        studentSubject.setSubjectId(Const.STUDENT_SUBJECT);
        studentSubject.setScores(Const.DEFAUlT_SCORES);
        studentSubjectRepository.insert(studentSubject);
    }
}