package com.mycompany.userservice.exception;

import com.mycompany.userservice.model.ErrorModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BusinessException extends Exception implements Serializable {
    private final List<ErrorModel> ERROR_LIST;
}
