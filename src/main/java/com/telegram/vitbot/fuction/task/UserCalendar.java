package com.telegram.vitbot.fuction.task;

import java.util.Calendar;
import java.util.Map;

public class UserCalendar {

    public Day day;

    public Map<Calendar,Day> dayMap;

    public void addDay(Map<Calendar,Day> dayMap, Day day){
        dayMap.put(day.getDateOfDay(),day);
    }

    public Day getDay(Map<Calendar,Day> dayMap, Day day) {
        return dayMap.get(day.getDateOfDay());
    }

    public void deleteDay(Map<Calendar,Day> dayMap, Day day){
        dayMap.remove(day.getDateOfDay());
    }
}
