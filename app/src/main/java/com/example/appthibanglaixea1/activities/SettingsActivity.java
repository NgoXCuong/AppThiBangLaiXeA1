package com.example.appthibanglaixea1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.appthibanglaixea1.R;
import com.example.appthibanglaixea1.database.entity.Settings;
import com.example.appthibanglaixea1.utils.SessionManagerUtils;
import com.example.appthibanglaixea1.viewmodels.SettingsViewModel;

public class SettingsActivity extends AppCompatActivity {

    private SettingsViewModel settingsViewModel;
    private SessionManagerUtils sessionManager;

    // UI Components
    private Switch switchSound, switchVibration, switchAutoNext, switchExplanation, switchReminder;
    private SeekBar seekBarFontSize, seekBarDailyGoal;
    private TextView tvThemeValue, tvDailyGoalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        setupToolbar();
        initViewModel();
        observeSettings();
        setupListeners();
    }

    private void initViews() {
        switchSound = findViewById(R.id.switchSound);
        switchVibration = findViewById(R.id.switchVibration);
        switchAutoNext = findViewById(R.id.switchAutoNext);
        switchExplanation = findViewById(R.id.switchExplanation);
        switchReminder = findViewById(R.id.switchReminder);
        seekBarFontSize = findViewById(R.id.seekBarFontSize);
        seekBarDailyGoal = findViewById(R.id.seekBarDailyGoal);
        tvThemeValue = findViewById(R.id.tvThemeValue);
        tvDailyGoalValue = findViewById(R.id.tvDailyGoalValue);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initViewModel() {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        sessionManager = new SessionManagerUtils(this);

        int userId = sessionManager.getUserId();
        settingsViewModel.setUserId(userId);
    }

    private void observeSettings() {
        settingsViewModel.getSettings().observe(this, settings -> {
            if (settings != null) {
                updateUI(settings);
            }
        });
    }

    private void updateUI(Settings settings) {
        switchSound.setChecked(settings.soundEnabled);
        switchVibration.setChecked(settings.vibrationEnabled);
        switchAutoNext.setChecked(settings.autoNext);
        switchExplanation.setChecked(settings.showExplanation);
        switchReminder.setChecked(settings.examReminder);

        // Font size: 0.8f-1.5f -> 0-100
        int fontProgress = (int) ((settings.fontSize - 0.8f) / 0.7f * 100);
        seekBarFontSize.setProgress(fontProgress);

        // Daily goal: 20-100 -> 0-80
        seekBarDailyGoal.setProgress(settings.dailyGoal - 20);
        tvDailyGoalValue.setText(settings.dailyGoal + " câu hỏi/ngày");

        updateThemeText(settings.themeMode);
    }

    private void setupListeners() {
        // Switch listeners
        switchSound.setOnCheckedChangeListener((v, checked) ->
                settingsViewModel.updateSoundEnabled(checked));

        switchVibration.setOnCheckedChangeListener((v, checked) ->
                settingsViewModel.updateVibrationEnabled(checked));

        switchAutoNext.setOnCheckedChangeListener((v, checked) ->
                settingsViewModel.updateAutoNext(checked));

        switchExplanation.setOnCheckedChangeListener((v, checked) ->
                settingsViewModel.updateShowExplanation(checked));

        switchReminder.setOnCheckedChangeListener((v, checked) ->
                settingsViewModel.updateVibrationEnabled(checked));

        // Font size listener
        seekBarFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    float fontSize = 0.8f + (progress / 100.0f) * 0.7f;
                    settingsViewModel.updateFontSize(fontSize);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Daily goal listener
        seekBarDailyGoal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int goal = progress + 20;
                    tvDailyGoalValue.setText(goal + " câu hỏi/ngày");
                    settingsViewModel.updateDailyGoal(goal);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Theme selector
        findViewById(R.id.layoutTheme).setOnClickListener(v -> showThemeDialog());

        // About
        findViewById(R.id.layoutAbout).setOnClickListener(v -> showAboutDialog());

        // Logout
        findViewById(R.id.layoutLogout).setOnClickListener(v -> showLogoutDialog());
    }

    private void showThemeDialog() {
        String[] themes = {"Sáng", "Tối", "Theo hệ thống"};
        String[] themeValues = {"LIGHT", "DARK", "SYSTEM"};

        new AlertDialog.Builder(this)
                .setTitle("Chọn chủ đề")
                .setItems(themes, (dialog, which) -> {
                    settingsViewModel.updateThemeMode(themeValues[which]);
                    updateThemeText(themeValues[which]);
                })
                .show();
    }

    private void updateThemeText(String themeMode) {
        String themeText = "Theo hệ thống";
        switch (themeMode) {
            case "LIGHT": themeText = "Sáng"; break;
            case "DARK": themeText = "Tối"; break;
        }
        tvThemeValue.setText(themeText);
    }

    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Về ứng dụng")
                .setMessage("Ứng dụng Luyện Thi Bằng Lái A1\n\nPhiên bản: 1.0.0\nPhát triển: Your Name\n\nỨng dụng giúp bạn luyện tập và chuẩn bị tốt nhất cho kỳ thi bằng lái xe A1.")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                .setPositiveButton("Đăng xuất", (dialog, which) -> {
                    sessionManager.logout();
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}