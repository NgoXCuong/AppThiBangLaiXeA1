package com.example.appthibanglaixea1.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "question_categories")
public class QuestionCategory {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    public int categoryId;

    @ColumnInfo(name = "category_name")
    public String categoryName;

    @ColumnInfo(name = "description")
    public String description;

    public QuestionCategory() {}

    public QuestionCategory(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
