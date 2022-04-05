package com.userloginapi.userloginapi.controller;

import com.userloginapi.userloginapi.db.services.FactoryService;
import com.userloginapi.userloginapi.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private final FactoryService factoryService;

    public UserController(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    @PostMapping()
    public ResponseEntity<String> postUser(@RequestBody UserDTO user) {
        try {

        factoryService.getUserService().postUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");

        } catch (RuntimeException | IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id) {
        try{

            factoryService.getUserService().deleteUserById(id);
            return ResponseEntity.status(200).body("DELETED");

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO) {
        try {

           boolean isLogin =  factoryService.getUserService().loginUser(userDTO);

            if(isLogin)
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("LOGIN SUCCESSFUL");
            else
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("USERNAME OR PASSWORD DOESN'T MATCH");


        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("USERNAME OR PASSWORD DOESN'T MATCH");
        }
    }
}
