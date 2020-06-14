package ru.kpfu.itis.springbots.handler;

import ru.kpfu.itis.springbots.dto.GameMessage;

public interface WebSocketInterface {

    void sendMessage(GameMessage mess);

}
