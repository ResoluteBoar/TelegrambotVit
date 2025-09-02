package com.telegram.vitbot.service;

import com.telegram.vitbot.user.User;

import java.util.HashMap;
import java.util.Map;

public class UserService implements UserServInterface{

    private User user;

    private Long chatId;


    public Map<Long ,User> userMap = new HashMap<>();

    public void addUser(Long chatId, User user){
        userMap.put(chatId,user);
    }

    public void deleteUser(Long chatId){
        userMap.remove(chatId);
    }

    public User getUser(Long chatId) {
        return userMap.get(chatId);
    }
}
