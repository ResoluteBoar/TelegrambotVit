package com.telegram.vitbot.user;


import com.telegram.vitbot.fuction.task.UserCalendar;

public class User {

    public long chatId;

    public int age;

    public String firstName;

    public UserCalendar userCalendar;

    public UserCalendar getUserCalendar() {
        return userCalendar;
    }

    public void setUserCalendar(UserCalendar userCalendar) {
        this.userCalendar = userCalendar;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
