package com.example.assignment_3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_3.MainActivity;
import com.example.assignment_3.R;
import com.example.assignment_3.TaskAdapter;

public class TaskListFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Use the static taskList from MainActivity
        taskAdapter = new TaskAdapter(MainActivity.taskList);
        recyclerView.setAdapter(taskAdapter);

        return view;
    }
}
