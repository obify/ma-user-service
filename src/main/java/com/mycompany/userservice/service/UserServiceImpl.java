package com.mycompany.userservice.service;

import com.mycompany.userservice.model.UserLogin;
import com.mycompany.userservice.entity.UserEntity;
import com.mycompany.userservice.model.ErrorModel;
import com.mycompany.userservice.exception.BusinessException;
import com.mycompany.userservice.model.UserRegisterRequest;
import com.mycompany.userservice.model.UserRegisterResponse;
import com.mycompany.userservice.repository.UserRepository;
import com.mycompany.userservice.utility.UserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final Long TIME_DURATION = 600L;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) throws BusinessException {

        List<ErrorModel> errorModelList = new ArrayList<>();
        ErrorModel errorModel = null;

        if(userRegisterRequest.getEmail()==null || "".equals(userRegisterRequest.getEmail().trim()))
        {
            errorModel = new ErrorModel("REG_001", "User Email Id is a required field!");
            errorModelList.add(errorModel);
        }
        if(userRegisterRequest.getFirstName()==null || "".equals(userRegisterRequest.getFirstName().trim()))
        {
            errorModel = new ErrorModel("REG_002", "User First Name is a required field!");
            errorModelList.add(errorModel);
        }
        if(userRegisterRequest.getLastName()==null || "".equals(userRegisterRequest.getLastName().trim()))
        {
            errorModel = new ErrorModel("REG_003", "User Last Name is a required field!");
            errorModelList.add(errorModel);
        }
        if(userRegisterRequest.getPassword()==null || "".equals(userRegisterRequest.getPassword().trim()))
        {
            errorModel = new ErrorModel("REG_004", "User Password is a required field!");
            errorModelList.add(errorModel);
        }
        if(userRegisterRequest.getPhone()==null || "".equals(userRegisterRequest.getPhone().trim()))
        {
            errorModel = new ErrorModel("REG_005", "User Phone Number is a required field!");
            errorModelList.add(errorModel);
        }

        if(errorModelList.isEmpty())
        {
            userRegisterRequest.setEmail(userRegisterRequest.getEmail().trim());
            userRegisterRequest.setFirstName(userRegisterRequest.getFirstName().trim());
            userRegisterRequest.setLastName(userRegisterRequest.getLastName().trim());
            userRegisterRequest.setPassword(userRegisterRequest.getPassword().trim());
            userRegisterRequest.setPhone(userRegisterRequest.getPhone().trim());

            UserEntity userEntity = userConverter.registerModelToEntity(userRegisterRequest);
            List<UserEntity> userEntityList = userRepository.findByEmailOrPhone(userRegisterRequest.getEmail(), userRegisterRequest.getPhone());
            if(userEntityList.isEmpty())
            {
                LOGGER.info("User registered successfully!");
                userEntity = userRepository.save(userEntity);
                return userConverter.registerEntityToModel(userEntity);
            }
            else
            {
                LOGGER.error("User already exists with the same Email Id and/or Phone Number!");
                errorModel = new ErrorModel("REG_006", "User already exists with the same Email Id and/or Phone Number!");
                errorModelList.add(errorModel);
                throw new BusinessException(errorModelList);
            }
        }
        else
        {
            LOGGER.error("One or more mandatory field(s) are missing!");
            throw new BusinessException(errorModelList);
        }
    }

    @Override
    public String loginUser(UserLogin userLogin) throws BusinessException {

        List<ErrorModel> errorModelList = new ArrayList<>();
        ErrorModel errorModel = null;

        if((userLogin.getEmail() == null || "".equals(userLogin.getEmail().trim())) && (userLogin.getPhone() ==null || "".equals(userLogin.getPhone().trim())))
        {
            errorModel = new ErrorModel("LOG_001", "Both Email id and Phone number not provided, Please provide either Email Id or Phone Number to Login!");
            errorModelList.add(errorModel);
        }
        if((userLogin.getEmail() != null && !("".equals(userLogin.getEmail().trim()))) && (userLogin.getPhone() !=null && !("".equals(userLogin.getPhone().trim()))))
        {
            errorModel = new ErrorModel("LOG_002", "Both Email id and Phone number provided, Please provide either Email Id or Phone Number to Login!");
            errorModelList.add(errorModel);
        }
        if(userLogin.getPassword() == null || "".equals(userLogin.getPassword().trim()))
        {
            errorModel = new ErrorModel("LOG_003", "Password is a required field!");
            errorModelList.add(errorModel);
        }

        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        if(errorModelList.isEmpty())
        {
            Optional<UserEntity> optUserEntity;

            if(userLogin.getEmail()!=null)
            {
                optUserEntity = userRepository.findByEmailAndPassword(userLogin.getEmail(), userLogin.getPassword());
                if(optUserEntity.isPresent())
                {
                    UserEntity userEntity1 = optUserEntity.get();
                    userEntity1.setAuthToken(UUID.randomUUID().toString());

                    //userEntity1.setLastLoginTime(new Date());
                    userEntity1.setLastLoginTime(LocalDateTime.now());
                    userRepository.save(userEntity1);
                    //return "ID: "+userEntity1.getId()+", Auth Token: "+userEntity1.getAuthToken()+", Last Login Time: "+dateFormat.format(userEntity1.getLastLoginTime());
                    LOGGER.info("User {} logged in!", userEntity1.getId());
                    return "ID: "+userEntity1.getId()+", Auth Token: "+userEntity1.getAuthToken()+", Last Login Time: "+dateTimeFormatter.format(userEntity1.getLastLoginTime());
                }
                else
                {
                    errorModel = new ErrorModel("LOG_004", "No matching user found based on Email id and Password provided!");
                    errorModelList.add(errorModel);
                    throw new BusinessException(errorModelList);
                }
            }
            else
            {
                optUserEntity = userRepository.findByPhoneAndPassword(userLogin.getPhone(), userLogin.getPassword());
                if(optUserEntity.isPresent())
                {
                    UserEntity userEntity1 = optUserEntity.get();
                    userEntity1.setAuthToken(UUID.randomUUID().toString());

                    //userEntity1.setLastLoginTime(new Date());
                    userEntity1.setLastLoginTime(LocalDateTime.now());
                    userRepository.save(userEntity1);
                    //return "ID: "+userEntity1.getId()+", Auth Token: "+userEntity1.getAuthToken()+", Last Login Time: "+dateFormat.format(userEntity1.getLastLoginTime());
                    return "ID: "+userEntity1.getId()+", Auth Token: "+userEntity1.getAuthToken()+", Last Login Time: "+dateTimeFormatter.format(userEntity1.getLastLoginTime());
                }
                else
                {
                    errorModel = new ErrorModel("LOG_005", "No matching user found based on Phone Number and Password provided!");
                    errorModelList.add(errorModel);
                    throw new BusinessException(errorModelList);
                }
            }
        }
        else
        {
            throw new BusinessException(errorModelList);
        }
    }

    @Override
    //@Scheduled(cron = "0 * * * * ?")//every 1 min
    // @Scheduled(cron = "*/5 * * * * ?")//every 5 sec
    public void sessionInvalidator() {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Cron Task  :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        }
        List<UserEntity> userEntityList = userRepository.findByAuthTokenNotNull();
        if(!userEntityList.isEmpty())
        {
            for(int i=0; i< userEntityList.size(); i++)
            {
                UserEntity userEntity = userEntityList.get(i);
                Long timeElapsed = Duration.between(userEntity.getLastLoginTime(), LocalDateTime.now()).getSeconds();
                if(timeElapsed>=TIME_DURATION)
                {
                    LOGGER.info("User {} logged out", userEntity.getId());
                    userEntity.setAuthToken(null);
                    userRepository.save(userEntity);
                }
                else
                {
                    LOGGER.info("No user found with Login duration of more than {} seconds!", TIME_DURATION);
                }
            }
        }
        else
        {
            LOGGER.info("No user with valid Authentication Token found!");
        }
    }

    @Override
    public String checkToken(Long id) throws BusinessException {
        List<ErrorModel> errorModelList = new ArrayList<>();
        ErrorModel errorModel = null;
        Optional<UserEntity> optUserEntity = userRepository.findById(id);
        UserEntity userEntity = null;
        String authToken = null;
        if(optUserEntity.isPresent())
        {
            userEntity = optUserEntity.get();
            authToken = userEntity.getAuthToken();
            if(authToken!=null)
            {
                return authToken;
            }
            errorModel = new ErrorModel("AUTH_002", "User ID provided does not have a valid Authentication token!");
        }
        else
        {
            errorModel = new ErrorModel("AUTH_001", "User ID does not exists. Please provide a valid User ID!");
        }
        errorModelList.add(errorModel);
        throw new BusinessException(errorModelList);
    }
}
