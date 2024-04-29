package org.task.itms_db.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.task.itms_db.entity.QuestionEntity;
import org.task.itms_db.exception.UserCantAccessException;
import org.task.itms_db.exception.UserNotFoundException;
import org.task.itms_db.service.EmailService;

@RestController
@RequestMapping("/mail/**")
public class EmailController {

    private final EmailService service;

    public EmailController(EmailService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<String> handleEmployeeFormPass(@RequestBody QuestionEntity entity, HttpServletRequest request)
            throws UserNotFoundException, UserCantAccessException {
        service.validateData(entity, request);
        return ResponseEntity.ok("Success");
    }
}
