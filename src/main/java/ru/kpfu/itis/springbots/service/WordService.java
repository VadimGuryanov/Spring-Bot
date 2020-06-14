package ru.kpfu.itis.springbots.service;

import ru.kpfu.itis.springbots.models.Word;

public interface WordService {

    boolean guess(String word, String nick);

    String go();

    boolean isStarted();

    String getDescription();

    String giveUp();
}
