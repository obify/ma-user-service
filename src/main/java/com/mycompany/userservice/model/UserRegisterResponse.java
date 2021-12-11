package com.mycompany.userservice.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponse implements Serializable {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
}
