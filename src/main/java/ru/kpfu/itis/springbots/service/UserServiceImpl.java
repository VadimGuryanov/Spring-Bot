package ru.kpfu.itis.springbots.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.springbots.models.User;
import ru.kpfu.itis.springbots.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void auth(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void exit(User user) {
        userRepository.delete(user);
    }

    @Override
    public String getNickname(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(User::getNickname).orElse("");
    }

    @Override
    public boolean isAuth(StringBuilder nick, String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return true;
        } else {
            Optional<User> user_nick = userRepository.findUserByNickname(nick.toString());
            return user_nick.isPresent();
        }
    }

}
