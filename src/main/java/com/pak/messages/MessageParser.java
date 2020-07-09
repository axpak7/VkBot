package com.pak.messages;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Структура входящего запроса
 * {
 * "type":"message_new",
 * "object":{
 *      "message":{
 *              "date":1593956138,
 *              "from_id":57821415,
 *              "id":5,
 *              "out":0,
 *              "peer_id":57821415,
 *              "text":"привет",
 *              "conversation_message_id":5,
 *              "fwd_messages":[],
 *              "important":false,
 *              "random_id":0,
 *              "attachments":[],
 *              "is_hidden":false},
 *        "client_info":{
 *              "button_actions":["text","vkpay","open_app","location","open_link"],
 *              "keyboard":true,"inline_keyboard":true,"carousel":false,"lang_id":0}},
 * "group_id":196756342,
 * "event_id":"6620fbc5a2989646e166e9802172b2b7e97659fc"
 * }
 * два основных поля:
 * from_id - для индентификации пользователя, кому будет отправлен ответ
 * text - входящее сообщение
 */


public class MessageParser{

    private String text;
    private String fromId;
    private String type;
    private static final Logger logger = LogManager.getLogger();

    /**
     * @param incomingJson - входящее сообщение пользователя для парсинга.
     */
    public MessageDto Parser(String incomingJson){
        MessageDto messageDto;
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(incomingJson);
        JsonObject jsonObject = jsonElement.getAsJsonObject(); // весь json
        type = jsonObject.get("type").getAsString(); // получает type
        if (type.equals("message_new")) {
            JsonObject rootJsonObject = jsonObject.getAsJsonObject("object"); // получаем Object
            JsonObject messageJsonObject = rootJsonObject.getAsJsonObject("message"); // получаем объект message
            fromId = messageJsonObject.get("from_id").getAsString();
            text = messageJsonObject.get("text").getAsString();
            logger.info("type: " + type + "; from_id: " + fromId + "; text: " + text);
            messageDto = new MessageDto(type, fromId, text);
        } else {
            logger.info("type: " + type + "; from_id: null; text: null");
            messageDto = new MessageDto(type);
        }
        return messageDto;
    }

    public String getText() {
        return text;
    }

    public String getFromId() {
        return fromId;
    }

    public String getType() {
        return type;
    }
}
