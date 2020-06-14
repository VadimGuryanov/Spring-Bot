package ru.kpfu.itis.springbots.dto;

import java.util.Objects;

public class GameMessage {
    private String message;
    private String from;
    private String author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameMessage message1 = (GameMessage) o;
        return Objects.equals(message, message1.message) &&
                Objects.equals(from, message1.from) &&
                Objects.equals(author, message1.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, from, author);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
