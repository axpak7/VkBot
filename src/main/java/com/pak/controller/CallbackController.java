package com.pak.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pak.messageHandler.MessageGeneration;
import com.pak.messageHandler.MessageParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

@Controller
public class CallbackController {
    private static final String ACCESS_TOKEN = "f910156aaae4c3d6f11096d0b1974126bc16ce332788311e66926379cfe0dada82e9ef82183c2651a601c";
    private static final String CALLBACK_API_CONFIRMATION_TOKEN = "89d1a0f5";
    private static final String CALLBACK_API_EVENT_CONFIRMATION = "confirmation";
    private static final String CALLBACK_API_EVENT_MESSAGE_NEW = "message_new";

    HttpClient client;
    HttpGet httpGet;

    private MessageGeneration messageGeneration;
    private MessageParser messageParser;

    private final Random random = new Random();

    private String vkApiMessageSendMethod = "https://api.vk.com/method/messages.send?";

    @RequestMapping(value = "/callback", method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String botResponse(@RequestBody String incomingMessage) {
        if (incomingMessage != null) {
            // проверка json
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(incomingMessage);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            System.out.println(jsonElement.toString());

            // получаем тип запроса
            String type = jsonObject.get("type").getAsString();
            System.out.println(type);
            if (type.equals(CALLBACK_API_EVENT_CONFIRMATION)) {
                // при добавлении сервера к Callback API будет отправлен POST запрос, содержащий json { "type": "confirmation", "group_id": 196756342 }
                // сервер должен вернуть строку CALLBACK_API_CONFIRMATION_TOKEN
                return CALLBACK_API_CONFIRMATION_TOKEN;
            } else if (type.equals(CALLBACK_API_EVENT_MESSAGE_NEW)) {
                // получаю user_id и message
                messageParser = new MessageParser(incomingMessage);
                String userId = messageParser.getUserId();
                String message = messageParser.getMessage();
                System.out.println(userId);
                System.out.println(message);
                // генерация сообщения бота
                messageGeneration = new MessageGeneration();
                String botAnswer = buildBotResponse(userId, messageGeneration.getBotAnswer(message));
                System.out.println(botAnswer);

                try {
                    client = HttpClientBuilder.create().build();
                    httpGet = new HttpGet(botAnswer);
                    httpGet.addHeader("accept", "application/x-www-form-urlencoded");

                    HttpResponse response = client.execute(httpGet);

                    BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    String output;
                    System.out.println("Output from server ... \n");
                    while ((output = br.readLine()) != null) {
                        System.out.println(output);
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return "ok";
    }

    private String buildBotResponse(String userId, String message) {
        return (vkApiMessageSendMethod + "user_id=" + userId + "&message=" + message + "&random_id=" + random.nextInt()+"&access_token="+ ACCESS_TOKEN +"&v=5.120");
    }
}
