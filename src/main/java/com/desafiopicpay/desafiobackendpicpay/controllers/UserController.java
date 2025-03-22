package com.desafiopicpay.desafiobackendpicpay.controllers;

import com.desafiopicpay.desafiobackendpicpay.domain.user.User;
import com.desafiopicpay.desafiobackendpicpay.dtos.UserDTO;
import com.desafiopicpay.desafiobackendpicpay.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/post")
    public ResponseEntity<User> createUser(@RequestBody UserDTO user){
    User newUser = userService.createUser(user);
    return new ResponseEntity<>(newUser, HttpStatus.CREATED);

    }

    @GetMapping("/get")
    public ResponseEntity<List<User>>getAllUsers(){
        List<User> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);

    }
}
