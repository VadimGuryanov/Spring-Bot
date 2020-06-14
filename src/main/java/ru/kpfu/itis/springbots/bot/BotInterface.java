package ru.kpfu.itis.springbots.bot;

import ru.kpfu.itis.springbots.dto.GameMessage;

public interface BotInterface {

    void sendMessage(GameMessage mess);

}
