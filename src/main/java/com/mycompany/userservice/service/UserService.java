package com.mycompany.userservice.service;

import com.mycompany.userservice.model.UserLogin;
import com.mycompany.userservice.exception.BusinessException;
import com.mycompany.userservice.model.UserRegisterRequest;
import com.mycompany.userservice.model.UserRegisterResponse;

public interface UserService {
    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) throws BusinessException;

    String loginUser(UserLogin userLogin) throws BusinessException;

    void sessionInvalidator();

    String checkToken(Long id) throws BusinessException;
}
