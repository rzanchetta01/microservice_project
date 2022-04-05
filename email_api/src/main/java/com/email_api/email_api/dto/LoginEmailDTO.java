package com.email_api.email_api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginEmailDTO {

    private String userEmail;
    private String userName;
    private String emailName;
}
