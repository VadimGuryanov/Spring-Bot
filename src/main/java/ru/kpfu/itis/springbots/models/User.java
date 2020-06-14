package ru.kpfu.itis.springbots.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private String id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private long backword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getBackword() {
        return backword;
    }

    public void setBackword(long backword) {
        this.backword = backword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return backword == user.backword &&
                Objects.equals(id, user.id) &&
                Objects.equals(nickname, user.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, backword);
    }

}
