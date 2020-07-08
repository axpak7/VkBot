package com.pak.messages;

public class MessageDto {
    private String text;
    private String fromId;
    private String type;

    public MessageDto(String type, String fromId, String text) {
        this.fromId = fromId;
        this.text = text;
        this.type = type;
    }

    public MessageDto(String type) {
        this.type = type;
        this.text = "";
        this.fromId = "";

    }

    public String getType() {
        return type;
    }

    public String getFromId() {
        return fromId;
    }

    public String getText() {
        return text;
    }
}
