package com.mycompany.userservice.utility;

import com.mycompany.userservice.entity.UserEntity;
import com.mycompany.userservice.model.UserRegisterRequest;
import com.mycompany.userservice.model.UserRegisterResponse;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserEntity registerModelToEntity(UserRegisterRequest userRegisterRequest)
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userRegisterRequest.getEmail());
        userEntity.setFirstName(userRegisterRequest.getFirstName());
        userEntity.setLastName(userRegisterRequest.getLastName());
        userEntity.setPassword(userRegisterRequest.getPassword());
        userEntity.setPhone(userRegisterRequest.getPhone());
        return userEntity;
    }
    public UserRegisterResponse registerEntityToModel(UserEntity userEntity)
    {
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setId(userEntity.getId());
        userRegisterResponse.setEmail(userEntity.getEmail());
        userRegisterResponse.setFirstName(userEntity.getFirstName());
        userRegisterResponse.setLastName(userEntity.getLastName());
        userRegisterResponse.setPhone(userEntity.getPhone());
        return userRegisterResponse;
    }
}
