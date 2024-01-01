package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {

    @Autowired
    ServiceRoom serviceRoom;

    @MessageMapping({"/hello/{roomId}"})
    @SendTo("/topic/room/{roomId}")
    public Greeting chatMsg(HelloMessage message, @DestinationVariable String roomId) {
        return new Greeting(HtmlUtils.htmlEscape(message.getName()));
    }


}
