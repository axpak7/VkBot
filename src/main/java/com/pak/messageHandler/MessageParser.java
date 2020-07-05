package com.pak.messageHandler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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


public class MessageParser {

    private JsonParser parser;
    private JsonElement jsonElement;
    private JsonObject jsonObject;
    private JsonObject rootJsonObject;
    private JsonObject messageJsonObject;

    private String message;
    private String userId;
    private String type;

    /**
     * @param incomingJson - входящее сообщение пользователя для парсинга.
     */
    public MessageParser(String incomingJson) {
        parser = new JsonParser();
        jsonElement = parser.parse(incomingJson);
        jsonObject = jsonElement.getAsJsonObject();
        rootJsonObject = jsonObject.getAsJsonObject("object"); // получаем Object
        messageJsonObject = rootJsonObject.getAsJsonObject("message"); // получаем объект message

        this.type = jsonObject.get("type").getAsString();
        this.message = messageJsonObject.get("text").getAsString();
        this.userId = messageJsonObject.get("from_id").getAsString();
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }
}
