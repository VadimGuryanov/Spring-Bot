package ru.kpfu.itis.springbots.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.kpfu.itis.springbots.bot.BotInterface;
import ru.kpfu.itis.springbots.dto.GameMessage;
import ru.kpfu.itis.springbots.helper.MessageHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketMessageHandler extends TextWebSocketHandler implements WebSocketInterface {

    private static final Map<String, WebSocketSession> map = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageHelper messageHelper;

    @Autowired
    private BotInterface botInterface;

    @Override
    public void handleMessage(@NotNull WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageText = (String) message.getPayload();
        GameMessage messageFromJson = objectMapper.readValue(messageText, GameMessage.class);

        if(!map.containsKey(messageFromJson.getFrom())) {
            map.put(messageFromJson.getFrom(), session);
        }

        GameMessage user = new GameMessage();
        user.setFrom(messageFromJson.getFrom());
        String nick = messageHelper.isAuth(messageFromJson.getFrom());
        if (!nick.equals("")) {
            user.setMessage(nick + ": " + messageFromJson.getMessage());
        } else {
            user.setMessage("Аноним: " + messageFromJson.getMessage());
        }
        for (WebSocketSession currentSession: map.values()) {
            currentSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(user)));
        }

        GameMessage serverMess = new GameMessage();
        String s = messageHelper.getMessage(messageFromJson.getMessage(), messageFromJson.getFrom());
        if (s.isEmpty()) return;
        serverMess.setMessage("Сервер: " + s);
        serverMess.setFrom(messageFromJson.getFrom());
        for (WebSocketSession currentSession: map.values()) {
            currentSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(serverMess)));
        }
        botInterface.sendMessage(serverMess);
    }

    @Override
    public void sendMessage(GameMessage mess) {
        for (WebSocketSession currentSession: map.values()) {
            try {
                currentSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(mess)));
            } catch (IOException e) {
                //логер бы
                System.out.println(e.getMessage());
            }
        }
    }
}
