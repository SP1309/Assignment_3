// MainActivity.java
package com.example.assignment_3;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText taskTitle, taskDescription, taskDate;
    public static List<Task> taskList = new ArrayList<>();
    private RecyclerView recycleView;
    private TaskAdapter taskAdapter;

    private Button saveTaskButton, showUpcomingTask;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskTitle = findViewById(R.id.taskTitle);
        taskDescription = findViewById(R.id.taskDescription);

        saveTaskButton = findViewById(R.id.saveTaskButton);
        showUpcomingTask = findViewById(R.id.showUpcomingTask);

        taskDate = findViewById(R.id.taskDate);

        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
                intent.putExtra("taskTitle", taskTitle.getText().toString());
                intent.putExtra("taskDescription", taskDescription.getText().toString());
                intent.putExtra("Deadline", taskDate.getText().toString());
                taskList.add(new Task(taskTitle.getText().toString(), taskDescription.getText().toString(), taskDate.getText().toString()));
                startActivity(intent);
            }
        });

        showUpcomingTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadTaskListFragment();
            }
        });
    }

    private void loadTaskListFragment() {
        TaskListFragment taskListFragment = new TaskListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, taskListFragment); // Replace or add the fragment
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void showDatePickerDialog() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Update the EditText with the selected date
                        taskDate.setText(String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay));
                    }
                }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

}

class Task {
    String title;
    String description;
    String deadline;

    public Task(String title, String description, String deadline) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }
}
