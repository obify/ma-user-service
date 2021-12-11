package com.mycompany.userservice.controller;

import com.mycompany.userservice.model.*;
import com.mycompany.userservice.exception.BusinessException;
import com.mycompany.userservice.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    private List<ServiceModel> serviceModelList = new ArrayList<>();
    private ServiceModel serviceModel = null;

    {
        serviceModel = new ServiceModel(111, "Cardless Cash Withdrawal");
        serviceModelList.add(serviceModel);
        serviceModel = new ServiceModel(112, "Housing Loan Available");
        serviceModelList.add(serviceModel);
        serviceModel = new ServiceModel(113, "Vehicle Finance Available");
        serviceModelList.add(serviceModel);
        serviceModel = new ServiceModel(114, "Savings Account Available");
        serviceModelList.add(serviceModel);
        serviceModel = new ServiceModel(115, "Current Account Available");
        serviceModelList.add(serviceModel);
        serviceModel = new ServiceModel(116, "Corporate Banking Services");
        serviceModelList.add(serviceModel);
        serviceModel = new ServiceModel(117, "Private Banking Services");
        serviceModelList.add(serviceModel);
        serviceModel = new ServiceModel(118, "NRI Services");
        serviceModelList.add(serviceModel);
        serviceModel = new ServiceModel(119, "Term Deposit");
        serviceModelList.add(serviceModel);
        serviceModel = new ServiceModel(120, "Term Insurance");
        serviceModelList.add(serviceModel);
        serviceModelList = Collections.unmodifiableList(serviceModelList);
    }

    @ApiOperation(value = "registerUser", notes = "This method registers a new User")
    @PostMapping("/users/register")
    public ResponseEntity<Object> registerUser(@ApiParam(
            name = "userRegisterRequest",
            type = "UserRegisterRequest",
            value = "User Register Request Data",
            example = "email, firstName, lastName, password, phone",
            required = true)
            @RequestBody UserRegisterRequest userRegisterRequest) throws BusinessException {
        UserRegisterResponse userRegisterResponse = userService.registerUser(userRegisterRequest);
        return new ResponseEntity<>(userRegisterResponse, HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity<Object> loginUser(@RequestBody UserLogin userLogin) throws BusinessException {
        String authToken = userService.loginUser(userLogin);
        return new ResponseEntity<>(authToken, HttpStatus.OK);
    }

    @GetMapping("/users/checkToken/{id}")
    public ResponseEntity<String> checkToken(@PathVariable(name = "id") Long id) throws BusinessException {
        String authToken = userService.checkToken(id);
        return new ResponseEntity<>(authToken, HttpStatus.OK);
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceModel>> getAllServices() {
        return new ResponseEntity<>(serviceModelList, HttpStatus.OK);
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<ServiceModel> getServiceById(@PathVariable(value = "id") Integer id) throws BusinessException {
        ErrorModel errorModel;
        List<ErrorModel> errorModelList = new ArrayList<>();

        Integer flag = 0;
        for(int i=0; i<serviceModelList.size(); i++)
        {
            serviceModel = serviceModelList.get(i);
            if(serviceModel.getId().equals(id))
            {
                flag++;
                break;
            }
        }
        if (flag==0)
        {
            errorModel = new ErrorModel("SERV_001", "INVALID SERVICE ID");
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        }
        else
        {
            return new ResponseEntity<>(serviceModel, HttpStatus.OK);
        }
    }
}
