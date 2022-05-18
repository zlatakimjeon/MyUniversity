package com.aplication.myuniversity.ui.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.aplication.myuniversity.MainActivity;
import com.aplication.myuniversity.R;
import com.aplication.myuniversity.adapter.UniversityAdapter;
import com.aplication.myuniversity.controller.Controller;
import com.aplication.myuniversity.databinding.FragmentHomeBinding;
import com.aplication.myuniversity.helpers.Preferences;
import com.aplication.myuniversity.model.City;
import com.aplication.myuniversity.model.Student;
import com.aplication.myuniversity.model.StudentSubject;
import com.aplication.myuniversity.model.Subject;
import com.aplication.myuniversity.model.University;
import com.aplication.myuniversity.repository.CityRepository;
import com.aplication.myuniversity.repository.StudentRepository;
import com.aplication.myuniversity.repository.UniversityRepository;
import com.aplication.myuniversity.room.Const;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private MainActivity mainActivity;

    private Controller controller;

    private StudentRepository studentRepository;
    private CityRepository cityRepository;
    private UniversityRepository universityRepository;
    private UniversityAdapter universityAdapter;

    private Preferences preferences;

    private Student student;

    private int activeStudent;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();
        updateViews();

        return root;
    }

    private void updateViews() {
        String textWelcome = getString(R.string.text_welcome);
        String fullWelcome = textWelcome + student.getName() + " " + student.getSurname();
        binding.textWelcomeUser.setText(fullWelcome); // назначаем текст добро пожаловать имя фамилия

        boolean showTip = preferences.showTip(); // данные о видимости подсказки
        // обновляем значение видимости кнопки закрыть
        binding.cardTip.setVisibility((showTip) ? View.VISIBLE : View.GONE);

        binding.imageClose.setOnClickListener(view -> {
            binding.cardTip.setVisibility(View.GONE); // скрываем карточку подсказки
            preferences.updateShowTip(false); // обновляем значение показа подсказки
        });

        binding.textSettings.setOnClickListener(view -> {
            mainActivity.goToSettings(); // переходим во вкладку настройки
        });

        int cityId = student.getCityId(); // получаем айди города
        if (cityId > 0) { // если студент выбрал город
            City city = cityRepository.get(cityId); // получаем город из базы
            binding.buttonCity.setText(city.getTitle()); // обновляем название кнопки на название города
        }

        binding.buttonCity.setOnClickListener(view -> {
            showCitiesDialog(); // отображаем диалог выбора города
        });

        universityAdapter = new UniversityAdapter(mainActivity);
        binding.recyclerView.setAdapter(universityAdapter); // назначаем адаптер

        updateUniversityList(); // получение университетов из базы
    }


    private void showCitiesDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(mainActivity);
        // builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle(R.string.title_choose_subject);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.select_dialog_item);

        List<City> citiesList = cityRepository.getCities();

        for (int i = 0; i < citiesList.size(); i++ ) {
            arrayAdapter.add(citiesList.get(i).getTitle());
        }

        builderSingle.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());

        builderSingle.setAdapter(arrayAdapter, (dialog, cityId) -> {
            updateCityButton(arrayAdapter, cityId); // обновление кноки
            updateUniversityList(); // обновляем список университетов для поступления
        });
        builderSingle.show();
    }

    private void updateUniversityList() {
        if (student.getCityId() <= 0) {
            binding.imageBackground.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_baseline_location_city_24));
            binding.textMessage.setText(R.string.text_nocity);
            changeBackgroundVisibility(true); // показываем фон
        } else {
            List<University> list =
                    universityRepository.getUniversities(student.getCityId(), student.getScores());
            universityAdapter.setList(list); // обновляем данные из базы
            if (list.isEmpty()) {
                binding.imageBackground.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_baseline_priority_high_24));
                binding.textMessage.setText(R.string.text_noscores);
                changeBackgroundVisibility(true); // показываем фон
            } else {
                changeBackgroundVisibility(false); // скрываем фон
            }
        }
    }

    private void changeBackgroundVisibility(boolean value) {
        binding.imageBackground.setVisibility((value) ? View.VISIBLE : View.INVISIBLE);
        binding.textMessage.setVisibility((value) ? View.VISIBLE : View.INVISIBLE);
        binding.recyclerView.setVisibility((value) ? View.INVISIBLE : View.VISIBLE);
    }

    private void updateCityButton(@NonNull ArrayAdapter arrayAdapter, int cityId) {
        String city = (String) arrayAdapter.getItem(cityId);
        binding.buttonCity.setText(city); // обновляем название кнопки
        student.setCityId(cityId + 1);  // обновляем айди города
        studentRepository.update(student); // обновляем город в базе данных
    }

    private void init() {
        mainActivity = (MainActivity) requireActivity();
        controller = mainActivity.getController();// получаем контроллер
        preferences = mainActivity.getPreferences(); // получаем настрйки

        // получаем репозитории
        studentRepository = controller.getStudentRepository();
        cityRepository = controller.getCityRepository();
        universityRepository = controller.getUniversityRepository();

        activeStudent = preferences.getActiveStudentId(); // получаем активного студента

        student = studentRepository.get(activeStudent); // получаем студента из базы
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}