package com.userloginapi.userloginapi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String login;
    private String password;
    private String email;


}
