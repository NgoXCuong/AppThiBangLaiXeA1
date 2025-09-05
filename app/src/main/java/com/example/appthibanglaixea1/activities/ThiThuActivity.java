package com.example.appthibanglaixea1.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.appthibanglaixea1.R;
import com.example.appthibanglaixea1.adapters.ThiThuAdapter;
import com.example.appthibanglaixea1.database.AppDatabase;
import com.example.appthibanglaixea1.database.dao.AnswerDao;
import com.example.appthibanglaixea1.database.dao.ExamQuestionDao;
import com.example.appthibanglaixea1.database.dao.QuestionDao;
import com.example.appthibanglaixea1.database.entity.Answers;
import com.example.appthibanglaixea1.database.entity.ExamQuestion;
import com.example.appthibanglaixea1.database.entity.QuestionWithAnswers;
import com.example.appthibanglaixea1.database.entity.Questions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ThiThuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ThiThuAdapter adapter;
    private List<QuestionWithAnswers> questionList = new ArrayList<>();
    private AppDatabase db;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thi_thu);

        recyclerView = findViewById(R.id.recyclerViewQuestions);
        btnSubmit = findViewById(R.id.btnSubmit);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "driving_test_a1_database")
                .allowMainThreadQueries()
                .build();

        loadQuestions();

        btnSubmit.setOnClickListener(v -> gradeExam());
    }

    private void loadQuestions() {
        int examId = 1; // Lấy đề số 1
        List<ExamQuestion> examQuestions = db.examQuestionDao().getQuestionsByExamId(examId);

        for (ExamQuestion eq : examQuestions) {
            Questions q = db.questionDao().getQuestionById(eq.getQuestionId());
            List<Answers> answers = db.answerDao().getAnswersByQuestionId(q.getQuestionId());
            questionList.add(new QuestionWithAnswers(q, answers));
        }

        adapter = new ThiThuAdapter(this, questionList);
        recyclerView.setAdapter(adapter);
    }

    private void gradeExam() {
        int correct = 0;
        for (QuestionWithAnswers qwa : questionList) {
            int selectedId = adapter.getSelectedAnswers().getOrDefault(
                    qwa.getQuestion().getQuestionId(), -1);
            for (Answers ans : qwa.getAnswers()) {
                if (ans.getId() == selectedId && ans.isCorrect()) {
                    correct++;
                }
            }
        }
        Toast.makeText(this, "Đúng: " + correct + "/" + questionList.size(), Toast.LENGTH_LONG).show();
    }
}
