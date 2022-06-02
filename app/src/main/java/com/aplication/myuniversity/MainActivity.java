package com.aplication.myuniversity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.aplication.myuniversity.controller.Controller;
import com.aplication.myuniversity.helpers.Preferences;
import com.aplication.myuniversity.utils.FileUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.aplication.myuniversity.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private Controller controller;
    private ActivityResultLauncher<Intent> chooseFileLauncher;
    private Uri imageUri = null;

    public Controller getController() {
        return controller;
    }

    private Preferences preferences;
    private NavController navController;

    public Preferences getPreferences() {
        return preferences;
    }

    private boolean ready = false;

    public boolean isReady() {
        return ready;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void nullImageUri() {
        ready = false;
        imageUri = null;
    }

    public ActivityResultLauncher<Intent> getChooseFileLauncher() {
        return chooseFileLauncher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller(this);
        preferences = new Preferences(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_account)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        registerChooseFileLauncher(); // зарегистрируем лаунчер активити для выбора фото
    }

    public void goToSettings() {
        navController.navigate(R.id.action_navigation_home_to_navigation_account);
    }

    private void registerChooseFileLauncher() {
        chooseFileLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),  result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        final Intent data = result.getData();
                        if (data != null) { // todo async copy
                            imageUri = FileUtil.copyFileToPicturesFolder(this, data.getData());
                            ready = true;
                        }
                    }
                });
    }


}