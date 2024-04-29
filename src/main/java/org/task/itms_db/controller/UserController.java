package org.task.itms_db.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.itms_db.entity.UserEntity;
import org.task.itms_db.exception.EmailExistException;
import org.task.itms_db.model.UserModel;
import org.task.itms_db.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable Long id, HttpServletRequest request) {
        UserModel user = service.getUser(id, request);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers(HttpServletRequest request) {
        return ResponseEntity.ok(service.getAllUsers(request));
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserEntity newUsers, HttpServletRequest request) {
        return ResponseEntity.ok(service.createUsers(newUsers, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserEntity newUsers, HttpServletRequest request) throws EmailExistException {
        return ResponseEntity.ok(service.updateUser(id, newUsers, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }
}