package com.telegram.vitbot.service;

import com.telegram.vitbot.fuction.task.Task;

import java.util.Map;

public class TaskService {

    public Task task;

    public Map<String, Task> taskMap;

    public void addTask(Map<String, Task> taskMap, Task task) {
        taskMap.put(task.getTaskName(), task);
    }

    public void deleteTask(Map<String, Task> taskMap, String taskName) {
        taskMap.remove(taskName);
    }

    public Task getTask(Map<String, Task> taskMap, String taskName) {
        return taskMap.get(taskName);
    }
}