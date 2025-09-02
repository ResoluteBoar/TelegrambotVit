package com.telegram.vitbot.service;

import com.telegram.vitbot.fuction.task.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskServiceImpl implements TaskService{

    private Task task;

    private List<Task> taskList = new ArrayList<>();


    public void addTask(Task task) {
        taskList.add(task);
    }

}