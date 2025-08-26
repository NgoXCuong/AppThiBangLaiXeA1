package com.example.appthibanglaixea1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appthibanglaixea1.database.entity.QuestionCategory;

import java.util.List;

public interface QuestionCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCategory(QuestionCategory category);

    @Update
    void updateCategory(QuestionCategory category);

    @Delete
    void deleteCategory(QuestionCategory category);

    @Query("SELECT * FROM question_categories ORDER BY category_name ASC")
    LiveData<List<QuestionCategory>> getAllCategories();

    @Query("SELECT * FROM question_categories WHERE category_id = :id LIMIT 1")
    QuestionCategory getCategoryById(int id);
}
