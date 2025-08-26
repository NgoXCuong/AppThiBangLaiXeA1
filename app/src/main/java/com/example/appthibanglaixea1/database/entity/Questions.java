package com.example.appthibanglaixea1.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions",
        foreignKeys = @ForeignKey(entity = QuestionCategory.class,
                parentColumns = "category_id",
                childColumns = "category_id",
                onDelete = ForeignKey.CASCADE))
public class Questions {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "question_id")
    public int questionId;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "image_url")
    public String imageUrl;

    @ColumnInfo(name = "category_id", index = true)
    public int categoryId;

    @ColumnInfo(name = "difficulty_level")
    public int difficultyLevel; // 1: Dễ, 2: Trung bình, 3: Khó

    @ColumnInfo(name = "is_critical") // Câu hỏi điểm liệt
    public boolean isCritical;

    @ColumnInfo(name = "explanation")
    public String explanation; // Giải thích đáp án

    public Questions() {}

    public Questions(String content, String imageUrl, int categoryId, int difficultyLevel, boolean isCritical, String explanation) {
        this.content = content;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.difficultyLevel = difficultyLevel;
        this.isCritical = isCritical;
        this.explanation = explanation;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
