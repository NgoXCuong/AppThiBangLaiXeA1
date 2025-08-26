package com.example.appthibanglaixea1.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exams")
public class Exams {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exam_id")
    public int examId;

    @ColumnInfo(name = "exam_name")
    public String examName;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "time_limit") // Thời gian thi (phút)
    public int timeLimit;

    @ColumnInfo(name = "total_questions") // Tổng số câu hỏi
    public int totalQuestions;

    @ColumnInfo(name = "pass_score") // Điểm cần đạt để đỗ
    public int passScore;

    @ColumnInfo(name = "exam_type") // Loại đề: 1-Sát hạch, 2-Luyện tập
    public int examType;

    @ColumnInfo(name = "created_at")
    public long createdAt;

    public Exams() {}

    public Exams(String examName, String description, int timeLimit, int totalQuestions, int passScore, int examType) {
        this.examName = examName;
        this.description = description;
        this.timeLimit = timeLimit;
        this.totalQuestions = totalQuestions;
        this.passScore = passScore;
        this.examType = examType;
        this.createdAt = System.currentTimeMillis();
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getPassScore() {
        return passScore;
    }

    public void setPassScore(int passScore) {
        this.passScore = passScore;
    }

    public int getExamType() {
        return examType;
    }

    public void setExamType(int examType) {
        this.examType = examType;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
