package com.example.appthibanglaixea1.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.appthibanglaixea1.database.AppDatabase;
import com.example.appthibanglaixea1.database.dao.SettingsDao;
import com.example.appthibanglaixea1.database.entity.Settings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsRepository
{
    private SettingsDao settingsDao;
    private ExecutorService executor;

    public SettingsRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        settingsDao = database.settingsDao();
        executor = Executors.newFixedThreadPool(4);
    }

    public LiveData<Settings> getSettings(int userId) {
        return settingsDao.getSettingsByUserId(userId);
    }

    public void createDefaultSettings(int userId) {
        executor.execute(() -> {
            Settings existingSettings = settingsDao.getSettingsByUserIdSync(userId);
            if (existingSettings == null) {
                Settings settings = new Settings();
                settings.userId = userId;
                settingsDao.insertSettings(settings);
            }
        });
    }

    public void updateSoundEnabled(int userId, boolean enabled) {
        executor.execute(() ->
                settingsDao.updateSoundEnabled(userId, enabled, System.currentTimeMillis())
        );
    }

    public void updateVibrationEnabled(int userId, boolean enabled) {
        executor.execute(() ->
                settingsDao.updateVibrationEnabled(userId, enabled, System.currentTimeMillis())
        );
    }

    public void updateAutoNext(int userId, boolean enabled) {
        executor.execute(() ->
                settingsDao.updateAutoNext(userId, enabled, System.currentTimeMillis())
        );
    }

    public void updateShowExplanation(int userId, boolean enabled) {
        executor.execute(() ->
                settingsDao.updateShowExplanation(userId, enabled, System.currentTimeMillis())
        );
    }

    public void updateDailyGoal(int userId, int goal) {
        executor.execute(() ->
                settingsDao.updateDailyGoal(userId, goal, System.currentTimeMillis())
        );
    }

    public void updateThemeMode(int userId, String themeMode) {
        executor.execute(() ->
                settingsDao.updateThemeMode(userId, themeMode, System.currentTimeMillis())
        );
    }

    public void updateFontSize(int userId, float fontSize) {
        executor.execute(() ->
                settingsDao.updateFontSize(userId, fontSize, System.currentTimeMillis())
        );
    }
}
