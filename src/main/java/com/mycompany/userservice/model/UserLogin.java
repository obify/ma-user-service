package com.mycompany.userservice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {

    /*@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;*/
    private String email;
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String phone;
    /*@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String authToken;*/
}
