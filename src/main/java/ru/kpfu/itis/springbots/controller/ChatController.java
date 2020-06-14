package ru.kpfu.itis.springbots.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Controller
public class ChatController {

    @RequestMapping(method = RequestMethod.GET, path = "/chat")
    public String getChatPage(ModelMap map) {
        map.addAttribute("pageId", UUID.randomUUID().toString());
        return "chat";
    }

}
