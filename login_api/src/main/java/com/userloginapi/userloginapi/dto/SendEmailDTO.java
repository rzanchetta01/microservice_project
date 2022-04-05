package com.userloginapi.userloginapi.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailDTO {

    private String userEmail;
    private String userName;
    private String emailName;
}
