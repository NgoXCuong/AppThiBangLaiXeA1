package com.example.appthibanglaixea1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appthibanglaixea1.database.entity.Answers;

import java.util.List;

public interface AnswerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAnswer(Answers answer);

    @Update
    void updateAnswer(Answers answer);

    @Delete
    void deleteAnswer(Answers answer);

    @Query("SELECT * FROM answers WHERE question_id = :questionId")
    LiveData<List<Answers>> getAnswersForQuestion(int questionId);
}
