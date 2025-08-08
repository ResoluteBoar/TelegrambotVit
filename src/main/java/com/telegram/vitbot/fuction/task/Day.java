package com.telegram.vitbot.fuction.task;

import com.telegram.vitbot.service.TaskService;

import java.util.Calendar;

public class Day {

    public TaskService taskService;

    public Calendar dateOfDay;

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public Calendar getDateOfDay() {
        return dateOfDay;
    }

    public void setDateOfDay(Calendar dateOfDay) {
        this.dateOfDay = dateOfDay;
    }



}
