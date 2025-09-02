package com.telegram.vitbot.service;

import com.telegram.vitbot.user.User;

public interface UserServInterface {

    public void addUser(Long chatId, User user);

    public void deleteUser(Long chatId);

    public User getUser(Long chatId);
}
