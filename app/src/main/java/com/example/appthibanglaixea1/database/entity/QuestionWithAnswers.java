package com.example.appthibanglaixea1.database.entity;

import java.util.List;

public class QuestionWithAnswers {
    private Questions question;
    private List<Answers> answers;

    public QuestionWithAnswers(Questions question, List<Answers> answers) {
        this.question = question;
        this.answers = answers;
    }

    // Getter cho câu hỏi
    public Questions getQuestion() {
        return question;
    }

    // Setter cho câu hỏi
    public void setQuestion(Questions question) {
        this.question = question;
    }

    // Getter cho danh sách câu trả lời
    public List<Answers> getAnswers() {
        return answers;
    }

    // Setter cho danh sách câu trả lời
    public void setAnswers(List<Answers> answers) {
        this.answers = answers;
    }
}
