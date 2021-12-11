package com.mycompany.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterRequest implements Serializable {

    @ApiModelProperty(value = "Email Id", name = "email", dataType = "String", required = true, example = "John@gmail.com")
    private String email;
    @ApiModelProperty(value = "First Name", name = "firstName", dataType = "String", required = true, example = "John")
    private String firstName;
    @ApiModelProperty(value = "Last Name", name = "lastName", dataType = "String", required = true, example = "Doe")
    private String lastName;
    @ApiModelProperty(value = "Password", name = "password", dataType = "String", required = true, example = "my secret")
    private String password;
    @ApiModelProperty(value = "Phone Number", name = "phone", dataType = "String", required = true, example = "8984739733")
    private String phone;
}
