package ru.kpfu.itis.springbots.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.springbots.models.User;
import ru.kpfu.itis.springbots.models.Word;
import ru.kpfu.itis.springbots.repository.UserRepository;
import ru.kpfu.itis.springbots.repository.WordRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WordServiceImpl implements WordService {

    private String serverWord;
    private String description;
    private List<String> backwards;

    public WordServiceImpl() {
        backwards = new ArrayList<>();
    }

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean guess(String word, String nick) {
        System.out.print(word);
        System.out.print(serverWord);
        if (word.toLowerCase().trim().equals(serverWord.toLowerCase().trim())) {
            Optional<User> user = userRepository.findUserByNickname(nick);
            if (user.isPresent()) {
                User u = user.get();
                userRepository.delete(u);
                u.setBackword(u.getBackword() + 1);
                userRepository.save(u);
                backwards.add(serverWord);
                serverWord = null;
                description = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public String go() {
        if (serverWord == null) {
            List<Word> words = wordRepository.findAll();
            if (words.size() == backwards.size()) backwards.clear();
            words = words.stream()
                    .filter(word -> !backwards.contains(word.getName()))
                    .collect(Collectors.toList());
            int i = (int)(Math.random() * words.size());
            serverWord = words.get(i).getName();
            description = words.get(i).getDescription();
            System.out.println(serverWord);
            return "\nОписание: " + description;
        } else {
            return "Игра уже идет";
        }
    }

    @Override
    public boolean isStarted() {
        return serverWord != null;
    }

    @Override
    public String getDescription() {
        if (description == null) {
            return "игра еще не началась";
        }
        return "\nОписание: " + description;
    }

    @Override
    public String giveUp() {
        if (serverWord == null) {
            return "игра еще не началась";
        }
        String s = "Вы сдались:\n Верное слово - " + serverWord;
        serverWord = null;
        description = null;
        return s;
    }

}
