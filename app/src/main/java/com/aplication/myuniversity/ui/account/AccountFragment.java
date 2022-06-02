package com.aplication.myuniversity.ui.account;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.aplication.myuniversity.LoginActivity;
import com.aplication.myuniversity.MainActivity;
import com.aplication.myuniversity.R;
import com.aplication.myuniversity.controller.Controller;
import com.aplication.myuniversity.databinding.FragmentAccountBinding;
import com.aplication.myuniversity.helpers.Preferences;
import com.aplication.myuniversity.model.Student;
import com.aplication.myuniversity.model.StudentSubject;
import com.aplication.myuniversity.model.Subject;
import com.aplication.myuniversity.repository.StudentRepository;
import com.aplication.myuniversity.repository.StudentSubjectRepository;
import com.aplication.myuniversity.repository.SubjectRepository;
import com.aplication.myuniversity.room.Const;
import com.aplication.myuniversity.utils.PermissionUtils;

import java.io.IOException;
import java.util.List;

import it.sephiroth.android.library.numberpicker.NumberPicker;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    private MainActivity mainActivity;
    private Controller controller;
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;
    private StudentSubjectRepository studentSubjectRepository;

    private Preferences preferences;

    private Student student;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init(); // инициализируем переменные
        initViews(); // инициализируем представления
        updateViews(); // обновляем представления
        return root;
    }

    private void initViews() {
        binding.imageAvatar.setOnClickListener(view -> {
            chooseAvatar();
        });

        binding.editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // перед изменением текста
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                student.setName(String.valueOf(charSequence));
                studentRepository.update(student);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // после изменения

            }
        });

        binding.editTextSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                student.setSurname(String.valueOf(charSequence));
                studentRepository.update(student);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //
            }
        });

        binding.buttonSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSubjectDialog();
            }
        });

        binding.pickerMath.setNumberPickerChangeListener(new NumberPicker.OnNumberPickerChangeListener() {
            @Override
            public void onProgressChanged(@NonNull NumberPicker numberPicker, int scores, boolean b) {
                StudentSubject studentSubjectMath = studentSubjectRepository.getFirstSubject(preferences.getActiveStudentId());
                studentSubjectMath.setScores(scores);  // устанавливаем баллы
                studentSubjectRepository.update(studentSubjectMath); // обновляем предмет студента
                updateAllScores(); // обновляем сумму баллов студента
            }

            @Override
            public void onStartTrackingTouch(@NonNull NumberPicker numberPicker) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull NumberPicker numberPicker) {

            }
        });

        binding.pickerRus.setNumberPickerChangeListener(new NumberPicker.OnNumberPickerChangeListener() {
            @Override
            public void onProgressChanged(@NonNull NumberPicker numberPicker, int scores, boolean b) {
                StudentSubject studentSubjectRus = studentSubjectRepository.getSubject(preferences.getActiveStudentId(), Const.OFFSET_RUS_ID);
                studentSubjectRus.setScores(scores);  // устанавливаем баллы
                studentSubjectRepository.update(studentSubjectRus); // обновляем предмет студента
                updateAllScores(); // обновляем сумму баллов студента
            }

            @Override
            public void onStartTrackingTouch(@NonNull NumberPicker numberPicker) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull NumberPicker numberPicker) {

            }
        });

        binding.pickerSubject.setNumberPickerChangeListener(new NumberPicker.OnNumberPickerChangeListener() {
            @Override
            public void onProgressChanged(@NonNull NumberPicker numberPicker, int scores, boolean b) {
                StudentSubject studentSubject = studentSubjectRepository.getSubject(preferences.getActiveStudentId(), Const.OFFSET_STUDENT_SUBJECT);
                studentSubject.setScores(scores); // устанавливаем баллы
                studentSubjectRepository.update(studentSubject); // обновляем предмет студента
                updateAllScores(); // обновляем сумму баллов студента
            }

            @Override
            public void onStartTrackingTouch(@NonNull NumberPicker numberPicker) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull NumberPicker numberPicker) {

            }
        });
    }

    private void chooseAvatar() {
        PermissionUtils.requestStoragePermissions(mainActivity); // запрашиваем доступ к файлам
        final Intent getContentIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getContentIntent.setType("*/*");
        getContentIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        getContentIntent.addCategory(Intent.CATEGORY_OPENABLE);
        Intent intent = Intent.createChooser(getContentIntent, getString(R.string.select_picture_file));
        mainActivity.getChooseFileLauncher().launch(intent); // запускаем выбор фото
        startThread(); // запускаем поток проверки выбора картинки
    }

    private void startThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        sleep(1000);
                        if (mainActivity.isReady()) {
                                Bitmap finalBitmap = getImageBitmap(mainActivity.getImageUri());
                                mainActivity.runOnUiThread(() -> {
                                    binding.imageAvatar.setImageBitmap(finalBitmap); // назначаем картинку
                                    student.setUri(mainActivity.getImageUri());
                                    studentRepository.update(student); // обновляем в базе ссылка
                                    mainActivity.nullImageUri(); // обнуляем ссылку на картинку
                                });

                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    private Bitmap getImageBitmap(Uri imageUri) {
        Bitmap bitmap = null;
        ContentResolver contentResolver = mainActivity.getContentResolver();
        try {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
            } else {
                ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, imageUri);
                bitmap = ImageDecoder.decodeBitmap(source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void updateAllScores() {
        StudentSubject studentSubjectMath =
                studentSubjectRepository.getFirstSubject(preferences.getActiveStudentId());
        StudentSubject studentSubjectRus =
                studentSubjectRepository.getSubject(preferences.getActiveStudentId(), Const.OFFSET_RUS_ID);
        StudentSubject studentSubject =
                studentSubjectRepository.getSubject(preferences.getActiveStudentId(), Const.OFFSET_STUDENT_SUBJECT);
        // считаем суммарное количество баллов студента
        int scores = studentSubjectMath.getScores() + studentSubjectRus.getScores() + studentSubject.getScores();
        student.setScores((int) scores); // обвноялем значение баллов у студента
        studentRepository.update(student); // обвноялем значение баллов у студента в базе
    }

    private void showSubjectDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(mainActivity);
        // builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle(R.string.choose_city);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.select_dialog_item);

        List<Subject> subjectList = subjectRepository.getSubjects();

        for (int i = 2; i < subjectList.size(); i++) {
            arrayAdapter.add(subjectList.get(i).getTitle());
        }

        builderSingle.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());

        builderSingle.setAdapter(arrayAdapter, (dialog, subjectId) -> {
            String subject = arrayAdapter.getItem(subjectId);
            binding.buttonSubject.setText(subject); // обновляем название кнопки
            StudentSubject studentSubject = studentSubjectRepository.getSubject(preferences.getActiveStudentId(), Const.OFFSET_STUDENT_SUBJECT);
            studentSubject.setSubjectId((long) (subjectId + 1)); // обновляем айди предмета
            studentSubjectRepository.update(studentSubject); // обновили значения в базе данных
        });
        builderSingle.show();
    }

    private void updateViews() {
        student = studentRepository.get(preferences.getActiveStudentId());

        binding.imageAvatar.setImageBitmap(getImageBitmap(student.getUri()));

        binding.editTextName.setText(student.getName()); // имя
        binding.editTextSurname.setText(student.getSurname()); // фамилия

        Subject math = subjectRepository.get(Const.MATH_ID); // кнопка математика
        binding.buttonMath.setText(math.getTitle()); // текст кнопки математика

        StudentSubject studentSubjectMath1 = studentSubjectRepository.getFirstSubject(preferences.getActiveStudentId());
        binding.pickerMath.setProgress(studentSubjectMath1.getScores()); // получаем баллы по математике

        Subject rus = subjectRepository.get(Const.RUS_ID); // кнопка русский язык
        binding.buttonRus.setText(rus.getTitle()); // текст кнопки  русский язык

        StudentSubject studentSubjectRus1 = studentSubjectRepository.getSubject(preferences.getActiveStudentId(), Const.OFFSET_RUS_ID);
        binding.pickerRus.setProgress(studentSubjectRus1.getScores()); // получаем баллы по русскому

        StudentSubject studentSubject1 = studentSubjectRepository.getSubject(preferences.getActiveStudentId(), Const.OFFSET_STUDENT_SUBJECT);
        binding.pickerSubject.setProgress(studentSubject1.getScores()); // получаем баллы по выбранному студентом предмету

        Subject subject1 = subjectRepository.get(studentSubject1.getSubjectId()); // получаем предмет выбранный студентом
        binding.buttonSubject.setText(subject1.getTitle()); // меняем текст кнопки на название выбранного предмета

        binding.buttonLogout.setOnClickListener(view -> {
            preferences.updateActiveStudentId(0);
            startLoginActivity();
        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(requireActivity(), LoginActivity.class));
        requireActivity().finish();
    }

    private void init() {
        mainActivity = (MainActivity) requireActivity();
        controller = mainActivity.getController();// получаем контроллер
        preferences = mainActivity.getPreferences(); // получаем настрйки

        studentRepository = controller.getStudentRepository();
        subjectRepository = controller.getSubjectRepository();
        studentSubjectRepository = controller.getStudentSubjectRepository();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}