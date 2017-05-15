package com.wandoo.homework.base;

public class ValidationMessage {
    private String message;
    private String field;
    private MessageType type;

    public ValidationMessage() {
        super();
    }

    public ValidationMessage(MessageType type, String message, String field) {
        super();
        this.message = message;
        this.type = type;
        this.field = field;
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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}

