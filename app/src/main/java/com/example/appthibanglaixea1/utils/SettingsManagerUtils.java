package com.example.appthibanglaixea1.utils;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.appthibanglaixea1.database.entity.Settings;
import com.example.appthibanglaixea1.repository.SettingsRepository;

public class SettingsManagerUtils {
    private static SettingsManagerUtils instance;
    private SettingsRepository repository;
    private Settings currentSettings;
    private Context context;

    private SettingsManagerUtils(Context context) {
        this.context = context.getApplicationContext();
    }

    public static synchronized SettingsManagerUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SettingsManagerUtils(context);
        }
        return instance;
    }

    public void initialize(int userId) {
        repository = new SettingsRepository((android.app.Application) context);
        LiveData<Settings> settingsLiveData = repository.getSettings(userId);

        settingsLiveData.observeForever(new Observer<Settings>() {
            @Override
            public void onChanged(Settings settings) {
                currentSettings = settings;
                if (settings == null) {
                    repository.createDefaultSettings(userId);
                }
            }
        });
    }

    public boolean isSoundEnabled() {
        return currentSettings != null ? currentSettings.soundEnabled : true;
    }

    public boolean isVibrationEnabled() {
        return currentSettings != null ? currentSettings.vibrationEnabled : true;
    }

    public boolean isAutoNextEnabled() {
        return currentSettings != null ? currentSettings.autoNext : false;
    }

    public boolean isShowExplanationEnabled() {
        return currentSettings != null ? currentSettings.showExplanation : true;
    }

    public int getDailyGoal() {
        return currentSettings != null ? currentSettings.dailyGoal : 50;
    }

    public String getThemeMode() {
        return currentSettings != null ? currentSettings.themeMode : "SYSTEM";
    }

    public float getFontSize() {
        return currentSettings != null ? currentSettings.fontSize : 1.0f;
    }
}
