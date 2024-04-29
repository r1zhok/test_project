package org.task.itms_db.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.task.itms_db.entity.UserEntity;
import org.task.itms_db.exception.EmailExistException;
import org.task.itms_db.exception.UserNotFoundException;
import org.task.itms_db.jwt.JwtService;
import org.task.itms_db.model.UserModel;
import org.task.itms_db.service.AuthorizationService;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthorizationService service;

    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserEntity user) throws UserNotFoundException {
        UserModel authUser = service.loginUser(user);
        return ResponseEntity.ok().header(
                HttpHeaders.AUTHORIZATION,
                ("Bearer " + authUser.getToken())
        ).body(authUser);
    }

    @PostMapping("/registration")
    public ResponseEntity<UserModel> registerUser(@RequestBody UserEntity user) throws EmailExistException {
        UserModel authUser = service.registerUser(user);
        return ResponseEntity.ok().header(
                HttpHeaders.AUTHORIZATION,
                ("Bearer " + authUser.getToken())
        ).body(authUser);
    }
}

