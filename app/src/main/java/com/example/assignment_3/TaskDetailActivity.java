// TaskDetailActivity.java
package com.example.assignment_3;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView taskTitleView, taskDescriptionView;
    private final String CHANNEL_ID = "task_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        taskTitleView = findViewById(R.id.taskTitleView);
        taskDescriptionView = findViewById(R.id.taskDescriptionView);

        String taskTitle = getIntent().getStringExtra("taskTitle");
        String taskDescription = getIntent().getStringExtra("taskDescription");

        taskTitleView.setText(taskTitle);
        taskDescriptionView.setText(taskDescription);

        // Request notification permission if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        1);
            } else {
                // Permission already granted, proceed with notification
                createNotificationChannel();
                showNotification(taskTitle, taskDescription);
            }
        } else {
            // No need to request permission, proceed with notification
            createNotificationChannel();
            showNotification(taskTitle, taskDescription);
        }

        createNotificationChannel();
        showNotification(taskTitle, taskDescription);
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Task Notification Channel";
            String description = "Channel for Task notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(String taskTitle, String taskDescription) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Task Reminder: " + taskTitle)
                .setContentText(taskDescription)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}
