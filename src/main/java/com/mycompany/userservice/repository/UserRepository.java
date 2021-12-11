package com.mycompany.userservice.repository;

import com.mycompany.userservice.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> findByEmailOrPhone(String email, String phone);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);
    Optional<UserEntity> findByPhoneAndPassword(String email, String password);
    List<UserEntity> findByAuthTokenNotNull();
}
