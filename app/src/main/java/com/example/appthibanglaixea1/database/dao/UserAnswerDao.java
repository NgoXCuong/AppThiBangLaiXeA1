package com.example.appthibanglaixea1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appthibanglaixea1.database.entity.UserAnswer;

import java.util.List;

@Dao
public interface UserAnswerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserAnswer(UserAnswer userAnswer);

    @Update
    void updateUserAnswer(UserAnswer userAnswer);

    @Delete
    void deleteUserAnswer(UserAnswer userAnswer);

    @Query("SELECT * FROM user_answers WHERE result_id = :resultId")
    LiveData<List<UserAnswer>> getAnswersForResult(int resultId);

    // 🔥 Hàm đồng bộ để lấy danh sách UserAnswer mà không cần LiveData
    @Query("SELECT * FROM user_answers WHERE result_id = :resultId")
    List<UserAnswer> getAnswersByResultId(int resultId);
}
