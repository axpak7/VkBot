package com.pak.messages;

public class MessageGeneration {

    public MessageGeneration() {}

    /**
     * @param message - входящее сообщение
     * @return Высказали: message
     */
    public String getBotAnswer(String message) {
        String answer = "Вы сказали: ";
        //return URLEncoder.encode(answer + message);
        return (answer + message);
    }
}
