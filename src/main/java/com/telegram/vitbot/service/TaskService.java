package com.telegram.vitbot.service;

import com.telegram.vitbot.fuction.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskService {

    public Task task;

    public List<Task> taskList = new ArrayList<>();

    public void addTask(Task task) {
        taskList.add(task);
    }
}