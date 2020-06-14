package ru.kpfu.itis.springbots.service;

import ru.kpfu.itis.springbots.models.User;

import java.util.List;

public interface UserService {

    void auth(User user);

    List<User> getUsers();

    void exit(User user);

    String getNickname(String id);

    boolean isAuth(StringBuilder nick, String id);

}
