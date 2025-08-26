package com.example.appthibanglaixea1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.appthibanglaixea1.database.entity.ExamQuestion;

import java.util.List;

public interface ExamQuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExamQuestion(ExamQuestion examQuestion);

    @Delete
    void deleteExamQuestion(ExamQuestion examQuestion);

    @Query("SELECT * FROM exam_questions WHERE exam_id = :examId ORDER BY question_order ASC")
    LiveData<List<ExamQuestion>> getQuestionsForExam(int examId);
}
