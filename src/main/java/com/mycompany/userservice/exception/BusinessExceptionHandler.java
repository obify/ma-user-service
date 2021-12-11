package com.mycompany.userservice.exception;

import com.mycompany.userservice.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@ControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleListOfBusinessException(BusinessException be, WebRequest req)
    {
        List<ErrorModel> errorModelList = be.getERROR_LIST();
        StringBuilder errorMessage = new StringBuilder();

        for (int i = 0; i < errorModelList.size(); i++)
        {
            ErrorModel errorModel = errorModelList.get(i);
            errorMessage.append(errorModel.getErrorCode() + " - " + errorModel.getErrorMessage());
            if(i<errorModelList.size()-1)
                errorMessage.append("\n");
        }
        if(errorMessage.toString().contains("REG")) {
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.CONFLICT);
        }
        else if(errorMessage.toString().contains("LOG")) {
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.UNAUTHORIZED);
        }
        else if(errorMessage.toString().contains("AUTH")) {
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        else
        {
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
