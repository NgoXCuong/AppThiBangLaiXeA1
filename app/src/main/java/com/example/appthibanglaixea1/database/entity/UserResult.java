package com.example.appthibanglaixea1.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_results",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "user_id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Exams.class,
                        parentColumns = "exam_id",
                        childColumns = "exam_id",
                        onDelete = ForeignKey.CASCADE)
        })
public class UserResult {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "result_id")
    public int resultId;

    @ColumnInfo(name = "user_id", index = true)
    public int userId;

    @ColumnInfo(name = "exam_id", index = true)
    public int examId;

    @ColumnInfo(name = "score")
    public int score;

    @ColumnInfo(name = "total_questions")
    public int totalQuestions;

    @ColumnInfo(name = "correct_answers")
    public int correctAnswers;

    @ColumnInfo(name = "wrong_answers")
    public int wrongAnswers;

    @ColumnInfo(name = "critical_mistakes") // Số câu điểm liệt sai
    public int criticalMistakes;

    @ColumnInfo(name = "passed")
    public boolean passed;

    @ColumnInfo(name = "time_taken") // Thời gian làm bài (giây)
    public int timeTaken;

    @ColumnInfo(name = "taken_at")
    public long takenAt;

    public UserResult() {}

    public UserResult(int userId, int examId, int score, int totalQuestions,
                      int correctAnswers, int wrongAnswers, int criticalMistakes,
                      boolean passed, int timeTaken) {
        this.userId = userId;
        this.examId = examId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.criticalMistakes = criticalMistakes;
        this.passed = passed;
        this.timeTaken = timeTaken;
        this.takenAt = System.currentTimeMillis();
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public int getCriticalMistakes() {
        return criticalMistakes;
    }

    public void setCriticalMistakes(int criticalMistakes) {
        this.criticalMistakes = criticalMistakes;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    public long getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(long takenAt) {
        this.takenAt = takenAt;
    }
}
