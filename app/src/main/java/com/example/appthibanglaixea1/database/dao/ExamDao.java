package com.example.appthibanglaixea1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appthibanglaixea1.database.entity.Exams;

import java.util.List;
@Dao
public interface ExamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertExam(Exams exam);

    @Update
    void updateExam(Exams exam);

    @Delete
    void deleteExam(Exams exam);

    @Query("SELECT * FROM exams ORDER BY created_at DESC")
    LiveData<List<Exams>> getAllExams();

    @Query("SELECT * FROM exams WHERE exam_id = :id LIMIT 1")
    Exams getExamById(int id);
}
