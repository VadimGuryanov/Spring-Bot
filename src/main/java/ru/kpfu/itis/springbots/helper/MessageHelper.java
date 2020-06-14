package ru.kpfu.itis.springbots.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.springbots.models.User;
import ru.kpfu.itis.springbots.service.UserService;
import ru.kpfu.itis.springbots.service.WordService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageHelper {

    @Autowired
    private UserService userService;

    @Autowired
    private WordService wordService;

    public String getMessage(String mess, String id) {
        String message = "";
        String[] part = mess.split(" ");
        if (part.length > 0) {
            switch (part[0]) {
                case "/auth": {
                    message = auth(part, id);
                    break;
                }
                case "/help": {
                    message = help();
                    break;
                }
                case "/word": {
                    message = word(part, id);
                    break;
                }
                case "/stars": {
                    message = stars();
                    break;
                }
                case "/go": {
                    message = go();
                    break;
                }
                case "/des": {
                    message = description();
                    break;
                }
                case "/giveup": {
                    message = giveUp();
                    break;
                }
                default: {
                    break;
                }
            }
        }
        return message;
    }

    private String giveUp() {
        return wordService.giveUp();
    }

    private String description() {
        return wordService.getDescription();
    }

    private String go() {
        return wordService.go();
    }

    private String help() {
        return "Правила: " +
                "Вы должны угадать слово по описанию. Ввводить по слову, а не по букве." +
                "\nКоманды:\n" +
                "1. /help - правила, описание команд\n" +
                "2. /stars - игроки с наибльшим числом отгадывания\n" +
                "3. /auth - регистрация игрока, должне ввести никнейм через пробел после команды\n" +
                "4. /word - через пробел после команды вводиться слово или группа слов.\n" +
                "5. /go - начать игру\n" +
                "6. /des - попросить описание слова\n" +
                "7. /giveup - сдаться.";
    }

    private String stars() {
        String stars = "";
        List<User> users = userService.getUsers();
        if (users == null || users.isEmpty()) {
            return "Пользователей пока нет";
        } else {
            List<String> s = users
                    .stream()
                    .sorted(new UserComporator())
                    .map( u -> u.getNickname() + ": " + u.getBackword() + " отгадано")
                    .limit(10)
                    .collect(Collectors.toList());
            for (int i = 0; i < s.size(); i++) {
                stars += "\n" + (i+1) + ". " + s.get(i);
            }
        }
        return stars;
    }

    private String auth(String[] part, String id) {
        if (part.length > 1) {
            User user = new User();
            user.setBackword(0);
            user.setId(id);
            StringBuilder nick = new StringBuilder();
            for (int i = 1; i < part.length; i++) {
                nick.append(part[i]).append(" ");
            }
            user.setNickname(nick.toString());
            if (!userService.isAuth(nick, id)) {
                userService.auth(user);
                return "Пользовательно успешно зарегистрирован - " + userService.getNickname(id);
            } else {
                return "Пользовательно уже зарегистрирован - " + nick;
            }
        } else {
            return "после команды должен следовать никнейм";
        }
    }

    private String word(String[] part, String id) {
        String nick = userService.getNickname(id);
        if (wordService.isStarted()) {
            if (!nick.equals("")) {
                if (part.length > 1) {
                    StringBuilder word = new StringBuilder();
                    for (int i = 1; i < part.length; i++) {
                        word.append(part[i]).append(" ");
                    }
                    if (wordService.guess(word.toString(), nick)) {
                        return "слово - " + word + "\nОтгадал - " + nick;
                    } else {
                        return "слово неотгаданно - " + nick;
                    }
                } else {
                    return "после команды должено следовать слово или слова";
                }
            } else {
                return "чтобы играть нужно зарегистрироваться";
            }
        } else {
            return "игра еще не началась";
        }
    }

    public String isAuth(String id) {
        return userService.getNickname(id);
    }

}
