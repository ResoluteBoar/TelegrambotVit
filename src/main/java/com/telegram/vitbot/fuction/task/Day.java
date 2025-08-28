package com.telegram.vitbot.fuction.task;

import com.telegram.vitbot.service.TaskService;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

    public void setDateOfDay(String dateDay) {

        String[] date = dateDay.split("\\.");

        this.dateOfDay = new GregorianCalendar(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
    }



}
