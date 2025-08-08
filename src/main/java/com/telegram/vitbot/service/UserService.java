package com.telegram.vitbot.service;

import com.telegram.vitbot.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserService {

    public User user;

    public Long chatId;

    public Map<Long ,User> userMap = new HashMap<>();

    public void addUser(Long chatId, User user, Map<Long, User> userMap){
        userMap.put(chatId,user);
    }

    public void deleteUser(Long chatId, Map<Long, User> userMap){
        userMap.remove(chatId);
    }

    public User getUser(Long chatId, Map<Long, User> userMap) {
        return userMap.get(chatId);
    }
}
