package com.playdata.userservice.dto;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginForm {

    private String email;
    private String password;

}
