package com.example.appthibanglaixea1.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "settings",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(value = "user_id", unique = true)
)
public class Settings {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "sound_enabled")
    public boolean soundEnabled = true;

    @ColumnInfo(name = "vibration_enabled")
    public boolean vibrationEnabled = true;

    @ColumnInfo(name = "auto_next")
    public boolean autoNext = false;

    @ColumnInfo(name = "show_explanation")
    public boolean showExplanation = true;

    @ColumnInfo(name = "daily_goal")
    public int dailyGoal = 50;

    @ColumnInfo(name = "theme_mode")
    public String themeMode = "SYSTEM"; // LIGHT, DARK, SYSTEM

    @ColumnInfo(name = "font_size")
    public float fontSize = 1.0f;

    @ColumnInfo(name = "exam_reminder")
    public boolean examReminder = false;

    @ColumnInfo(name = "updated_at")
    public long updatedAt = System.currentTimeMillis();
}