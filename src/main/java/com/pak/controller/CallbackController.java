package com.pak.controller;

import com.pak.messages.MessageDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.pak.messages.MessageGeneration;
import com.pak.messages.MessageParser;

import java.util.Random;

@RestController
public class CallbackController {
    @Value("${ACESS_TOKEN}")
    private String ACCESS_TOKEN;
    @Value("${CALLBACK_API_CONFIRMATION_TOKEN}")
    private String CALLBACK_API_CONFIRMATION_TOKEN;

    @Autowired
    RestTemplate restTemplate;

    private static final String CALLBACK_API_EVENT_CONFIRMATION = "confirmation";
    private static final String CALLBACK_API_EVENT_MESSAGE_NEW = "message_new";

    private MessageGeneration messageGeneration;
    private MessageParser messageParser;
    private MessageDto messageDto;

    private final Random random = new Random();

    private static final Logger logger = LogManager.getLogger();

    private String vkApiMessageSendMethod = "https://api.vk.com/method/messages.send";

    @RequestMapping(value = "/callback", method = RequestMethod.POST, consumes = {"application/json"})
    public @ResponseBody String botResponse(@RequestBody String incomingMessage) {
        if (incomingMessage != null) {
            messageParser = new MessageParser();
            messageDto = messageParser.Parser(incomingMessage);
            String type = messageDto.getType();

            if (type.equals(CALLBACK_API_EVENT_CONFIRMATION)) {
                // при добавлении сервера к Callback API будет отправлен POST запрос, содержащий json { "type": "confirmation", "group_id": 196756342 }
                // сервер должен вернуть строку CALLBACK_API_CONFIRMATION_TOKEN
                return CALLBACK_API_CONFIRMATION_TOKEN;
            } else if (type.equals(CALLBACK_API_EVENT_MESSAGE_NEW)) {
                // получаю user_id и message
                String userId = messageDto.getFromId();
                String message = messageDto.getText();
                // генерация сообщения бота
                messageGeneration = new MessageGeneration();
                String botAnswer = buildBotResponse(userId, messageGeneration.getBotAnswer(message));
                logger.info(botAnswer);

                try {
                    restTemplate.getForObject(botAnswer, String.class);
                } catch (Exception ex) {
                    logger.error("Error message: ", ex);
                }
            }
        }
        return "ok";
    }

    private String buildBotResponse(String userId, String message) {
        return (vkApiMessageSendMethod + "?user_id=" + userId + "&message=" + message + "&random_id=" + random.nextInt()+"&access_token="+ ACCESS_TOKEN +"&v=5.120");
    }
}
