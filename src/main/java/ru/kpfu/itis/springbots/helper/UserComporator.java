package ru.kpfu.itis.springbots.helper;

import ru.kpfu.itis.springbots.models.User;

import java.util.Comparator;

public class UserComporator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        return (int)(o2.getBackword() - o1.getBackword());
    }

}
