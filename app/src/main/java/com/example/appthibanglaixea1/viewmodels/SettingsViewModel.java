package com.example.appthibanglaixea1.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.appthibanglaixea1.database.entity.Settings;
import com.example.appthibanglaixea1.repository.SettingsRepository;

public class SettingsViewModel extends AndroidViewModel {
    private SettingsRepository repository;
    private LiveData<Settings> settings;
    private int currentUserId;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        repository = new SettingsRepository(application);
    }

    public void setUserId(int userId) {
        this.currentUserId = userId;
        this.settings = repository.getSettings(userId);
        repository.createDefaultSettings(userId); // Tạo settings mặc định nếu chưa có
    }

    public LiveData<Settings> getSettings() {
        return settings;
    }

    public void updateSoundEnabled(boolean enabled) {
        repository.updateSoundEnabled(currentUserId, enabled);
    }

    public void updateVibrationEnabled(boolean enabled) {
        repository.updateVibrationEnabled(currentUserId, enabled);
    }

    public void updateAutoNext(boolean enabled) {
        repository.updateAutoNext(currentUserId, enabled);
    }

    public void updateShowExplanation(boolean enabled) {
        repository.updateShowExplanation(currentUserId, enabled);
    }

    public void updateDailyGoal(int goal) {
        repository.updateDailyGoal(currentUserId, goal);
    }

    public void updateThemeMode(String themeMode) {
        repository.updateThemeMode(currentUserId, themeMode);
    }

    public void updateFontSize(float fontSize) {
        repository.updateFontSize(currentUserId, fontSize);
    }
}