package com.example.appthibanglaixea1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appthibanglaixea1.database.entity.UserResult;

import java.util.List;

public interface UserResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertResult(UserResult result);

    @Update
    void updateResult(UserResult result);

    @Delete
    void deleteResult(UserResult result);

    @Query("SELECT * FROM user_results WHERE user_id = :userId ORDER BY taken_at DESC")
    LiveData<List<UserResult>> getResultsByUser(int userId);

    @Query("SELECT * FROM user_results WHERE exam_id = :examId AND user_id = :userId LIMIT 1")
    UserResult getResultForExam(int userId, int examId);
}
