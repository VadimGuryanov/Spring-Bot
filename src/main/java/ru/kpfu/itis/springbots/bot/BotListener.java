package ru.kpfu.itis.springbots.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.springbots.dto.GameMessage;
import ru.kpfu.itis.springbots.handler.WebSocketInterface;
import ru.kpfu.itis.springbots.helper.MessageHelper;

@Component
public class BotListener extends ListenerAdapter implements BotInterface {

    @Autowired
    private MessageHelper helper;

    @Autowired
    private WebSocketInterface webSocketInterface;

    @Autowired
    private JDA jda;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        String[] part = content.split(" ");
        if (part[0].equals("/auth") && part.length == 1) content += " " + event.getAuthor().getName();
        String serverMess = helper.getMessage(content, event.getAuthor().getId());
        System.out.println(serverMess);
        if (!serverMess.isEmpty()) {
            event.getJDA().getTextChannels().forEach(textChannel -> {
                textChannel.sendMessage(serverMess).queue();
            });
        }
        GameMessage m = new GameMessage();
        m.setMessage("Сервер:\n" + serverMess.trim());
        webSocketInterface.sendMessage(m);
    }

    @Override
    public void sendMessage(GameMessage mess) {
        jda.getTextChannels().forEach(textChannel -> {
            textChannel.sendMessage(mess.getMessage()).queue();
        });
    }

}
