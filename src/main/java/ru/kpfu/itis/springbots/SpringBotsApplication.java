package ru.kpfu.itis.springbots;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.kpfu.itis.springbots.bot.BotListener;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class SpringBotsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBotsApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ListenerAdapter getListener() {
        return new BotListener();
    }

    @Bean
    public JDA getJDA() throws LoginException {
        return new JDABuilder("NzEwMTc1MDE3MTEwOTk1MDA1.XrwojA.bhqmuFnnDiwetXt-XQko9McGjI0").build();
    }

    @Override
    public void run(String... args) throws Exception {
        JDA jda = getJDA();
        jda.addEventListener(getListener());
    }
}
