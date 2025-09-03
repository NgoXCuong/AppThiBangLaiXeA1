package com.example.appthibanglaixea1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.appthibanglaixea1.R;
import com.example.appthibanglaixea1.database.AppDatabase;
import com.example.appthibanglaixea1.database.dao.UserDao;
import com.example.appthibanglaixea1.database.entity.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Gán ID đúng cho layout gốc
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LinearLayout layoutClick = findViewById(R.id.layoutClick);

        // Gắn sự kiện click
        layoutClick.setOnClickListener(v -> {
            // Chuyển sang trang khác
            Intent intent = new Intent(MainActivity.this, ChuDeActivity.class);
            startActivity(intent);
        });

//        // Bắt sự kiện click Settings
//        ImageView btnSettings = findViewById(R.id.cardSettings);
//        btnSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//                startActivity(intent);
//            }
//        });

        TextView textView = findViewById(R.id.textViewFullName);

// Giả sử bạn có AppDatabase
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "driving_test_a1_database")
                .allowMainThreadQueries() // ⚠️ chỉ dùng cho test, thực tế nên dùng AsyncTask/Executor
                .build();

        UserDao userDao = db.userDao();

// Lấy user theo id (ví dụ id = 1)
        User user = userDao.getUserById(1);

        if (user != null) {
            textView.setText(user.getFullName());
        }
    }
}
