package com.example.appthibanglaixea1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appthibanglaixea1.database.entity.Questions;

import java.util.List;

public interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertQuestion(Questions question);

    @Update
    void updateQuestion(Questions question);

    @Delete
    void deleteQuestion(Questions question);

    @Query("SELECT * FROM questions WHERE category_id = :categoryId")
    LiveData<List<Questions>> getQuestionsByCategory(int categoryId);

    @Query("SELECT * FROM questions WHERE question_id = :id LIMIT 1")
    Questions getQuestionById(int id);

    @Query("SELECT * FROM questions WHERE is_critical = 1")
    LiveData<List<Questions>> getCriticalQuestions();
}
