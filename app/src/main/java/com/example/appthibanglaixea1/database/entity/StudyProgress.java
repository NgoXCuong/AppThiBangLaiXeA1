package com.example.appthibanglaixea1.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// StudyProgress Entity - Theo dõi tiến độ học tập
@Entity(tableName = "study_progress",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = QuestionCategory.class,
                        parentColumns = "category_id",
                        childColumns = "category_id",
                        onDelete = ForeignKey.CASCADE)
        })
public class StudyProgress {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "progress_id")
    public int progressId;

    @ColumnInfo(name = "user_id", index = true)
    public int userId;

    @ColumnInfo(name = "category_id", index = true)
    public int categoryId;

    @ColumnInfo(name = "studied_questions")
    public int studiedQuestions;

    @ColumnInfo(name = "correct_answers")
    public int correctAnswers;

    @ColumnInfo(name = "last_studied")
    public long lastStudied;

    public StudyProgress() {}

    public StudyProgress(int userId, int categoryId, int studiedQuestions, int correctAnswers) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.studiedQuestions = studiedQuestions;
        this.correctAnswers = correctAnswers;
        this.lastStudied = System.currentTimeMillis();
    }

    public int getProgressId() {
        return progressId;
    }

    public void setProgressId(int progressId) {
        this.progressId = progressId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getStudiedQuestions() {
        return studiedQuestions;
    }

    public void setStudiedQuestions(int studiedQuestions) {
        this.studiedQuestions = studiedQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public long getLastStudied() {
        return lastStudied;
    }

    public void setLastStudied(long lastStudied) {
        this.lastStudied = lastStudied;
    }
}
