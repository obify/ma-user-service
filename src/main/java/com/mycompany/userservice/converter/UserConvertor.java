package com.mycompany.userservice.converter;

import com.mycompany.userservice.dto.UserDto;
import com.mycompany.userservice.entity.UserEntity;

public class UserConvertor {

    public UserDto entityToDto(UserEntity userEntity){
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setPassword(userEntity.getPassword());
        userDto.setPhone(userEntity.getPhone());
        return userDto;
    }

    public UserEntity dtoToEntity(UserDto userDto){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setPhone(userDto.getPhone());
        return userEntity;
    }
}
