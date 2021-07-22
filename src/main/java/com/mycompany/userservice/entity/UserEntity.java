package com.mycompany.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;

}
