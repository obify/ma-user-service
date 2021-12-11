package com.mycompany.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ErrorModel implements Serializable {
    private String errorCode;
    private String errorMessage;
}
