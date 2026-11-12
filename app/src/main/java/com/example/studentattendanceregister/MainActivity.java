package com.example.studentattendanceregister;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText studentNameInput;
    Button addStudentButton;
    TextView attendanceSummary;
    ListView studentListView;

    ArrayList<String> students = new ArrayList<>();
    ArrayList<Boolean> attendance = new ArrayList<>();

    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentNameInput = findViewById(R.id.studentNameInput);
        addStudentButton = findViewById(R.id.addStudentButton);
        attendanceSummary = findViewById(R.id.attendanceSummary);
        studentListView = findViewById(R.id.studentListView);

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return students.size();
            }

            @Override
            public Object getItem(int position) {
                return students.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView,
                                ViewGroup parent) {

                LinearLayout row = new LinearLayout(MainActivity.this);
                row.setOrientation(LinearLayout.VERTICAL);
                row.setPadding(12, 12, 12, 12);

                TextView name = new TextView(MainActivity.this);
                name.setText(students.get(position));
                name.setTextSize(18);

                Button statusButton = new Button(MainActivity.this);

                if (attendance.get(position)) {
                    statusButton.setText("Present");
                } else {
                    statusButton.setText("Absent");
                }

                statusButton.setOnClickListener(v -> {
                    attendance.set(position, !attendance.get(position));
                    notifyDataSetChanged();
                    updateSummary();
                });

                row.addView(name);
                row.addView(statusButton);

                return row;
            }
        };

        studentListView.setAdapter(adapter);

        addStudentButton.setOnClickListener(v -> {
            String name = studentNameInput.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Enter student name",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            students.add(name);
            attendance.add(false);

            studentNameInput.setText("");

            adapter.notifyDataSetChanged();
            updateSummary();
        });

        updateSummary();
    }

    private void updateSummary() {
        int total = students.size();
        int present = 0;

        for (boolean status : attendance) {
            if (status) {
                present++;
            }
        }

        int absent = total - present;

        attendanceSummary.setText(
                "Total: " + total +
                        " | Present: " + present +
                        " | Absent: " + absent
        );
    }
}