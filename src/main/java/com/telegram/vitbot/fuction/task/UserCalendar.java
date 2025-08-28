package com.telegram.vitbot.fuction.task;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserCalendar {

    public Day day;

    public Map<Calendar,Day> dayMap = new HashMap<>();

    public void addDay(Calendar calendar){
        dayMap.put(calendar,day);
    }

    public Day getDay(Calendar date) {
        return dayMap.get(date);
    }

    public void deleteDay(Map<Calendar,Day> dayMap, Day day){
        dayMap.remove(day.getDateOfDay());
    }
}
