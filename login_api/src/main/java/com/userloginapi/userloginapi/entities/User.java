package com.userloginapi.userloginapi.entities;

import com.userloginapi.userloginapi.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "user")
@Getter
@Setter
@ToString
public class User {

    @Id
    private String id;
    private String login;
    private String password;
    private String email;

    public User() {
        this.id = UUID.randomUUID().toString();
    }

    public User(UserDTO user) {
        this.id = UUID.randomUUID().toString();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }

    public User(String errorMenssage) {
        this.login = errorMenssage;
    }
}
