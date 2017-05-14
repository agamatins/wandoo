package com.wandoo.homework.base;

public class ValidationMessage {
    private String message;
    private MessageType type;

    public ValidationMessage() {
        super();
    }

    public ValidationMessage(MessageType type, String message) {
        super();
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}

