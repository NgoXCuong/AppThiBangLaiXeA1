package com.example.appthibanglaixea1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appthibanglaixea1.database.entity.StudyProgress;

import java.util.List;

public interface StudyProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertProgress(StudyProgress progress);

    @Update
    void updateProgress(StudyProgress progress);

    @Delete
    void deleteProgress(StudyProgress progress);

    @Query("SELECT * FROM study_progress WHERE user_id = :userId")
    LiveData<List<StudyProgress>> getProgressByUser(int userId);

    @Query("SELECT * FROM study_progress WHERE user_id = :userId AND category_id = :categoryId LIMIT 1")
    StudyProgress getProgressForCategory(int userId, int categoryId);
}
