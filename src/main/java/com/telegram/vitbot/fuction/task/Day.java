package com.telegram.vitbot.fuction.task;

import com.telegram.vitbot.service.TaskServiceImpl;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Day {

    private TaskServiceImpl taskServiceImpl;

    private Calendar dateOfDay;

    public TaskServiceImpl getTaskService() {
        return taskServiceImpl;
    }

    public void setTaskService(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    public Calendar getDateOfDay() {
        return dateOfDay;
    }

    public void setDateOfDay(String dateDay) {

        String[] date = dateDay.split("\\.");

        this.dateOfDay = new GregorianCalendar(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
    }



}
