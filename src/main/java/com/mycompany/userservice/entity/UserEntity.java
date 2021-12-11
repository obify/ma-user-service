package com.mycompany.userservice.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_INFO")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id;
    @Column(name = "EMAIL_ID")
    private String email;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "PHONE_NUMBER")
    private String phone;
    @Column(name = "AUTHENTICATION_TOKEN")
    private String authToken;
    @Column(name = "LAST_LOGIN_TIME")
    private LocalDateTime lastLoginTime;
}
