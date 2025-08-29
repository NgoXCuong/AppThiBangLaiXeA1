package com.example.appthibanglaixea1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appthibanglaixea1.database.entity.Settings;

@Dao
public interface SettingsDao {

    @Query("SELECT * FROM settings WHERE user_id = :userId LIMIT 1")
    LiveData<Settings> getSettingsByUserId(int userId);

    @Query("SELECT * FROM settings WHERE user_id = :userId LIMIT 1")
    Settings getSettingsByUserIdSync(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertSettings(Settings settings);

    @Update
    void updateSettings(Settings settings);

    @Query("UPDATE settings SET sound_enabled = :enabled, updated_at = :timestamp WHERE user_id = :userId")
    void updateSoundEnabled(int userId, boolean enabled, long timestamp);

    @Query("UPDATE settings SET vibration_enabled = :enabled, updated_at = :timestamp WHERE user_id = :userId")
    void updateVibrationEnabled(int userId, boolean enabled, long timestamp);

    @Query("UPDATE settings SET auto_next = :enabled, updated_at = :timestamp WHERE user_id = :userId")
    void updateAutoNext(int userId, boolean enabled, long timestamp);

    @Query("UPDATE settings SET show_explanation = :enabled, updated_at = :timestamp WHERE user_id = :userId")
    void updateShowExplanation(int userId, boolean enabled, long timestamp);

    @Query("UPDATE settings SET daily_goal = :goal, updated_at = :timestamp WHERE user_id = :userId")
    void updateDailyGoal(int userId, int goal, long timestamp);

    @Query("UPDATE settings SET theme_mode = :themeMode, updated_at = :timestamp WHERE user_id = :userId")
    void updateThemeMode(int userId, String themeMode, long timestamp);

    @Query("UPDATE settings SET font_size = :fontSize, updated_at = :timestamp WHERE user_id = :userId")
    void updateFontSize(int userId, float fontSize, long timestamp);

    @Query("DELETE FROM settings WHERE user_id = :userId")
    void deleteSettingsByUserId(int userId);
}
