package com.playdata.userservice.dto;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpForm {

    private String name;
    private String email;
    private String password;

}
